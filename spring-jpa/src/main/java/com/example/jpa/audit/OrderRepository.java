package com.example.jpa.audit;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2021/01/10
 */
public interface OrderRepository extends JpaRepository<OrderPO, Long> {

    OrderPO findFirstByOrderByIdDesc();
}

