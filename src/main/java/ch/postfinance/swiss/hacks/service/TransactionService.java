package ch.postfinance.swiss.hacks.service;

import ch.postfinance.swiss.hacks.domain.Account;
import ch.postfinance.swiss.hacks.domain.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.FAILED_INVALID_ACCOUNT_ACCESS;
import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.FAILED_INVALID_AMOUNT;
import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.FAILED_NOT_ENOUGH_FUNDS;
import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.FAILED_RECIPIENT_NOT_FOUND;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;

@ApplicationScoped
public class TransactionService {

    @Inject
    AccountService accountService;

    private static Optional<Account> getAccountForIban(String iban) {
        return Account.find("iban", iban).singleResultOptional();
    }

    private static boolean accountHasEnoughFunds(Account account, BigDecimal amount) {
        return account.balance.compareTo(amount) >= 0;
    }

    @Transactional
    public String transfer(String fromIban, String toIban, Double amount, String description) throws IllegalTransactionException {
        var transactionId = UUID.randomUUID();

        if (!isAccountOfCurrentUser(fromIban)) {
            throw new IllegalTransactionException(transactionId, FAILED_INVALID_ACCOUNT_ACCESS);
        }

        var transactionAmount = validateAndConstructAmount(transactionId, amount);

        var accountForToIban = getAccountForIban(toIban);
        if (accountForToIban.isEmpty()) {
            throw new IllegalTransactionException(transactionId, FAILED_RECIPIENT_NOT_FOUND);
        }

        var fromAccount = getAccountForIban(fromIban).orElseThrow();
        if (!accountHasEnoughFunds(fromAccount, transactionAmount)) {
            throw new IllegalTransactionException(transactionId, FAILED_NOT_ENOUGH_FUNDS);
        }

        var toAccount = accountForToIban.get();

        fromAccount.balance = fromAccount.balance.subtract(transactionAmount);
        toAccount.balance = toAccount.balance.add(transactionAmount);

        fromAccount.persist();
        toAccount.persist();

        Transaction transaction = new Transaction();
        transaction.transactionId = transactionId;
        transaction.amount = transactionAmount;
        transaction.description = description;
        transaction.fromIban = fromIban;
        transaction.toIban = toIban;

        transaction.persist();

        return transactionId.toString();
    }

    private static BigDecimal validateAndConstructAmount(UUID transactionId, Double amount) throws IllegalTransactionException {
        var transactionAmount = BigDecimal.valueOf(amount);
        transactionAmount = transactionAmount.setScale(2, HALF_EVEN);

        var lastTwoDigits = transactionAmount.multiply(new BigDecimal(100)).remainder(TEN).setScale(0, HALF_EVEN);

        if (transactionAmount.scale() > 2
                || (!lastTwoDigits.equals(BigDecimal.valueOf(5)) && !lastTwoDigits.equals(ZERO))) {
            throw new IllegalTransactionException(transactionId, FAILED_INVALID_AMOUNT);
        }

        return transactionAmount;
    }

    private boolean isAccountOfCurrentUser(String fromIban) {
        return accountService.currentUserAccounts()
                .orElseThrow()
                .stream()
                .anyMatch(account -> fromIban.equals(account.iban));
    }

    public List<Transaction> getAllTransactions() {
        return Transaction.findAll().list();
    }
}
