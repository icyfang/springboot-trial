package com.example.jpa.logicdelete;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogicDeleteRepositoryTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private LogicDeleteRepository logicDeleteRepository;

    @Test
    @Order(1)
    public void findAll() {

        MainPO po = new MainPO(1L, "content1", 1, Arrays.asList(new SubPO(1L, "name1", 1), new SubPO(2L, "name2", 0)));
        logicDeleteRepository.save(po);
        List<MainPO> all = logicDeleteRepository.findAll();
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(1, all.get(0).getSubPOS().size());
    }

    @Test
    @Order(2)
    public void delete() {

        logicDeleteRepository.deleteById(1L);
        List<MainPO> all = logicDeleteRepository.findAll();
        Assertions.assertEquals(0, all.size());
    }

    @Test
    @Order(3)
    public void empty() {

        logicDeleteRepository.emptySub();
        logicDeleteRepository.emptyMain();
        Assertions.assertEquals(0, logicDeleteRepository.countAll());
    }

}
