# Devsu Banking — Prueba Técnica Java Senior

Sistema bancario de microservicios (clientes y cuentas) con comunicación asíncrona vía RabbitMQ. Prueba técnica Devsu.

---

## Stack tecnológico

| Tecnología | Versión |
|---|---|
| Java | 25 |
| Spring Boot | 4.0.6 |
| PostgreSQL | 16 |
| RabbitMQ | 3 (management) |
| Docker Compose | v2 |
| Spring Data JPA / Hibernate | 6 |
| MapStruct | 1.6.3 |
| Lombok | — |
| springdoc-openapi | 3.0.2 |

---

## Arquitectura

```
            ┌──────────────┐   eventos    ┌──────────────┐
            │ clienteMicro │ ──────────►  │  cuentaMicro │
            │    :8081     │  (RabbitMQ)  │    :8082     │
            └──────┬───────┘              └──────┬───────┘
                   │                             │
            ┌──────┴───────┐              ┌──────┴───────┐
            │ db_clientes  │              │  db_cuentas  │
            │    :5432     │              │    :5433     │
            └──────────────┘              └──────────────┘
```

**clienteMicro** gestiona el CRUD de clientes y publica eventos (`client.created`, `client.updated`, `client.deleted`) al exchange `clients.exchange` de RabbitMQ tras cada commit.

**cuentaMicro** consume esos eventos para mantener un read-model local de clientes (`clients_info`) y así servir el reporte F4 sin llamadas síncronas al otro microservicio. Gestiona cuentas y movimientos de forma autónoma.

Cada microservicio tiene su propia base de datos (aislamiento, escalado independiente). Ambos siguen **arquitectura hexagonal** (domain / application / infrastructure) — ver sección de decisiones.

---

## Requisitos previos

- **Docker** y **Docker Compose** (v2)
- Git (para clonar)

> No se requiere Java ni Maven en la máquina host. El build ocurre dentro del contenedor Docker (imagen con JDK 25, multi-stage). Solo se necesita Docker.

---

## Levantar el proyecto

```bash
git clone <url-del-repo>
cd DevsuJavaTechnicalAssessment
docker compose up --build
```

El primer arranque tarda unos minutos (descarga imágenes, compila ambos micros dentro del contenedor). Cuando veas `Started ClienteMicroApplication` y `Started CuentaMicroApplication` en los logs, el sistema está listo.

**URLs resultantes:**

| Servicio | URL |
|---|---|
| clienteMicro REST | http://localhost:8081 |
| cuentaMicro REST | http://localhost:8082 |
| Swagger clienteMicro | http://localhost:8081/swagger-ui.html |
| Swagger cuentaMicro | http://localhost:8082/swagger-ui.html |
| RabbitMQ UI | http://localhost:15672 (guest / guest) |

**Reset limpio** (si algo salió mal o quieres empezar desde cero):

```bash
docker compose down -v   # elimina volúmenes; el init SQL vuelve a correr
docker compose up --build
```

---

## Smoke test

```bash
curl http://localhost:8081/clientes   # responde [] o lista de clientes
curl http://localhost:8082/cuentas    # responde [] o lista de cuentas
```

Si ambos responden con JSON, los servicios y las bases de datos están operativos.

---

## Probar los endpoints

### Opción 1 — Postman (recomendado)

1. Importar `postman/Devsu-Banking.postman_collection.json` en Postman.
2. Usar **Run Collection** y ejecutar en orden de arriba a abajo.

> **Orden obligatorio:** crear clientes → crear cuentas → registrar movimientos → consultar reporte.
> El reporte (F4) depende de que los eventos de cliente hayan sido consumidos por cuentaMicro vía RabbitMQ (consistencia eventual). Al ejecutar la colección en orden ya hay tiempo suficiente para la propagación.

> **Delay entre requests:** la colección incluye un pre-request script a nivel de colección que introduce una pausa de **3 000 ms** antes de cada request. Esto garantiza que los eventos de RabbitMQ se propaguen correctamente entre pasos, especialmente entre la creación de clientes (carpeta 1) y la creación de cuentas (carpeta 2).

