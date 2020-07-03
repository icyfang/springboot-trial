package com.example.springjpa.repository;

import com.example.springjpa.bean.Account;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Account.class, idClass = Long.class)
public interface AccountRepository2 {

    Account save(Account account);

    Account findByAccountName(String accountName);

    List<Account> findAll();

}
