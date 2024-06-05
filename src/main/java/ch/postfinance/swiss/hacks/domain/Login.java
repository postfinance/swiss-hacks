package ch.postfinance.swiss.hacks.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static ch.postfinance.swiss.hacks.domain.Account.newAccount;
import static io.quarkus.elytron.security.common.BcryptUtil.bcryptHash;
import static jakarta.persistence.CascadeType.ALL;

@Entity
@UserDefinition
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"firstName", "lastName"})
})
public class Login extends PanacheEntity {

    @Username
    public String username;

    public String firstName;

    @Password
    public String password;

    public String lastName;
    public Instant dateOfBirth;

    @Roles
    public String role;

    @OneToMany(mappedBy = "login", cascade = ALL, orphanRemoval = true)
    public Set<Account> accounts = new HashSet<>();

    public static Login newLogin(String firstName, String lastName, Instant dateOfBirth, String password) {
        var login = new Login();
        login.password = bcryptHash(password);
        login.firstName = firstName.trim();
        login.lastName = lastName.trim();
        login.dateOfBirth = dateOfBirth;
        login.role = "user";
        login.accounts.add(newAccount(login));
        login.persist();
        return login;
    }

    @PrePersist
    public void afterLoad() {
        this.username = firstName.toLowerCase() + "." + lastName.toLowerCase();
    }
}
