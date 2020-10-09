package com.example.jpa.attrOverride;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcreteRepository extends JpaRepository<ConcretePO, Integer> {
}
