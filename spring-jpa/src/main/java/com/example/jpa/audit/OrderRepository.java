package com.example.jpa.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderPO, Long> {

    OrderPO findFirstByOrderByIdDesc();
}

