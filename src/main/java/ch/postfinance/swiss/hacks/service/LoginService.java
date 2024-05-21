package ch.postfinance.swiss.hacks.service;

import ch.postfinance.swiss.hacks.domain.Login;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.security.SecureRandom;
import java.time.Instant;

import static ch.postfinance.swiss.hacks.domain.Login.newLogin;

@ApplicationScoped
public class LoginService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Transactional
    public RegistrationInformation register(String firstName, String lastName, Instant dateOfBirth) {
        // TODO: Some verification/validity checks, probably

        var password = String.valueOf(SECURE_RANDOM.nextInt(100_000,100_000_000));
        var login = newLogin(firstName, lastName, dateOfBirth, password);

        return new RegistrationInformation(login, password);
    }

    public record RegistrationInformation(Login login, String password) {
    }
}
