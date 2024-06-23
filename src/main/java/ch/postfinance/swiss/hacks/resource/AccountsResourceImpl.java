package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.domain.Account;
import ch.postfinance.swiss.hacks.resource.beans.AccountBalance;
import ch.postfinance.swiss.hacks.service.AccountService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.util.List;

@Authenticated
public class AccountsResourceImpl implements AccountsResource {

    @Inject
    AccountService accountService;

    private static AccountBalance convertToDTO(Account a) {
        var accountBalance = new AccountBalance();
        accountBalance.setIban(a.iban);
        accountBalance.setBalance(a.balanceInCentimes().toEngineeringString());
        return accountBalance;
    }

    @Override
    @RolesAllowed("user")
    public AccountBalance createANewAccount() {
        return convertToDTO(accountService.addAccount());
    }

    @Override
    @RolesAllowed("user")
    public List<AccountBalance> checkAccountBalance() {
        return accountService.currentUserAccounts()
                .map(accounts -> accounts.stream()
                        .map(AccountsResourceImpl::convertToDTO)
                        .toList())
                .orElseThrow();
    }

    @Override
    @RolesAllowed("user")
    public void removeAccount(@PathParam("iban") String iban, @QueryParam("transferTo") String transferTo) {
        accountService.removeAccount(iban, transferTo);
    }
}
