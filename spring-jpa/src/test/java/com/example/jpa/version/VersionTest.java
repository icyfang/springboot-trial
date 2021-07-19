package com.example.jpa.version;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VersionTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountWithVersionService accountWithVersionService;
    @Autowired
    AccountWithVersionRepository accountWithVersionRepository;

    @Test
    public void addAccountMoney() throws InterruptedException {

        Account account = new Account((long) 1, "Bob", new BigDecimal(100));
        accountRepository.save(account);

        CountDownLatch count = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> {
            String result = accountService.addAccountMoney((long) 1, BigDecimal.valueOf(-50));
            System.out.println(Thread.currentThread().getName() + "，result : " + result);
            count.countDown();
        });
        TimeUnit.SECONDS.sleep(1);

        executorService.execute(() -> {
            String result = accountService.addAccountMoney((long) 1, BigDecimal.valueOf(100));
            System.out.println(Thread.currentThread().getName() + "，result : " + result);
            count.countDown();
        });
        count.await(10, TimeUnit.SECONDS);

        Account endAccount = accountRepository.findById((long) 1).get();
        System.out.println("final balance ：" + endAccount.getBalance());
        Assertions.assertNotEquals(endAccount.getBalance(), BigDecimal.valueOf(150));
    }

    @Test
    public void addAccountMoneyWithVersion() throws InterruptedException {

        AccountWithVersion account = new AccountWithVersion((long) 1, "Bob", new BigDecimal(100));
        accountWithVersionRepository.save(account);

        CountDownLatch count = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> {
            String result = accountWithVersionService.addAccountMoney((long) 1, BigDecimal.valueOf(-50));
            System.out.println(Thread.currentThread().getName() + "，result : " + result);
            count.countDown();
        });
        TimeUnit.SECONDS.sleep(1);

        executorService.execute(() -> {
            String result = accountWithVersionService.addAccountMoney((long) 1, BigDecimal.valueOf(100));
            System.out.println(Thread.currentThread().getName() + "，result : " + result);
            count.countDown();
        });
        count.await(10, TimeUnit.SECONDS);

        AccountWithVersion endAccount = accountWithVersionRepository.findById((long) 1).get();
        System.out.println("final balance ：" + endAccount.getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(150), endAccount.getBalance());
    }
}
