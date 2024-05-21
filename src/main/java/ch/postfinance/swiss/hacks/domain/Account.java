package ch.postfinance.swiss.hacks.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.iban4j.Iban;

import java.math.BigDecimal;
import java.security.SecureRandom;

import static java.lang.String.format;
import static java.math.RoundingMode.HALF_UP;
import static org.iban4j.CountryCode.CH;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"iban"})
})
public class Account extends PanacheEntity {

    private static final int ROUNDING_SCALE = 2;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Column(length = 22)
    public String iban;

    public BigDecimal balance;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    public Login login;

    /**
     * Adds a new user account to the database. {@code iban} and {@code password} for authentication will be generated
     * randomly.
     *
     * @return the persisted account with all information
     */
    public static Account newAccount(Login login) {
        var account = new Account();
        account.iban = generateIban();
        account.balance = BigDecimal.ZERO;
        account.login = login;
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

    public BigDecimal balanceInCentimes() {
        // Multiply by 20 to convert francs to units of 0.05 (centimes)
        BigDecimal inCentimes = balance.multiply(new BigDecimal(20));
        // Round the value in centimes using half-up rounding mode
        BigDecimal roundedInCentimes = inCentimes.setScale(0, HALF_UP);
        // Divide by 20 to convert back to francs with rounding
        return roundedInCentimes.divide(new BigDecimal(20), ROUNDING_SCALE, HALF_UP);
    }
}
