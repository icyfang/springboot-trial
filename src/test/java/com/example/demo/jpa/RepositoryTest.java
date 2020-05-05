package com.example.demo.jpa;

import com.example.demo.jpa.bean.Account;
import com.example.demo.jpa.bean.ConcreteVo;
import com.example.demo.jpa.bean.ConcreteVo2;
import com.example.demo.jpa.bean.Forum;
import com.example.demo.jpa.repository.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountRepository2 accountRepository2;
    @Autowired
    private ConcreteRepository concreteRepository;
    @Autowired
    private Concrete2Repository concrete2Repository;

    @Test
    public void test() {

        // 创建10条记录
        accountRepository.save(new Account("AAA", new BigDecimal(10)));
        accountRepository.save(new Account("BBB", new BigDecimal(20)));
        accountRepository.save(new Account("CCC", new BigDecimal(30)));
        accountRepository.save(new Account("DDD", new BigDecimal(40)));
        accountRepository.save(new Account("EEE", new BigDecimal(50)));
        accountRepository.save(new Account("FFF", new BigDecimal(60)));
        accountRepository.save(new Account("GGG", new BigDecimal(70)));
        accountRepository.save(new Account("HHH", new BigDecimal(80)));
        accountRepository.save(new Account("HHH", new BigDecimal(80)));
        accountRepository.save(new Account("HHH", new BigDecimal(80)));
        accountRepository.save(new Account("HHH", new BigDecimal(80)));
        accountRepository.save(new Account("III", new BigDecimal(90)));
        accountRepository.save(new Account("JJJ", new BigDecimal(100)));

        Assert.assertEquals(3, accountRepository.findTop3ByAccountName("HHH").size());
        Assert.assertEquals(3, accountRepository.findFirst3ByAccountName("HHH").size());

        // 测试findAll, 查询所有记录
        Assert.assertEquals(13, accountRepository.findAll().size());

        accountRepository.deleteByAccountName("HHH");

        Assert.assertEquals(9, accountRepository.findAll().size());

        // 测试findByName, 查询姓名为FFF的Account
        Assert.assertEquals(60, accountRepository.findByAccountName("FFF").getBalance().longValue());

        // 测试findAccount, 查询姓名为FFF的Account
        Assert.assertEquals(60, accountRepository.findAccount("FFF").getBalance().longValue());

        // 测试findByNameAndAge, 查询姓名为FFF并且年龄为60的Account
        Assert.assertEquals("FFF",
                accountRepository.findByAccountNameAndBalance("FFF", new BigDecimal(60)).getAccountName());

        // 测试findByNameAndAge, 查询姓名为FFF并且年龄为60的Account
        Assert.assertEquals(2, accountRepository.find1(90).size());

        // 测试删除姓名为AAA的Account
        accountRepository.delete(accountRepository.findByAccountName("AAA"));

        // 测试findAll, 查询所有记录, 验证上面的删除是否成功
        Assert.assertEquals(8, accountRepository.findAll().size());
    }

    @Test
    public void test2() {
        accountRepository2.save(new Account("AAA", new BigDecimal(10)));
        System.out.println(accountRepository2.findAll());
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
        ConcreteVo vo = new ConcreteVo();
        vo.setDesp("desp");
        vo.setName("name");
        concreteRepository.save(vo);

        ConcreteVo2 vo1 = new ConcreteVo2();
        vo1.setDesp("desp");
        vo1.setName("name");
        concrete2Repository.save(vo1);
    }

    @Test
    public void test5() {
        ConcreteVo2 vo = new ConcreteVo2();
        vo.setDesp("desp");
        vo.setName("name");
        concrete2Repository.save(vo);
    }
}
