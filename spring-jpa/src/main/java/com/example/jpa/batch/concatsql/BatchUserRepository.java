package com.example.jpa.batch.concatsql;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2021/8/13
 */
public interface BatchUserRepository extends JpaRepository<BatchUserPO, Long>, ConcatSqlRepository<BatchUserPO, Long> {
}
