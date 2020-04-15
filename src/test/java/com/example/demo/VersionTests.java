package com.example.demo;

import com.example.demo.bean.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VersionTests {

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void addAccountMoney() throws InterruptedException {

        CountDownLatch count = new CountDownLatch(2);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Account account = new Account();
        account.setBalance(new BigDecimal(100));
        account.setAccountName("张三的账户");
        accountRepository.save(account);

        executorService.execute(() -> {
            String result = accountService.addAccountMoney("张三的账户", BigDecimal.valueOf(-50));
            System.out.println(Thread.currentThread().getName() + "，result : " + result);
            count.countDown();
        });

        TimeUnit.SECONDS.sleep(1);

        executorService.execute(() -> {
            String result = accountService.addAccountMoney("张三的账户", BigDecimal.valueOf(100));
            System.out.println(Thread.currentThread().getName() + "，result : " + result);
            count.countDown();
        });

        count.await(10, TimeUnit.SECONDS);

        Account endAccount = accountRepository.findByAccountName("张三的账户");
        System.out.println("final balance ：" + endAccount.getBalance());

    }
}
