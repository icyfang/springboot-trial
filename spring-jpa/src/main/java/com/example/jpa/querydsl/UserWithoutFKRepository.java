package com.example.jpa.querydsl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author Hodur
 * @date 2021/7/17
 */
public interface UserWithoutFKRepository extends JpaRepository<UserWithoutFKPO, Long>, QuerydslPredicateExecutor<UserWithoutFKPO> {

}
