package com.example.jpa.transactional;

import com.example.jpa.attroverride.ConcreteRepository;
import com.example.jpa.attroverride.ConcreteRepository2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Hodur
 * @date 2021-04-28
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CallerServiceTest {

    @Autowired
    private CallerService service;

    @Autowired
    private ConcreteRepository repository;
    @Autowired
    private ConcreteRepository2 repository2;

    @BeforeEach
    public void clear() {
        repository.deleteAll();
        repository2.deleteAll();
    }

    @Test
    void requireNew1() {
        try {
            service.requireNew1();
        } catch (Exception e) {

        } finally {
            Assertions.assertEquals(0, repository.findAll().size());
            Assertions.assertEquals(1, repository2.findAll().size());
        }
    }

    @Test
    void requireNew2() {
        try {
            service.requireNew2();
        } catch (Exception e) {

        } finally {
            Assertions.assertEquals(0, repository.findAll().size());
            Assertions.assertEquals(0, repository2.findAll().size());
        }
    }

    @Test
    void requireNew3() {
        try {
            service.requireNew3();
        } catch (Exception e) {

        } finally {
            Assertions.assertEquals(1, repository.findAll().size());
            Assertions.assertEquals(0, repository2.findAll().size());
        }
    }

    @Test
    void nested1() {
        try {
            service.nested1();
        } catch (Exception e) {

        } finally {
            Assertions.assertEquals(0, repository.findAll().size());
            Assertions.assertEquals(0, repository2.findAll().size());
        }
    }

    @Test
    void nested2() {
        try {
            service.nested2();
        } catch (Exception e) {

        } finally {
            Assertions.assertEquals(0, repository.findAll().size());
            Assertions.assertEquals(0, repository2.findAll().size());
        }
    }

    @Test
    void nested3() {
        try {
            service.nested3();
        } catch (Exception e) {

        } finally {
            Assertions.assertEquals(1, repository.findAll().size());
            Assertions.assertEquals(0, repository2.findAll().size());
        }
    }

    @Test
    void notSupported1() {
        try {
            service.notSupported1();
        } catch (Exception e) {

        } finally {
            Assertions.assertEquals(0, repository.findAll().size());
            Assertions.assertEquals(1, repository2.findAll().size());
        }
    }
}