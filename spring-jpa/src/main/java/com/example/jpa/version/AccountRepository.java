package com.example.jpa.version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

/**
 * @author Hodur
 * @date 2020/07/06
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Account findByAccountName(String accountName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Account queryByAccountName(String accountName);
}
