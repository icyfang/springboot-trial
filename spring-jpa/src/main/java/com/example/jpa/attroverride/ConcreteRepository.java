package com.example.jpa.attroverride;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcreteRepository extends JpaRepository<ConcretePO, Integer> {
}
