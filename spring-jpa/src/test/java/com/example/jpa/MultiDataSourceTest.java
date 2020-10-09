package com.example.jpa;

import com.example.jpa.multiDatasource.primary.PrimaryBean;
import com.example.jpa.multiDatasource.primary.PrimaryRepository;
import com.example.jpa.multiDatasource.secondary.SecondaryBean;
import com.example.jpa.multiDatasource.secondary.SecondaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
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
