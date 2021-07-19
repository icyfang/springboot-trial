package com.example.jpa.version;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class LockTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @Order(1)
    public void queryWithReadLock() {

        Account account = new Account((long) 1, "Bob", new BigDecimal(100));
        accountRepository.save(account);

        accountRepository.findByAccountName("Bob");
    }

    @Test
    @Order(2)
    public void queryWithWriteLock() {

        Account account = new Account((long) 2, "Jack", new BigDecimal(100));
        accountRepository.save(account);

        accountRepository.queryByAccountName("Jack");
    }

}
