package com.example.jpa.audit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Hodur
 * @date 2021-01-06
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuditTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RecordRepository recordRepository;

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testAutoFill() {
        OrderPO orderPO = new OrderPO();
        orderRepository.save(orderPO);

        OrderPO o = orderRepository.findFirstByOrderByIdDesc();
        Assertions.assertEquals(1, o.getCreateUser().getId());
        Assertions.assertEquals(1, o.getUpdateUser().getId());
        Assertions.assertNotNull(o.getCreateTime());
        Assertions.assertNotNull(o.getUpdateTime());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testPersist() {
        OrderPO orderPO = new OrderPO();
        orderRepository.save(orderPO);

        RecordPO o = recordRepository.findFirstByOrderByIdDesc();
        Assertions.assertEquals("persist", o.getMethod());
        Assertions.assertNotNull(o.getContent());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testGetAndUpdate() {

        OrderPO orderPO = orderRepository.findFirstByOrderByIdDesc();

        RecordPO o = recordRepository.findFirstByOrderByIdDesc();
        Assertions.assertEquals("query", o.getMethod());
        Assertions.assertEquals(orderPO.toString(), o.getContent());

        orderPO.setStatus(1);
        orderRepository.save(orderPO);

        o = recordRepository.findFirstByOrderByIdDesc();
        Assertions.assertEquals("update", o.getMethod());
        Assertions.assertNotNull(o.getContent());

    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void testRemove() {

        OrderPO orderPO = orderRepository.findFirstByOrderByIdDesc();
        orderRepository.delete(orderPO);

        RecordPO o = recordRepository.findFirstByOrderByIdDesc();
        Assertions.assertEquals("remove", o.getMethod());
        Assertions.assertEquals(orderPO.toString(), o.getContent());
    }
}
