package com.example.jpa.batch;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hodur
 * @date 2021/8/16
 */
@RestController
@RequestMapping("/batch")
public class BatchController {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DemoBatchRepository demoBatchRepository;

    @PostMapping("/saveAll")
    public void saveAll() {
        List<DemoBatch> l = new ArrayList<>();
        for (int i = 5000; i < 10000; i++) {
            DemoBatch demoBatch = new DemoBatch();
            demoBatch.setId((long) i);
            demoBatch.setContent("content of demo batch#" + i);
            demoBatch.setName("name of demo batch#" + i);
            l.add(demoBatch);
        }

        demoBatchRepository.saveAll(l);
    }

    @PostMapping("/batchInsert")
    public void batchInsert() {
        List<DemoBatch> l = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            DemoBatch demoBatch = new DemoBatch();
            demoBatch.setId((long) i);
            demoBatch.setContent("content of demo batch#" + i);
            demoBatch.setName("name of demo batch#" + i);
            l.add(demoBatch);
        }

        demoBatchRepository.batchInsert(l);
    }

    EntityManager entityManager = ApplicationContextHolder.getApplicationContext()
                                                          .getBean("entityManagerSecondary", EntityManager.class);

    @PostMapping("/nativeQuery")
    public void nativeQuery() {
        StringBuilder sb = new StringBuilder("insert into demo_batch(id, content, name) values ");
        List<DemoBatch> l = new ArrayList<>();
        for (int i = 10000; i < 15000; i++) {
            sb.append("(")
              .append(i)
              .append(",'content of demo batch#")
              .append(i)
              .append("','name of demo batch#")
              .append(i)
              .append("'),");
        }
        sb.deleteCharAt(sb.length() - 1);

        Session unwrap = entityManager.unwrap(Session.class);
        unwrap.setJdbcBatchSize(1000);
        try {
            unwrap.getTransaction().begin();
            Query query = entityManager.createNativeQuery(sb.toString());
            int rowCount = query.executeUpdate();
            unwrap.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            unwrap.getTransaction().rollback();
        }
    }
}