### Opción 2 — Swagger

- http://localhost:8081/swagger-ui.html → endpoints de clientes
- http://localhost:8082/swagger-ui.html → endpoints de cuentas, movimientos y reportes

### Opción 3 — curl (flujo del enunciado)

**1. Crear un cliente:**
```bash
curl -s -X POST http://localhost:8081/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jose Lema",
    "gender": "Masculino",
    "age": 30,
    "identification": "1001",
    "address": "Otavalo sn y principal",
    "phone": "098254785",
    "password": "1234",
    "active": true
  }'
# Responde con clientId (UUID), ej: "550e8400-e29b-41d4-a716-446655440000"
```

**2. Crear una cuenta** (reemplazar `<UUID>` con el clientId de la respuesta anterior):
```bash
curl -s -X POST http://localhost:8082/cuentas \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "478758",
    "accountType": "Ahorros",
    "initialBalance": 2000,
    "active": true,
    "clientId": "<UUID>"
  }'
```

**3. Registrar un movimiento:**
```bash
curl -s -X POST http://localhost:8082/movimientos \
  -H "Content-Type: application/json" \
  -d '{
    "transactionType": "Retiro",
    "amount": -575,
    "accountNumber": "478758"
  }'
# Responde con balance: 1425
```

**4. Consultar el reporte F4** (reemplazar `<UUID>` con el clientId):
```bash
curl -s "http://localhost:8082/reportes?fechaInicio=2025-01-01&fechaFin=2030-12-31&clienteId=<UUID>"
```

**Caso F3 — Saldo no disponible** (debe responder HTTP 400):
```bash
curl -s -X POST http://localhost:8082/movimientos \
  -H "Content-Type: application/json" \
  -d '{
    "transactionType": "Retiro",
    "amount": -9999,
    "accountNumber": "478758"
  }'
# Responde 400 con mensaje "Saldo no disponible"
```

---

## Correr los tests

Los tests usan **H2 en memoria** y no requieren Docker, PostgreSQL ni RabbitMQ levantados.

```bash
cd clienteMicro && ./mvnw test
```
```bash
cd cuentaMicro && ./mvnw test
```

Cobertura:
- **F5** — test unitario de la entidad `Client` (validaciones de dominio)
- **F6** — test de integración del flujo movimiento/reporte (`TransactionIntegrationTest`)

---

## Estructura del proyecto

```
DevsuJavaTechnicalAssessment/
├── clienteMicro/
│   ├── src/                  # Código fuente (hexagonal: domain/application/infrastructure)
│   ├── BaseDatos.sql         # Schema inicial (montado en postgres-cliente)
│   ├── Dockerfile            # Multi-stage: build con Maven + runtime JDK 25 Alpine
│   └── pom.xml
├── cuentaMicro/
│   ├── src/
│   ├── BaseDatos.sql         # Schema inicial (montado en postgres-cuenta)
│   ├── Dockerfile
│   └── pom.xml
├── postman/
│   └── Devsu-Banking.postman_collection.json
├── docker-compose.yml
└── README.md
```

---

## Decisiones de arquitectura

- **Arquitectura hexagonal** en ambos microservicios: las capas `domain` y `application` no dependen de frameworks ni de infraestructura. Los adaptadores (REST, JPA, RabbitMQ) están en `infrastructure` e implementan puertos definidos en `application`.

- **Base de datos por microservicio**: `db_clientes` (puerto 5432) y `db_cuentas` (puerto 5433). Permite escalar, desplegar y evolucionar cada servicio de forma independiente sin acoplamiento de schema.

- **Comunicación asíncrona vía RabbitMQ**: clienteMicro publica eventos de dominio; cuentaMicro los consume para mantener un read-model local de clientes (`clients_info`). El reporte F4 se genera localmente en cuentaMicro sin ninguna llamada síncrona a clienteMicro, evitando acoplamiento temporal.

- **`@TransactionalEventListener(AFTER_COMMIT)`**: el evento de dominio se publica al broker solo después de que la transacción de negocio se confirma en BD. Evita publicar eventos de cambios que luego se revierten.

