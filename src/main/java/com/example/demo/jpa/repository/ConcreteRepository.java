package com.example.demo.jpa.repository;

import com.example.demo.jpa.bean.ConcreteVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcreteRepository extends JpaRepository<ConcreteVo, Integer> {
}
