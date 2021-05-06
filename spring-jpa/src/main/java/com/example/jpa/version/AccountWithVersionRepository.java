package com.example.jpa.version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @date 2021-01-06
 */
@Component
public interface AccountWithVersionRepository extends JpaRepository<AccountWithVersion, Long> {

    Account findByAccountName(String accountName);
}
