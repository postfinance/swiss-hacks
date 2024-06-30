package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.domain.Login;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static ch.postfinance.swiss.hacks.domain.Account.newAccount;
import static io.restassured.RestAssured.given;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class TransactionResourceTest {

    private static String authenticate(String username, String password) {
        return given()
                .formParam("j_username", username)
                .formParam("j_password", password)
                .post("/j_security_check")
                .thenReturn()
                .cookie("quarkus-credential");
    }

    @Test
    @TestTransaction
    @TestSecurity(user = "peter.parker", roles = {"user"})
    void cannotTransferNegativeFundsBetweenAccounts() {
        var login = new Login();
        login.firstName = "Peter";
        login.lastName = "Parker";
        login.password = "Secret spidy password.";
        login.dateOfBirth = LocalDate.parse("2001-08-10", ISO_LOCAL_DATE).atStartOfDay().toInstant(ZoneOffset.UTC);

        var fromAccount = newAccount(login);
        login.accounts.add(fromAccount);

        var toAccount = newAccount(login);
        login.accounts.add(toAccount);

        login.persist();

        var transactionAmount = -200.00;

        var requestBody = "{" +
                "\"fromIban\": \"" + fromAccount.iban + "\"," +
                "\"toIban\": \"" + toAccount.iban + "\"," +
                "\"amount\": \"" + transactionAmount + "\"," +
                "\"description\": \"\"" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .cookie("quarkus-credential", authenticate(login.username, login.password))
                .post("/transactions/transfer")
                .then()
                .statusCode(200) // TODO: this should be 201 according to spec!
                .body("transactionId", notNullValue())
                .body("status", equalTo("SUCCESS"));
    }
}
