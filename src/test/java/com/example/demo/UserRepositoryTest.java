package com.example.demo;

import com.example.demo.bean.ConcVo;
import com.example.demo.bean.ConcVo2;
import com.example.demo.bean.Forum;
import com.example.demo.bean.User;
import com.example.demo.repository.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository2 userRepository2;

    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private ConcreteRepository concreteRepository;
    @Autowired
    private Concrete2Repository concrete2Repository;

    @Test
    public void test() throws Exception {

        // 创建10条记录
        userRepository.save(new User("AAA", 10));
        userRepository.save(new User("BBB", 20));
        userRepository.save(new User("CCC", 30));
        userRepository.save(new User("DDD", 40));
        userRepository.save(new User("EEE", 50));
        userRepository.save(new User("FFF", 60));
        userRepository.save(new User("GGG", 70));
        userRepository.save(new User("HHH", 80));
        userRepository.save(new User("HHH", 80));
        userRepository.save(new User("HHH", 80));
        userRepository.save(new User("HHH", 80));
        userRepository.save(new User("III", 90));
        userRepository.save(new User("JJJ", 100));

        Assert.assertEquals(3, userRepository.findTop3ByName("HHH").size());
        Assert.assertEquals(3, userRepository.findFirst3ByName("HHH").size());

        // 测试findAll, 查询所有记录
        Assert.assertEquals(13, userRepository.findAll().size());

        userRepository.deleteByName("HHH");

        Assert.assertEquals(9, userRepository.findAll().size());

        // 测试findByName, 查询姓名为FFF的User
        Assert.assertEquals(60, userRepository.findByName("FFF").getAge().longValue());

        // 测试findUser, 查询姓名为FFF的User
        Assert.assertEquals(60, userRepository.findUser("FFF").getAge().longValue());

        // 测试findByNameAndAge, 查询姓名为FFF并且年龄为60的User
        Assert.assertEquals("FFF", userRepository.findByNameAndAge("FFF", 60).getName());

        // 测试findByNameAndAge, 查询姓名为FFF并且年龄为60的User
        Assert.assertEquals(2, userRepository.find1(90).size());

        // 测试删除姓名为AAA的User
        userRepository.delete(userRepository.findByName("AAA"));

        // 测试findAll, 查询所有记录, 验证上面的删除是否成功
        Assert.assertEquals(8, userRepository.findAll().size());
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
    }

    @Test
    public void test2() throws Exception {
        userRepository2.save(new User("AAA", 10));
        System.out.println(userRepository2.findAll());
    }

    @Test
    public void test3() {
        Forum forum = new Forum();
        forum.setCity("city");
        forum.setComments("comment");
        forum.setComments_length(20);
        forum.setCountry("country");
        forum.setPassword("pass");
        forum.setStreet("street");
        forum.setUsername("user");
        forum.setTitle("title");
        forumRepository.save(forum);
    }

    @Test
    public void test4() {
        ConcVo vo = new ConcVo();
        vo.setDesp("desp");
        vo.setName("name");
        concreteRepository.save(vo);
    }

    @Test
    public void test5() {
        ConcVo2 vo = new ConcVo2();
        vo.setDesp("desp");
        vo.setName("name");
        concrete2Repository.save(vo);
    }
}
