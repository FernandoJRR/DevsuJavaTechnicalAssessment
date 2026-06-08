package com.devsu.cuentas.application.service;

import com.devsu.cuentas.application.port.in.ReportUseCase;
import com.devsu.cuentas.application.port.out.AccountRepositoryPort;
import com.devsu.cuentas.application.port.out.ClientInfoRepositoryPort;
import com.devsu.cuentas.application.port.out.TransactionRepositoryPort;
import com.devsu.cuentas.domain.model.Account;
import com.devsu.cuentas.domain.model.ClientInfo;
import com.devsu.cuentas.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService implements ReportUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final ClientInfoRepositoryPort clientInfoRepositoryPort;

    @Override
    public List<ReportEntry> generateReport(LocalDate start, LocalDate end, UUID clientId) {
        String clientName = clientInfoRepositoryPort.findByClientId(clientId)
                .map(ClientInfo::getName)
                .orElse("Unknown");

        List<Account> accounts = accountRepositoryPort.findByClientId(clientId);

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        List<ReportEntry> entries = new ArrayList<>();
        for (Account account : accounts) {
            List<Transaction> transactions = transactionRepositoryPort
                    .findByAccountNumberAndDateBetween(account.getAccountNumber(), startDateTime, endDateTime);

            for (Transaction tx : transactions) {
                entries.add(new ReportEntry(
                        tx.getDate(),
                        clientName,
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getInitialBalance(),
                        Boolean.TRUE.equals(account.getActive()),
                        tx.getAmount(),
                        tx.getBalance()
                ));
            }
        }
        return entries;
    }
}
