package ch.postfinance.swiss.hacks.service;

import ch.postfinance.swiss.hacks.domain.Account;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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
}
