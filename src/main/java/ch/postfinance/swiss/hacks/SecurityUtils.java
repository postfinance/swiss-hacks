package ch.postfinance.swiss.hacks;

import ch.postfinance.swiss.hacks.domain.Login;
import io.quarkus.security.identity.SecurityIdentity;

import java.util.Optional;

public class SecurityUtils {

    private SecurityUtils() {
        throw new IllegalStateException("Utility class!");
    }

    public static Optional<Login> currentLogin(SecurityIdentity securityIdentity) {
        var login = Login.<Login>find("username", securityIdentity.getPrincipal().getName())
                .firstResult();
        return Optional.ofNullable(login);
    }
}
