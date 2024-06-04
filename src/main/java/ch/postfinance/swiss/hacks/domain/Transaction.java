package ch.postfinance.swiss.hacks.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table
public class Transaction extends PanacheEntity {

    public String description;

    @Column(length = 22)
    public String fromIban;

    @Column(length = 22)
    public String toIban;

    public BigDecimal amount;
}
