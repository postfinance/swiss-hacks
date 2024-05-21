package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.resource.beans.CustomerRegistration;
import ch.postfinance.swiss.hacks.resource.beans.PasswordChange;
import ch.postfinance.swiss.hacks.resource.beans.RegistrationResponse;
import ch.postfinance.swiss.hacks.service.LoginService;
import jakarta.inject.Inject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class CustomersResourceImpl implements CustomersResource {

    @Inject
    LoginService loginService;

    @Override
    public RegistrationResponse registerANewCustomer(CustomerRegistration data) {
        var registrationInformation = loginService.register(data.getFirstName(), data.getLastName(), parseDateToInstant(data.getDateOfBirth()));

        var registrationResponse = new RegistrationResponse();
        registrationResponse.setUsername(registrationInformation.login().username);
        registrationResponse.setPassword(registrationInformation.password());
        return registrationResponse;
    }

    @Override
    public void changePassword(PasswordChange data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Instant parseDateToInstant(String dateString) {
        return LocalDate.parse(dateString, ISO_LOCAL_DATE).atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
