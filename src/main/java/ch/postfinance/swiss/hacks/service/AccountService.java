package ch.postfinance.swiss.hacks.service;

import static ch.postfinance.swiss.hacks.SecurityUtils.currentLogin;

import ch.postfinance.swiss.hacks.domain.Account;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class AccountService {

    @Inject
    SecurityIdentity securityIdentity;

    public Optional<Set<Account>> currentUserAccounts() {
        return currentLogin(securityIdentity)
            .map(login -> login.accounts);
    }
}
