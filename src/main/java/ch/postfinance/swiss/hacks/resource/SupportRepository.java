package ch.postfinance.swiss.hacks.resource;


import ch.postfinance.swiss.hacks.domain.SupportContactInfo;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SupportRepository implements PanacheRepositoryBase<SupportContactInfo, Long> {
}
