package com.example.demo.jpa.service;

import com.example.demo.jpa.bean.Account;
import com.example.demo.jpa.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl {

    @Resource
    private AccountRepository accountRepository;

    @Transactional(rollbackFor = Exception.class)
    public String addAccountMoney(String accountName, BigDecimal money) {

        System.out.println(Thread.currentThread().getName() + "，addAccountMoney start...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Account account = accountRepository.findByAccountName(accountName);
        System.out.println(Thread.currentThread().getName() + "，find balance : " + account.getBalance());
        account.setBalance(account.getBalance().add(money));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Account result = accountRepository.save(account);
        System.out.println(Thread.currentThread().getName() + "， update balance end ,balance : " + result.getBalance());

        System.out.println(Thread.currentThread().getName() + "，addAccountMoney sleep...");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "，addAccountMoney end...");

        return "success";
    }

}