- **`ddl-auto=update` + `BaseDatos.sql`**: el SQL de inicialización crea el schema en contenedores nuevos; Hibernate actualiza diferencias en arranques subsiguientes. El docker-compose sobreescribe la propiedad para garantizar `update` en contenedor.

- **`BigDecimal` para dinero**: evita los errores de redondeo de `double`/`float` en operaciones monetarias.

- **UUID como clave primaria**: generados por Hibernate antes del INSERT (`GenerationType.UUID`). Eliminan colisiones al escalar horizontalmente y no exponen un ID secuencial enumerable en la API.

- **Herencia `Cliente` → `Persona`**: tabla única (single table inheritance) para simplificar las consultas y el mapeo JPA mientras el dominio solo tiene un subtipo concreto.

- **Java 25 sin riesgo**: el build ocurre dentro del contenedor (imagen `maven:3.9-eclipse-temurin-25`). El evaluador no necesita JDK 25 en su máquina.

---

## Resiliencia y escalabilidad

| Medida | Estado |
|---|---|
| Consumidor idempotente (upsert via `JpaRepository.save()` con `@Id clientId`) | ✅ Implementado |
| Retry con backoff exponencial en listener RabbitMQ (max 3 intentos, 1 s → 2 s → 4 s) | ✅ Implementado |
| Jackson tolerante a campos desconocidos (`FAIL_ON_UNKNOWN_PROPERTIES=false`) | ✅ Implementado |
| Consistencia eventual explícita (`@TransactionalEventListener(AFTER_COMMIT)`) | ✅ Implementado |
| Patrón Outbox (atomicidad garantizada entre cambio en BD y publicación del evento) | ☐ Contemplado |
| Dead Letter Queue para mensajes venenosos | ☐ Contemplado |
| `@Version` (bloqueo optimista en saldos con acceso concurrente) | ☐ Contemplado |
| API Gateway (rate limiting, auth centralizada, routing) | ☐ Contemplado |
| Escalado horizontal independiente por servicio | ☐ Contemplado |

---

## Variables de entorno

Las siguientes variables se configuran en `docker-compose.yml`. Pueden ajustarse para apuntar a infraestructura externa.

| Variable | Servicio | Default | Descripción |
|---|---|---|---|
| `DB_URL` | ambos | `jdbc:postgresql://<host>:<port>/<db>` | JDBC URL de la base de datos |
| `DB_USER` | ambos | `postgres` | Usuario de PostgreSQL |
| `DB_PASSWORD` | ambos | `postgres` | Contraseña de PostgreSQL |
| `RABBITMQ_HOST` | ambos | `rabbitmq` | Host del broker |
| `RABBITMQ_PORT` | ambos | `5672` | Puerto AMQP |
| `RABBITMQ_USER` | ambos | `guest` | Usuario RabbitMQ |
| `RABBITMQ_PASSWORD` | ambos | `guest` | Contraseña RabbitMQ |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | clienteMicro | `update` | Estrategia DDL de Hibernate |

---

## Troubleshooting

**Las tablas no aparecen o el init SQL no corrió**
El volumen de PostgreSQL ya existía de un arranque anterior y el script de init no vuelve a correr. Solución:
```bash
docker compose down -v && docker compose up --build
```

**Puerto ya en uso**
Algún proceso local ocupa el 8081, 8082, 5432, 5433 o 5672. Detenerlo, o cambiar el mapeo de puertos en `docker-compose.yml` (lado izquierdo del `:`).

**El reporte no muestra un cliente recién creado**
Consistencia eventual: el evento de creación viaja por RabbitMQ y puede tardar unos segundos en ser consumido por cuentaMicro. Esperar 2-3 segundos y reintentar la consulta al reporte.

**`./mvnw test` falla con "JDK version not supported"**
No uses el Maven local si tu JDK no es 25. Si aún falla, correr los tests dentro del contenedor de build:
```bash
docker run --rm -v "$PWD/clienteMicro":/app -w /app maven:3.9-eclipse-temurin-25 mvn test
```
