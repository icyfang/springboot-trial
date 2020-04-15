package com.example.demo.repository;

import com.example.demo.bean.Account;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface AccountRepository extends JpaRepositoryImplementation<Account, Long> {

    Account findByAccountName(String accountName);

}
