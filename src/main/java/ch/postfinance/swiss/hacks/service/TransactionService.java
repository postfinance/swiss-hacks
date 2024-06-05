package ch.postfinance.swiss.hacks.service;

import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.FAILED_INVALID_ACCOUNT_ACCESS;
import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.FAILED_NOT_ENOUGH_FUNDS;
import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.FAILED_RECIPIENT_NOT_FOUND;

import ch.postfinance.swiss.hacks.domain.Account;
import ch.postfinance.swiss.hacks.domain.Transaction;
import ch.postfinance.swiss.hacks.resource.TransactionsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TransactionService {

    @Inject
    AccountService accountService;

    @Inject
    TransactionsRepository transactionsRepository;

    public String transfer(String fromIban, String toIban, Double amount) throws IllegalTransactionException {
        var transactionId = UUID.randomUUID();

        if (!isAccountOfCurrentUser(fromIban)) {
            throw new IllegalTransactionException(transactionId, FAILED_INVALID_ACCOUNT_ACCESS);
        }

        var accountForToIban = getAccountForIban(toIban);
        if (accountForToIban.isEmpty()) {
            throw new IllegalTransactionException(transactionId, FAILED_RECIPIENT_NOT_FOUND);
        }

        var fromAccount = getAccountForIban(fromIban).orElseThrow();
        if (!accountHasEnoughFunds(fromAccount, amount)) {
            throw new IllegalTransactionException(transactionId, FAILED_NOT_ENOUGH_FUNDS);
        }

        var toAccount = accountForToIban.get();
        var transactionAmount = BigDecimal.valueOf(amount);

        fromAccount.balance = fromAccount.balance.subtract(transactionAmount);
        toAccount.balance = toAccount.balance.add(transactionAmount);

        fromAccount.persist();
        toAccount.persist();

        return transactionId.toString();
    }

    private static Optional<Account> getAccountForIban(String iban) {
        return Account.find("iban", iban).singleResultOptional();
    }

    private boolean isAccountOfCurrentUser(String fromIban) {
        return accountService.currentUserAccounts()
            .orElseThrow()
            .stream()
            .anyMatch(account -> fromIban.equals(account.iban));
    }

    private static boolean accountHasEnoughFunds(Account account, Double amount) {
        return account.balance.compareTo(BigDecimal.valueOf(amount)) >= 0;
    }

    public List<Transaction> getAllTransactions() {
        return transactionsRepository.findAll().list();
    }

    public Transaction getTransactionById(Long id) {
        return transactionsRepository.findById(id);
    }
}
