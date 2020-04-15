package com.example.demo.repository;

import com.example.demo.bean.ConcreteVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcreteRepository extends JpaRepository<ConcreteVo, Integer> {
}
