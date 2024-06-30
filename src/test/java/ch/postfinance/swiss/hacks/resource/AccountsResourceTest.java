package ch.postfinance.swiss.hacks.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.stringContainsInOrder;

@QuarkusTest
class AccountsResourceTest {

    @Test
    void testSuccessfulRegistration() {
        // Define customer data
        String firstName = "Foo";
        String lastName = "Bar";
        String dateOfBirth = "2000-01-01";

        // Build request body
        String requestBody = String.format("{" +
                "\"firstName\": \"%s\"," +
                "\"lastName\": \"%s\"," +
                "\"dateOfBirth\": \"%s\"" +
                "}", firstName, lastName, dateOfBirth);

        // Send POST request and verify response
        String response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/customers/register")
                .then()
                .statusCode(200) // TODO: this should be 201 according to spec!
                .body("username", equalTo((firstName + "." + lastName).toLowerCase()))
                .body("password", notNullValue())
                .extract().asString();

        // Extract username from response
        String username = JsonPath.from(response).getString("username");
        String password = JsonPath.from(response).getString("password");

        // Send login request with extracted username and password
        given()
                // .contentType(ContentType.FORM)  // Use form data for login
                .formParam("j_username", username)
                .formParam("j_password", password)
                .post("/j_security_check")
                .then()
                .statusCode(302)
                .header("location", stringContainsInOrder("/index.html"));
    }

    @Test
    void testMissingRequiredField() {
        // Define customer data with missing first name
        String lastName = "Doe";
        String dateOfBirth = "2000-01-01";

        // Build request body
        String requestBody = String.format("{" +
                "\"lastName\": \"%s\"," +
                "\"dateOfBirth\": \"%s\"" +
                "}", lastName, dateOfBirth);

        // Send POST request and verify response
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/customers/register")
                .then()
                .statusCode(400);
    }
}
