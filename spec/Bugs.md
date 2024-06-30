# List of Bugs

## Known Bugs

These bugs have already been in the application/code since the beginning of the challenge. They should probably be well
known by now.

- [Wrong Response Code](#wrong-response-code)
- [Accounts cannot be deleted](#accounts-cannot-be-deleted)
- [Invalid Registration Validation](#invalid-registration-validation)

### Wrong Response Code

This bug is related to any endpoint specifying a success response code other than HTTP 200 (
see [`openapi.yml`](../src/main/resources/openapi/openapi.yml)).

**Current (falsy) behavior**: The endpoint does always return an HTTP 200 response code.

**Expected (true) behavior**: The endpoint returns the specified success response code.

This bug has also been depicted in
the [`AccountsResourceTest`](../src/test/java/ch/postfinance/swiss/hacks/resource/AccountsResourceTest.java).

### Accounts cannot be deleted

This bug is related to "User Story 5: Delete account" and the `DELETE /accounts/{iban}` endpoint.

**Current (falsy) behavior**: When I delete an account and transfer the remaining money to another one, the deleted
account vanishes.

**Expected (true) behavior**: The remaining money will be added to the transfer account, but the original account will
_not_ be deleted.

Basically, with this bug, you can stack your money endlessly.

### Invalid Registration Validation

This bug is related to "User Story 1: Customer Registration" and the `POST /customers/register` endpoint.

**Current (falsy) behavior**: The backend does not validate the inputs from the API, it just suspects that all
parameters are present. That results in an internal server error and an HTTP 500 response code, if one is not present.

**Expected (true) behavior**: Invalid input results in an HTTP 400 response code.

There has been a `TODO` in the [`LoginService`](../src/main/java/ch/postfinance/swiss/hacks/service/LoginService.java)
that stated out the missing implementation.
