package com.example.jpa.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author shanghongcai
 * @since 2021-01-06
 */
@Service
public class AccountWithVersionService {

    @Autowired
    private AccountWithVersionRepository accountWithVersionRepository;

    @Transactional(rollbackFor = Exception.class)
    public String addAccountMoney(Long id, BigDecimal money) {

        try {
            updateAccount(id, money);
            return "success";
        } catch (ObjectOptimisticLockingFailureException e) {
            return "fail";
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAccount(Long id, BigDecimal money) {
        System.out.println(Thread.currentThread().getName() + ",addAccountMoney start...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AccountWithVersion account = accountWithVersionRepository.findById(id).get();
        System.out.println(Thread.currentThread().getName() + ",find balance : " + account.getBalance());
        account.setBalance(account.getBalance().add(money));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AccountWithVersion result = accountWithVersionRepository.save(account);
        System.out.println(Thread.currentThread().getName() + ", update balance end ,balance : " + result.getBalance());

        System.out.println(Thread.currentThread().getName() + ",addAccountMoney sleep...");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + ",addAccountMoney end...");
    }

}
