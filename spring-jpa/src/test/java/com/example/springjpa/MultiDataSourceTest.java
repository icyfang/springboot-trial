package com.example.springjpa;

import com.example.springjpa.bean.primary.PrimaryBean;
import com.example.springjpa.bean.secondary.SecondaryBean;
import com.example.springjpa.repository.primary.PrimaryRepository;
import com.example.springjpa.repository.secondary.SecondaryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiDataSourceTest {

    @Autowired
    PrimaryRepository primaryRepository;

    @Autowired
    SecondaryRepository secondaryRepository;

    @Test
    public void test() {

        SecondaryBean s = new SecondaryBean();
        s.setId(4);
        s.setName("second");

        PrimaryBean p = new PrimaryBean();
        p.setId(4);

        primaryRepository.save(p);
        p.setName("second");

        primaryRepository.save(p);
        secondaryRepository.save(s);
    }


}
