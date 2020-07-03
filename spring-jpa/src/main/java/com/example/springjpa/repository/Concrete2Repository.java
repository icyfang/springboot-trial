package com.example.springjpa.repository;

import com.example.springjpa.bean.ConcreteVo2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Concrete2Repository extends JpaRepository<ConcreteVo2, Integer> {
}
