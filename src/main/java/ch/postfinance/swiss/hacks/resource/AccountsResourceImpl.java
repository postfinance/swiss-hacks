package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.domain.Account;
import ch.postfinance.swiss.hacks.resource.beans.AccountBalance;
import ch.postfinance.swiss.hacks.service.AccountService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import java.util.List;

@Authenticated
public class AccountsResourceImpl implements AccountsResource {

    @Inject
    AccountService accountService;

    private static AccountBalance getAccountBalance(Account a) {
        var accountBalance = new AccountBalance();
        accountBalance.setIban(a.iban);
        accountBalance.setBalance(a.balanceInCentimes().toEngineeringString());
        return accountBalance;
    }

    @Override
    @RolesAllowed("user")
    public List<AccountBalance> checkAccountBalance() {
        return accountService.currentUserAccounts()
                .map(accounts -> accounts.stream()
                        .map(AccountsResourceImpl::getAccountBalance)
                        .toList())
                .orElseThrow();
    }
}
