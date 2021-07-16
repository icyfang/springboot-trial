package com.example.jpa.version;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2021-01-06
 */
public interface AccountWithVersionRepository extends JpaRepository<AccountWithVersion, Long> {

    Account findByAccountName(String accountName);
}
