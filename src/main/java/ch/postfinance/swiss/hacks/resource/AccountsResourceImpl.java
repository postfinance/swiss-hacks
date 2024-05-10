package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.resource.beans.AccountBalance;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@Authenticated
public class AccountsResourceImpl implements AccountsResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Override
    public AccountBalance checkAccountBalance() {
        var accountBalance = new AccountBalance();
        accountBalance.setAccountId(securityIdentity.getPrincipal().getName());
        accountBalance.setBalance(100.0);
        return accountBalance;
    }

    @Override
    public Response downloadAccountStatement(String period) {
        return null;
    }
}
