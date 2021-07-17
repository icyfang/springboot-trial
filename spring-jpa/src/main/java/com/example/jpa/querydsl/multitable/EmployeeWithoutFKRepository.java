package com.example.jpa.querydsl.multitable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author Hodur
 * @date 2021/7/17
 */
public interface EmployeeWithoutFKRepository extends JpaRepository<EmployeeWithoutFKPO, Long>, QuerydslPredicateExecutor<EmployeeWithoutFKPO> {

}
