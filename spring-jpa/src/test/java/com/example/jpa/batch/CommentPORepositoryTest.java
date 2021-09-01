package com.example.jpa.batch;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hodur
 * @date 2021/8/13
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentPORepositoryTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @Order(1)
    public void batchInsert() {

        long start = System.currentTimeMillis();

        List<CommentPO> l = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            CommentPO commentPO = new CommentPO();
            commentPO.setId((long) i);
            commentPO.setContent("content of demo batch#" + i);
            commentPO.setName("name of demo batch#" + i);
            l.add(commentPO);
        }

        commentRepository.batchInsert(l);
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    @Order(2)
    public void saveAll() {

        long start = System.currentTimeMillis();

        List<CommentPO> l = new ArrayList<>();
        for (int i = 5000; i < 10000; i++) {
            CommentPO commentPO = new CommentPO();
            commentPO.setId((long) i);
            commentPO.setContent("content of demo batch#" + i);
            commentPO.setName("name of demo batch#" + i);
            l.add(commentPO);
        }

        commentRepository.saveAll(l);
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    @Order(4)
    public void batchUpdate() {
        long start = System.currentTimeMillis();
        List<CommentPO> l = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            CommentPO commentPO = new CommentPO();
            commentPO.setId((long) i);
            commentPO.setContent("new content of demo batch#" + i);
            commentPO.setName("new name of demo batch#" + i);
            l.add(commentPO);
        }

        commentRepository.batchUpdate(l);
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    @Order(4)
    public void updateAll() {
        long start = System.currentTimeMillis();
        List<CommentPO> l = new ArrayList<>();
        for (int i = 5000; i < 10000; i++) {
            CommentPO commentPO = new CommentPO();
            commentPO.setId((long) i);
            commentPO.setContent("new content of demo batch#" + i);
            commentPO.setName("new name of demo batch#" + i);
            l.add(commentPO);
        }

        commentRepository.saveAll(l);
        System.out.println(System.currentTimeMillis() - start);
    }
}
