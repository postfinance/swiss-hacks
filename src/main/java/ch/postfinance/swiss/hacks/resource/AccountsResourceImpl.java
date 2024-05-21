package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.domain.Account;
import ch.postfinance.swiss.hacks.resource.beans.AccountBalance;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

import static ch.postfinance.swiss.hacks.SecurityUtils.currentLogin;

@Authenticated
public class AccountsResourceImpl implements AccountsResource {

    @Inject
    SecurityIdentity securityIdentity;

    private static AccountBalance getAccountBalance(Account a) {
        var accountBalance = new AccountBalance();
        accountBalance.setIban(a.iban);
        accountBalance.setBalance(a.balanceInCentimes().toEngineeringString());
        return accountBalance;
    }

    @Override
    @RolesAllowed("user")
    public List<AccountBalance> checkAccountBalance() {
        return currentLogin(securityIdentity)
                .map(l -> l.accounts)
                .map(accounts -> accounts.stream()
                        .map(AccountsResourceImpl::getAccountBalance)
                        .toList())
                .orElseThrow();
    }

    @Override
    public Response downloadAccountStatement(String period) {
        return null;
    }
}
