package ch.postfinance.swiss.hacks.service;

import ch.postfinance.swiss.hacks.domain.Account;
import ch.postfinance.swiss.hacks.domain.Transaction;
import ch.postfinance.swiss.hacks.resource.TransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.FAILED_INVALID_ACCOUNT_ACCESS;
import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.FAILED_NOT_ENOUGH_FUNDS;
import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.FAILED_RECIPIENT_NOT_FOUND;

@ApplicationScoped
public class TransactionService {

    @Inject
    AccountService accountService;
    @Inject
    private TransactionRepository transactionRepository;

    private static Optional<Account> getAccountForIban(String iban) {
        return Account.find("iban", iban).singleResultOptional();
    }

    private static boolean accountHasEnoughFunds(Account account, Double amount) {
        return account.balance.compareTo(BigDecimal.valueOf(amount)) >= 0;
    }

    @Transactional
    public String transfer(String fromIban, String toIban, Double amount, String description) throws IllegalTransactionException {
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

        Transaction transaction = new Transaction();
        transaction.transactionId = transactionId;
        transaction.amount = BigDecimal.valueOf(amount);
        transaction.description = description;
        transaction.fromIban = fromIban;
        transaction.toIban = toIban;

        transactionRepository.persist(transaction);

        return transactionId.toString();
    }

    private boolean isAccountOfCurrentUser(String fromIban) {
        return accountService.currentUserAccounts()
                .orElseThrow()
                .stream()
                .anyMatch(account -> fromIban.equals(account.iban));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll().list();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }
}
