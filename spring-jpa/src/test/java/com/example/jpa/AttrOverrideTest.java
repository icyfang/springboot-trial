package com.example.jpa;

import com.example.jpa.attrOverride.ConcretePO;
import com.example.jpa.attrOverride.ConcretePO2;
import com.example.jpa.attrOverride.ConcreteRepository;
import com.example.jpa.attrOverride.ConcreteRepository2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AttrOverrideTest {

    @Autowired
    private ConcreteRepository concreteRepository;
    @Autowired
    private ConcreteRepository2 concreteRepository2;

    @Test
    public void save() {
        ConcretePO vo = new ConcretePO();
        vo.setDesp("desp");
        vo.setName("name");
        concreteRepository.save(vo);

        ConcretePO2 vo1 = new ConcretePO2();
        vo1.setDesp("desp");
        vo1.setName("name");
        concreteRepository2.save(vo1);
    }

}
