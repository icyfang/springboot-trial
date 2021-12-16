package com.example.jpa.association;

import com.example.jpa.association.onetomany.bidirectional.BiOneToManyMainRepository;
import com.example.jpa.association.onetomany.bidirectional.BiOneToManySubRepository;
import com.example.jpa.association.onetomany.bidirectional.MainPO;
import com.example.jpa.association.onetomany.bidirectional.SubPO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OneToManyTest {

    @Autowired
    private BiOneToManyMainRepository mainRepository;

    @Autowired
    private BiOneToManySubRepository subRepository;

    @Order(1)
    @Test
    public void testSave() {

        SubPO subPO = new SubPO();
        subPO.setId((long) 1001);
        subPO.setName("sub1001");

        MainPO mainPO = new MainPO();
        mainPO.setId((long) 1);
        mainPO.setContent("main1");
        mainPO.setSubPOS(Collections.singletonList(subPO));
        mainRepository.save(mainPO);

        MainPO one = mainRepository.findById((long) 1).get();
        Assertions.assertEquals(1, (long) one.getId());
        Assertions.assertEquals(1001, (long) one.getSubPOS().get(0).getId());
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
        mainPO.setSubPOS(Collections.singletonList(subPO));
        mainRepository.save(mainPO);

        MainPO one = mainRepository.getOne((long) 1);
        Assertions.assertEquals(1, (long) one.getId());
        Assertions.assertEquals(1002, (long) one.getSubPOS().get(0).getId());
    }

    @Order(3)
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

    @Order(4)
    @Test
    public void testSubSave() {

        MainPO mainPO = new MainPO();
        mainPO.setId((long) 2001);
        mainPO.setContent("main2");

        SubPO subPO = new SubPO();
        subPO.setId((long) 2);
        subPO.setName("sub2");
        subPO.setMainPO(mainPO);

        subRepository.save(subPO);

        SubPO one = subRepository.findById((long) 2).get();
        Assertions.assertEquals(2, (long) one.getId());
        Assertions.assertEquals(2001, (long) one.getMainPO().getId());
    }

    @Order(5)
    @Test
    public void testSubUpdate() {

        MainPO mainPO = new MainPO();
        mainPO.setId((long) 2002);
        mainPO.setContent("main2");

        SubPO subPO = new SubPO();
        subPO.setId((long) 2);
        subPO.setName("sub2");
        subPO.setMainPO(mainPO);

        subRepository.save(subPO);

        SubPO one = subRepository.findById((long) 2).get();
        Assertions.assertEquals(2, (long) one.getId());
        Assertions.assertEquals(2002, (long) one.getMainPO().getId());
    }

    @Order(6)
    @Test
    public void testSubDelete() {

        Optional<SubPO> main = subRepository.findById((long) 2);
        Assertions.assertTrue(main.isPresent());

        subRepository.deleteById((long) 2);
        Optional<SubPO> byId = subRepository.findById((long) 2);
        Assertions.assertFalse(byId.isPresent());

        Optional<MainPO> subPO = mainRepository.findById((long) 2002);
        Assertions.assertFalse(subPO.isPresent());
    }

}
