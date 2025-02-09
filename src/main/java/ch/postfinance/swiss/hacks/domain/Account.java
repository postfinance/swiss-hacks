package ch.postfinance.swiss.hacks.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import org.iban4j.Iban;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

import static ch.postfinance.swiss.hacks.GlobalConstants.BANK_CODE;
import static java.lang.String.format;
import static java.math.RoundingMode.HALF_UP;
import static org.iban4j.CountryCode.CH;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"iban"})
})
public class Account extends PanacheEntityBase {

    private static final int ROUNDING_SCALE = 2;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Id
    @NotNull
    @Column(length = 22, nullable = false, updatable = false)
    public String iban;

    @NotNull
    @Column(nullable = false)
    public BigDecimal balance;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    public Login login;

    public static Account newAccount(Login login) {
        var account = new Account();
        account.iban = generateIban();
        account.balance = generateRandomBigDecimalInRange(BigDecimal.valueOf(1_000),
                BigDecimal.valueOf(10_000));
        account.login = login;
        return account;
    }

    private static String generateIban() {
        return new Iban.Builder()
                .countryCode(CH)
                .bankCode(BANK_CODE)
                .accountNumber(generateAccountNumber())
                .build()
                .toString();
    }

    private static String generateAccountNumber() {
        return format("%012d", SECURE_RANDOM.nextLong(1_000_000_000_000L));
    }

    private static BigDecimal generateRandomBigDecimalInRange(BigDecimal min, BigDecimal max) {
        BigDecimal randomBigDecimal = min.add(
                BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble())
                        .multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2, HALF_UP);
    }

    public BigDecimal balanceInCentimes() {
        BigDecimal inCentimes = balance.multiply(new BigDecimal(20));
        BigDecimal roundedInCentimes = inCentimes.setScale(0, HALF_UP);
        return roundedInCentimes.divide(new BigDecimal(20), ROUNDING_SCALE, HALF_UP);
    }
}
