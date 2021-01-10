package com.example.jpa.version;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;

/**
 * @author shanghongcai
 * @since 2021-01-06
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "t_account_version")
public class AccountWithVersion {

    @Id
    private Long id;

    private String accountName;

    @Column(columnDefinition = "DECIMAL(19, 2)")
    private BigDecimal balance;

    @Version
    private Integer version;

    public AccountWithVersion(Long id, String accountName, BigDecimal balance) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
    }
}
