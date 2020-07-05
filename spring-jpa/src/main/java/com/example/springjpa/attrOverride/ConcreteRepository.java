package com.example.springjpa.attrOverride;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcreteRepository extends JpaRepository<ConcreteVo, Integer> {
}
