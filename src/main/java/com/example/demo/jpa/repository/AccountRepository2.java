package com.example.demo.jpa.repository;

import com.example.demo.jpa.bean.Account;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Account.class, idClass = Long.class)
public interface AccountRepository2 {

    Account save(Account account);

    Account findByAccountName(String accountName);

    List<Account> findAll();

}
