package com.example.jpa.association.manytomany;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/manyToMany")
public class ManyToManyController {

    private final ManyToManyMainRepository mainRepository;

    private final ManyToManySubRepository subRepository;

    public ManyToManyController(ManyToManyMainRepository mainRepository, ManyToManySubRepository subRepository) {
        this.mainRepository = mainRepository;
        this.subRepository = subRepository;
    }

    @GetMapping("/main")
    public MainPO getMainPO() {
        SubPO subPO = new SubPO();
        subPO.setId((long) 1001);
        subPO.setName("sub1001");

        MainPO mainPO = new MainPO();
        mainPO.setId((long) 1);
        mainPO.setContent("main1");

        mainPO.setSubPO(Collections.singletonList(subPO));
        subPO.setMainPOS(Collections.singletonList(mainPO));
        mainRepository.save(mainPO);

        return mainRepository.findById((long) 1).orElse(null);
    }

    @GetMapping("/sub")
    public SubPO getSubPO() {
        MainPO mainPO = new MainPO();
        mainPO.setId((long) 2001);
        mainPO.setContent("main2");

        SubPO subPO = new SubPO();
        subPO.setId((long) 2);
        subPO.setName("sub2");

        subPO.setMainPOS(Collections.singletonList(mainPO));
        mainPO.setSubPO(Collections.singletonList(subPO));
        subRepository.save(subPO);

        return subRepository.findById((long) 2).orElse(null);
    }
}
