package com.example.jpa.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findFirstByOrderByIdDesc();
}

