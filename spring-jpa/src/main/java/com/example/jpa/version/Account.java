package com.example.jpa.version;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
@Table(name = "t_account")
public class Account {

    @Id
    private Long id;

    private String accountName;

    @Column(columnDefinition = "DECIMAL(19, 2)")
    private BigDecimal balance;

    public Account(Long id, String accountName, BigDecimal balance) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
    }
}
