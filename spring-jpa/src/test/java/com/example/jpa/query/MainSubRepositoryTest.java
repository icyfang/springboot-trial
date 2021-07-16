package com.example.jpa.query;

import com.example.jpa.association.onetoone.MainPO;
import com.example.jpa.association.onetoone.MainSubPO;
import com.example.jpa.association.onetoone.SubPO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainSubRepositoryTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MainSubRepository mainSubRepository;

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
        mainSubRepository.save(mainPO);

        MainPO one = mainSubRepository.findById((long) 1).orElse(null);
        Assertions.assertNotNull(one);
        Assertions.assertEquals(1, (long) one.getId());
        Assertions.assertEquals(1001, (long) one.getSubPO().getId());
    }

    @Test
    @Order(2)
    public void getMainSubByMainId() {
        MainSubPO mainSubByMainId = mainSubRepository.getMainSubByMainId((long) 1);
        Assertions.assertEquals(1, (long) mainSubByMainId.getId());
        Assertions.assertEquals("main1", mainSubByMainId.getContent());
        Assertions.assertEquals(1001, (long) mainSubByMainId.getSubId());
        Assertions.assertEquals("sub1001", mainSubByMainId.getName());
    }

    @Test
    @Order(3)
    void getMainSubByMainIdLeftJoin() {
        MainSubPO mainSubByMainId = mainSubRepository.getMainSubByMainIdLeftJoin((long) 1);
        Assertions.assertEquals(1, (long) mainSubByMainId.getId());
        Assertions.assertEquals("main1", mainSubByMainId.getContent());
        Assertions.assertEquals(1001, (long) mainSubByMainId.getSubId());
        Assertions.assertEquals("sub1001", mainSubByMainId.getName());
    }

    @Test
    @Order(4)
    void getMainPOBySubNameIsNull() {

        MainPO mainSubByMainId = mainSubRepository.getMainPOBySubNameIsNotNull();
        Assertions.assertEquals(1, mainSubByMainId.getId());
    }

    @Test
    @Order(5)
    void queryMainSubByMainId() {
        MainSubPO mainSubByMainId = mainSubRepository.queryMainSubByMainId((long) 1);
        Assertions.assertEquals(1, (long) mainSubByMainId.getId());
        Assertions.assertEquals("main1", mainSubByMainId.getContent());
        Assertions.assertEquals(1001, (long) mainSubByMainId.getSubId());
        Assertions.assertEquals("sub1001", mainSubByMainId.getName());
    }

    @Test
    @Order(5)
    void queryMainSubByNullMainId() {
        MainSubPO mainSubByMainId = mainSubRepository.queryMainSubByMainId(null);
        Assertions.assertEquals(1, (long) mainSubByMainId.getId());
        Assertions.assertEquals("main1", mainSubByMainId.getContent());
        Assertions.assertEquals(1001, (long) mainSubByMainId.getSubId());
        Assertions.assertEquals("sub1001", mainSubByMainId.getName());
    }

    @Test
    @Order(6)
    void listMainSub() {

        Page<MainSubPO> page = mainSubRepository.ListMainSub(PageRequest.of(0, 2, Sort.by("id")));
        Assertions.assertTrue(page.getContent().size() <= 2);
    }

    @Test
    @Order(7)
    void listMainSubByNative() {

        Page<MainSubPO> page = mainSubRepository.ListMainSubByNative(PageRequest.of(0, 2, Sort.by("id")));
        Assertions.assertTrue(page.getContent().size() <= 2);
    }

}
