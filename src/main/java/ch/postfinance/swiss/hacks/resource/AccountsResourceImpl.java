package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.resource.beans.AccountBalance;
import jakarta.ws.rs.core.Response;

public class AccountsResourceImpl implements AccountsResource {

    @Override
    public AccountBalance checkAccountBalance() {
        return null;
    }

    @Override
    public Response downloadAccountStatement(String period) {
        return null;
    }
}
