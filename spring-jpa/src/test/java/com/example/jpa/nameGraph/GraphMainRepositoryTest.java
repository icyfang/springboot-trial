package com.example.jpa.nameGraph;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

/**
 * @author Hodur
 * @date 2021/7/13
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GraphMainRepositoryTest {

    @Autowired
    private GraphMainRepository graphMainRepository;

    @Autowired
    private GraphSubRepository graphSubRepository;

    @Test
    @Order(2)
    public void initGraph() {

        GraphSubPO subPO = new GraphSubPO();
        subPO.setId((long) 1001);
        subPO.setName("sub1001");

        GraphSubPO subPO2 = new GraphSubPO();
        subPO2.setId((long) 1002);
        subPO2.setName("sub1002");

        GraphMainPO mainPO = new GraphMainPO();
        mainPO.setId((long) 1);
        mainPO.setContent("main1");
        mainPO.setSubPOList(Arrays.asList(subPO, subPO2));
        graphMainRepository.save(mainPO);

        GraphMainPO mainPO2 = new GraphMainPO();
        mainPO2.setId((long) 2);
        mainPO2.setContent("main2");
        mainPO2.setSubPOList(Arrays.asList(subPO, subPO2));
        graphMainRepository.save(mainPO2);
    }

    @Test
    @Order(3)
    public void query() {
        List<GraphMainPO> all = graphMainRepository.findAll();

    }

    @Test
    @Order(4)
    public void querySub() {
        List<GraphSubPO> all = graphSubRepository.findAll();
    }

    @Test
    @Order(4)
    public void queryPage() {
        Page<GraphMainPO> all = graphMainRepository.findAll(PageRequest.of(0, 1));
        System.out.println(all.getContent().size());
    }
}
