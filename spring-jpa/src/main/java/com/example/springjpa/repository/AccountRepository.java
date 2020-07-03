package com.example.springjpa.repository;

import com.example.springjpa.bean.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountName(String accountName);

    @Query(value = "select * from account u where u.accountName=:accountName", nativeQuery = true)
    Account findAccount(@Param("accountName") String name);

    List<Account> find1(int name);

    List<Account> findTop3ByAccountName(String name);

    void deleteByAccountName(String name);

    List<Account> findFirst3ByAccountName(String name);

    Account findByAccountNameAndBalance(String name, BigDecimal balance);
}
