package com.example.jpa.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class AccountService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AccountRepository accountRepository;

    @Transactional(rollbackFor = Exception.class)
    public String addAccountMoney(Long id, BigDecimal money) {

        System.out.println(Thread.currentThread().getName() + ",addAccountMoney start...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Account account = accountRepository.findById(id).get();
        System.out.println(Thread.currentThread().getName() + ",find balance : " + account.getBalance());
        account.setBalance(account.getBalance().add(money));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Account result = accountRepository.save(account);
        System.out.println(Thread.currentThread().getName() + ", update balance end ,balance : " + result.getBalance());

        System.out.println(Thread.currentThread().getName() + ",addAccountMoney sleep...");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + ",addAccountMoney end...");

        return "success";
    }

}
