package com.example.springjpa;

import com.example.springjpa.attrOverride.Concrete2Repository;
import com.example.springjpa.attrOverride.ConcreteRepository;
import com.example.springjpa.attrOverride.ConcreteVo;
import com.example.springjpa.attrOverride.ConcreteVo2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AttrOverrideTest {

    @Autowired
    private ConcreteRepository concreteRepository;
    @Autowired
    private Concrete2Repository concrete2Repository;

    @Test
    public void save() {
        ConcreteVo vo = new ConcreteVo();
        vo.setDesp("desp");
        vo.setName("name");
        concreteRepository.save(vo);

        ConcreteVo2 vo1 = new ConcreteVo2();
        vo1.setDesp("desp");
        vo1.setName("name");
        concrete2Repository.save(vo1);
    }

}
