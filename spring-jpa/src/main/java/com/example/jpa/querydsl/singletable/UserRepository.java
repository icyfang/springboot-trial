package com.example.jpa.querydsl.singletable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author Hodur
 * @date 2021/7/15
 */
public interface UserRepository extends JpaRepository<UserPO, Long>, QuerydslPredicateExecutor<UserPO> {
}

