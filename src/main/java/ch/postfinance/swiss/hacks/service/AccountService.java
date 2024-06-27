package ch.postfinance.swiss.hacks.service;

import ch.postfinance.swiss.hacks.domain.Account;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

import java.util.Optional;
import java.util.Set;

import static ch.postfinance.swiss.hacks.SecurityUtils.currentLogin;
import static ch.postfinance.swiss.hacks.domain.Account.newAccount;

@ApplicationScoped
public class AccountService {

    @Inject
    SecurityIdentity securityIdentity;

    public Optional<Set<Account>> currentUserAccounts() {
        return currentLogin(securityIdentity)
                .map(login -> login.accounts);
    }

    @Transactional
    public Account addAccount() {
        var login = currentLogin(securityIdentity)
                .orElseThrow();

        var account = newAccount(login);
        login.accounts.add(account);

        login.persist();

        return account;
    }

    @Transactional
    public void removeAccount(String iban, String transferTo) {
        Optional<Account> accountToDelete = getUserAccount(iban);
        Optional<Account> transferAccount = getUserAccount(transferTo);

        if (accountToDelete.isEmpty() || transferAccount.isEmpty()) {
            throw new BadRequestException("You are not allowed to remove this account!");
        }

        transferAccount.get().balance = transferAccount.get().balance.add(accountToDelete.get().balance);
        transferAccount.get().persist();
        accountToDelete.get().delete();
    }

    private Optional<Account> getUserAccount(String iban) {
        return currentUserAccounts()
                .flatMap(accounts -> accounts.stream().filter(account -> account.iban.equals(iban)).findFirst());
    }
}
