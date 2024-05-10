package ch.postfinance.swiss.hacks.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.iban4j.Iban;

import java.security.SecureRandom;
import java.time.Instant;

import static io.quarkus.elytron.security.common.BcryptUtil.bcryptHash;
import static java.lang.String.format;
import static org.iban4j.CountryCode.CH;

@Entity
@UserDefinition
public class Account extends PanacheEntity {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Username
    @Column(length = 22)
    public String iban;

    @Password
    public String password;

    public String firstName;
    public String lastName;
    public Instant dateOfBirth;

    @Roles
    public String role;

    /**
     * Adds a new user account to the database. {@code iban} and {@code password} for authentication will be generated
     * randomly.
     *
     * @param firstName   the first name of the new user
     * @param lastName    the last name of the new user
     * @param dateOfBirth birthday of the new user
     * @param password    a random generated password used for authentication
     * @return the persisted account with all information
     */
    public static Account addAccount(String firstName, String lastName, Instant dateOfBirth, String password) {
        var account = new Account();
        account.iban = generateIban();
        account.password = bcryptHash(password);
        account.firstName = firstName;
        account.lastName = lastName;
        account.dateOfBirth = dateOfBirth;
        account.role = "user";
        account.persist();
        return account;
    }

    private static String generateIban() {
        return new Iban.Builder()
                .countryCode(CH)
                .bankCode("12345")
                .accountNumber(generateAccountNumber())
                .build()
                .toString();
    }

    public static String generateAccountNumber() {
        return format("%012d", SECURE_RANDOM.nextLong(1_000_000_000_000L));
    }
}
