package com.example.jpa.attroverride;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2020/04/11
 */
public interface ConcreteRepository extends JpaRepository<ConcretePO, Integer> {
}
