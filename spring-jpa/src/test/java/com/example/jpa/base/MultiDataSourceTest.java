package com.example.jpa.base;

import com.example.jpa.multiDatasource.primary.PrimaryPO;
import com.example.jpa.multiDatasource.primary.PrimaryRepository;
import com.example.jpa.multiDatasource.secondary.SecondaryPO;
import com.example.jpa.multiDatasource.secondary.SecondaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MultiDataSourceTest {

    @Autowired
    PrimaryRepository primaryRepository;

    @Autowired
    SecondaryRepository secondaryRepository;

    @Test
    public void save() {

        SecondaryPO s = new SecondaryPO();
        s.setName("second");
        secondaryRepository.save(s);

        PrimaryPO p = new PrimaryPO();
        p.setName("primary");
        primaryRepository.save(p);
    }

}
