package com.example.demo.bean;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "col_account")
public class Account {

    /**
     * 简单代表一下账户所属人
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountName;

    @Column(columnDefinition = "DECIMAL(19, 2)")
    private BigDecimal balance;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
