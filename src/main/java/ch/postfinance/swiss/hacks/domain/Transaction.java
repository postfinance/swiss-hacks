package ch.postfinance.swiss.hacks.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
public class Transaction extends PanacheEntity {

    @Column(length = 10)
    public UUID transactionId;

    @Column(length = 255)
    public String description;

    @Column(length = 22)
    public String fromIban;

    @Column(length = 22)
    public String toIban;

    @Column(length = 22)
    public BigDecimal amount;
}
