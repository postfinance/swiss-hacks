package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.domain.Transaction;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionRepository implements PanacheRepositoryBase<Transaction, Long> {
}
