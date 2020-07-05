package com.example.springjpa;

import com.example.springjpa.multiDatasource.primary.PrimaryBean;
import com.example.springjpa.multiDatasource.primary.PrimaryRepository;
import com.example.springjpa.multiDatasource.secondary.SecondaryBean;
import com.example.springjpa.multiDatasource.secondary.SecondaryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringJpaApplication.class)
public class MultiDataSourceTest {

    @Autowired
    PrimaryRepository primaryRepository;

    @Autowired
    SecondaryRepository secondaryRepository;

    @Test
    public void save() {

        SecondaryBean s = new SecondaryBean();
        s.setName("second");
        secondaryRepository.save(s);

        PrimaryBean p = new PrimaryBean();
        p.setName("primary");
        primaryRepository.save(p);
    }

}
