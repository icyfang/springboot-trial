package com.example.springjpa.repository;

import com.example.springjpa.bean.ConcreteVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcreteRepository extends JpaRepository<ConcreteVo, Integer> {
}
