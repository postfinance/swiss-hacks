package ch.postfinance.swiss.hacks.service;

import static ch.postfinance.swiss.hacks.domain.Account.newAccount;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.assertj.core.api.Assertions.assertThat;

import ch.postfinance.swiss.hacks.domain.Login;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TransactionServiceTest {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    TransactionService fixture;

    @Test
    @TestTransaction
    @TestSecurity(user = "bruce.wayne", roles = {"user"})
    void canTransferFundsBetweenAccounts() throws IllegalTransactionException {
        var login = new Login();
        login.firstName = "Bruce";
        login.lastName = "Wayne";
        login.password = "You cannot pass.";
        login.dateOfBirth = LocalDate.parse("1939-03-30", ISO_LOCAL_DATE).atStartOfDay().toInstant(ZoneOffset.UTC);

        var fromAccount = newAccount(login);
        login.accounts.add(fromAccount);

        var toAccount = newAccount(login);
        login.accounts.add(toAccount);

        login.persist();

        var fromBalance = fromAccount.balance;
        var toBalance = toAccount.balance;

        securityIdentity.getPrincipal();

        var transactionAmount = 200.00;

        var description = "Lorem ipsum";

        var transactionId = fixture.transfer(fromAccount.iban, toAccount.iban, transactionAmount, description);
        assertThat(transactionId).isNotEmpty();

        assertThat(fromAccount.balance).isEqualTo(fromBalance.subtract(BigDecimal.valueOf(transactionAmount)));
        assertThat(toAccount.balance).isEqualTo(toBalance.add(BigDecimal.valueOf(transactionAmount)));
    }
}
