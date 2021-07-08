package com.example.jpa.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ForumRepositoryTest {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ForumRepository2 forumRepository2;

    @Test
    public void doTest() {
        init();
        findForum();
        findNameNotEmpty();
        findByUserName();
        findByUserNameAndAccount();
        findByAccountBetween();
        findTop3ByUsername();
        findLast3ByUsername();
        deleteAllInBatch();
    }

    public void init() {
        forumRepository.save(new Forum((long) 1, "user1", "pwd", new BigDecimal(10), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository.save(new Forum((long) 2, "user2", "pwd", new BigDecimal(20), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository.save(new Forum((long) 20, "user2", "pwd", new BigDecimal(200), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository.save(new Forum((long) 3, "user3", "pwd", new BigDecimal(30), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository.save(new Forum((long) 4, "user4", "pwd", new BigDecimal(40), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository2.save(new Forum((long) 5, "user5", "pwd", new BigDecimal(50), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository2.save(new Forum((long) 6, "user6", "pwd", new BigDecimal(60), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository2.save(new Forum((long) 7, "user7", "pwd", new BigDecimal(70), "street1", "city1", "country1", "title1", "comment1", 10));
        forumRepository2.save(new Forum((long) 8, "user8", "pwd", new BigDecimal(80), "street1", "city1", "country1", "title1", "comment1", 10));
    }

    public void findForum() {
        Forum forum = forumRepository.findForum("user1");
        Long aLong = Optional.ofNullable(forum).map(Forum::getId).orElse((long) 0);
        Assertions.assertEquals(aLong.longValue(), 1);
    }

    public void findNameNotEmpty() {
        List<Forum> nameNotEmpty = forumRepository.findNameNotEmpty();
        Assertions.assertEquals(nameNotEmpty.size(), 9);
    }

    public void findByUserName() {
        List<Forum> forums = forumRepository.findByUserName("user2", Sort.by(Sort.Direction.DESC, "id"));
        Long aLong = Optional.ofNullable(forums.get(0)).map(Forum::getId).orElse((long) 0);
        Assertions.assertEquals(aLong.longValue(), 9);
    }

    public void findByUserNameAndAccount() {
        Forum forum = forumRepository.findByUserNameAndAccount("user2", new BigDecimal(20));
        Long aLong = Optional.ofNullable(forum).map(Forum::getId).orElse((long) 0);
        Assertions.assertEquals(aLong.longValue(), 2);
    }

    public void findByAccountBetween() {
        List<Forum> forums = forumRepository.findByAccountBetween(new BigDecimal(30), new BigDecimal(60));
        Assertions.assertEquals(forums.size(), 4);
    }

    public void findTop3ByUsername() {
        List<Forum> forums = forumRepository.findTop3ByUserName("user2");
        Assertions.assertEquals(forums.size(), 2);
    }

    public void findLast3ByUsername() {
        List<Forum> forums = forumRepository.findLast3ByUserName("user2");
        Assertions.assertEquals(forums.size(), 2);
    }

    public void deleteAllInBatch() {
        forumRepository.deleteAllInBatch();
        List<Forum> all = forumRepository.findAll();
        Assertions.assertEquals(all.size(), 0);
    }
}
