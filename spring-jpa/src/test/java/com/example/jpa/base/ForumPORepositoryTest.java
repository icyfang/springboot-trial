package com.example.jpa.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ForumPORepositoryTest {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ForumRepository2 forumRepository2;

    @Test
    @Order(1)
    public void init() {
        forumRepository
                .save(new ForumPO((long) 1, "user1", "pwd", new BigDecimal(10), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository
                .save(new ForumPO((long) 2, "user2", "pwd", new BigDecimal(20), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository
                .save(new ForumPO((long) 20, "user2", "pwd", new BigDecimal(200), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository
                .save(new ForumPO((long) 3, "user3", "pwd", new BigDecimal(30), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository
                .save(new ForumPO((long) 4, "user4", "pwd", new BigDecimal(40), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository2
                .save(new ForumPO((long) 5, "user5", "pwd", new BigDecimal(50), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository2
                .save(new ForumPO((long) 6, "user6", "pwd", new BigDecimal(60), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository2
                .save(new ForumPO((long) 7, "user7", "pwd", new BigDecimal(70), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository2
                .save(new ForumPO((long) 8, "user8", "pwd", new BigDecimal(80), "street1", "city1", "country1", "title1", "comment1", 10));
    }

    @Test
    @Order(2)
    public void findForum() {
        ForumPO forumPO = forumRepository.findForum("user1");
        Long aLong = Optional.ofNullable(forumPO).map(ForumPO::getId).orElse((long) 0);
        Assertions.assertEquals(aLong.longValue(), 1);
    }

    @Test
    @Order(3)
    public void findNameNotEmpty() {
        List<ForumPO> nameNotEmpty = forumRepository.findNameNotEmpty();
        Assertions.assertEquals(nameNotEmpty.size(), 9);
    }

    @Test
    @Order(4)
    public void findByUserName() {
        List<ForumPO> forumPOS = forumRepository.findByUsername("user2", Sort.by(Sort.Direction.DESC, "id"));
        Long aLong = Optional.ofNullable(forumPOS.get(0)).map(ForumPO::getId).orElse((long) 0);
        Assertions.assertEquals(aLong.longValue(), 9);
    }

    @Test
    @Order(5)
    public void findByUserNameAndAccount() {
        ForumPO forumPO = forumRepository.findByUsernameAndAccount("user2", new BigDecimal(20));
        Long aLong = Optional.ofNullable(forumPO).map(ForumPO::getId).orElse((long) 0);
        Assertions.assertEquals(aLong.longValue(), 2);
    }

    @Test
    @Order(6)
    public void findByAccountBetween() {
        List<ForumPO> forumPOS = forumRepository.findByAccountBetween(new BigDecimal(30), new BigDecimal(60));
        Assertions.assertEquals(forumPOS.size(), 4);
    }

    @Test
    @Order(7)
    public void findTop3ByUsername() {
        List<ForumPO> forumPOS = forumRepository.findTop3ByUsername("user2");
        Assertions.assertEquals(forumPOS.size(), 2);
    }

    @Test
    @Order(8)
    public void findLast3ByUsername() {
        List<ForumPO> forumPOS = forumRepository.findLast3ByUsername("user2");
        Assertions.assertEquals(forumPOS.size(), 2);
    }

    @Test
    @Order(9)
    public void deleteAllInBatch() {
        forumRepository.deleteAllInBatch();
        List<ForumPO> all = forumRepository.findAll();
        Assertions.assertEquals(all.size(), 0);
    }
}
