package ch.postfinance.swiss.hacks.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table
public class Transaction extends PanacheEntity {

    @NotNull
    @Column(length = 10, nullable = false)
    public UUID transactionId;

    @Lob
    @Column
    public String description;

    @NotNull
    @Column(length = 22, nullable = false)
    public String fromIban;

    @NotNull
    @Column(length = 22, nullable = false)
    public String toIban;

    @NotNull
    @Column(length = 22, nullable = false)
    public BigDecimal amount;

    @NotNull
    @Column
    public Instant persistedAt = Instant.now();
}
