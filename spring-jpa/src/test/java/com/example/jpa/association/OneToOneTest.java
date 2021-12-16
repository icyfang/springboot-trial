package com.example.jpa.association;

import com.example.jpa.association.onetoone.MainPO;
import com.example.jpa.association.onetoone.OneToOneMainRepository;
import com.example.jpa.association.onetoone.OneToOneSubRepository;
import com.example.jpa.association.onetoone.SubPO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OneToOneTest {

    @Autowired
    private OneToOneMainRepository mainRepository;

    @Autowired
    private OneToOneSubRepository subRepository;

    @Order(1)
    @Test
    public void testSave() {

        SubPO subPO = new SubPO();
        subPO.setId((long) 1001);
        subPO.setName("sub1001");

        MainPO mainPO = new MainPO();
        mainPO.setId((long) 1);
        mainPO.setContent("main1");
        mainPO.setSubPO(subPO);
        mainRepository.save(mainPO);

        MainPO one = mainRepository.findById((long) 1).orElse(null);
        Assertions.assertNotNull(one);
        Assertions.assertEquals(1, (long) one.getId());
        Assertions.assertEquals(1001, (long) one.getSubPO().getId());
    }

    @Order(2)
    @Test
    public void testUpdate() {

        SubPO subPO = new SubPO();
        subPO.setId((long) 1002);
        subPO.setName("sub1002");

        MainPO mainPO = new MainPO();
        mainPO.setId((long) 1);
        mainPO.setContent("main2");
        mainPO.setSubPO(subPO);
        mainRepository.save(mainPO);

        MainPO one = mainRepository.getOne((long) 1);
        Assertions.assertEquals(1, (long) one.getId());
        Assertions.assertEquals(1002, (long) one.getSubPO().getId());
    }

    @Order(5)
    @Test
    public void testDelete() {
        Optional<MainPO> main = mainRepository.findById((long) 1);
        Assertions.assertTrue(main.isPresent());

        mainRepository.deleteById((long) 1);
        Optional<MainPO> byId = mainRepository.findById((long) 1);
        Assertions.assertFalse(byId.isPresent());

        Optional<SubPO> subPO = subRepository.findById((long) 1002);
        Assertions.assertFalse(subPO.isPresent());
    }
}
