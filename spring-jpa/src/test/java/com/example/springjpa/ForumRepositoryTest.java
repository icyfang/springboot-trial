package com.example.springjpa;

import com.example.springjpa.base.Forum;
import com.example.springjpa.base.ForumRepository;
import com.example.springjpa.base.ForumRepository2;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class ForumRepositoryTest {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ForumRepository2 forumRepository2;

    @BeforeClass
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

    @Test
    void findForum() {
        Forum forum = forumRepository.findForum("user1");
        Long aLong = Optional.ofNullable(forum).map(Forum::getId).orElse((long) 0);
        Assert.assertEquals(aLong.longValue(), 1);
    }

    @Test
    void findNameNotEmpty() {
        List<Forum> nameNotEmpty = forumRepository.findNameNotEmpty();
        Assert.assertEquals(nameNotEmpty.size(), 9);
    }

    @Test
    void findByUserName() {
        List<Forum> forums = forumRepository.findByUserName("user2", Sort.by(Sort.Direction.DESC, "id"));
        Long aLong = Optional.ofNullable(forums.get(0)).map(Forum::getId).orElse((long) 0);
        Assert.assertEquals(aLong.longValue(), 20);
    }

    @Test
    void findByUserNameAndAccount() {
        Forum forum = forumRepository.findByUserNameAndAccount("user2", new BigDecimal(20));
        Long aLong = Optional.ofNullable(forum).map(Forum::getId).orElse((long) 0);
        Assert.assertEquals(aLong.longValue(), 2);
    }

    @Test
    void findByAccountBetween() {
        List<Forum> forums = forumRepository.findByAccountBetween(new BigDecimal(30), new BigDecimal(60));
        Assert.assertEquals(forums.size(), 4);
    }

    @Test
    void findTop3ByUsername() {
        List<Forum> forums = forumRepository.findTop3ByUserName("user2");
        Assert.assertEquals(forums.size(), 2);
    }

    @Test
    void findLast3ByUsername() {
        List<Forum> forums = forumRepository.findLast3ByUserName("user2");
        Assert.assertEquals(forums.size(), 2);
    }

    @Test
    void deleteByUserName() {
        forumRepository.deleteByUserName("user8");
        List<Forum> all = forumRepository.findAll();
        Assert.assertEquals(all.size(), 8);
    }
}