package com.example.jpa.batch;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    final EntityManager entityManager = ApplicationContextHolder.getApplicationContext()
                                                                .getBean("entityManagerSecondary", EntityManager.class);

    @PostMapping("/saveAll")
    public void saveAll() {
        List<DemoBatch> l = getDemoBatches(0, 5000, "");
        demoBatchRepository.saveAll(l);
    }

    @PostMapping("/batchInsert")
    public void batchInsert() {
        List<DemoBatch> l = getDemoBatches(5000, 10000, "");
        demoBatchRepository.batchInsert(l);
    }

    @PutMapping("/updateAll")
    public void updateAll() {
        List<DemoBatch> l = getDemoBatches(0, 5000, "new");
        demoBatchRepository.saveAll(l);
    }

    @PutMapping("/concatUpdateClause")
    public void updateUsingConcat() {
        List<DemoBatch> l = getDemoBatches(5000, 10000, "new");
        StringBuilder sb = new StringBuilder("update demo_batch set content = case");
        for (DemoBatch demoBatch : l) {
            sb.append(" when id = ").append(demoBatch.getId()).append(" then '").append(demoBatch.getContent())
              .append("'");
        }
        sb.append(" else content end")
          .append(" where id in (")
          .append(l.stream().map(i -> String.valueOf(i.getId())).collect(Collectors.joining(",")))
          .append(")");
        executeQuery(sb);
    }

    @PostMapping("/concatInsertClause")
    public void insertUsingConcat() {
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

        executeQuery(sb);
    }

    private List<DemoBatch> getDemoBatches(int start, int end, String suffix) {
        List<DemoBatch> l = new ArrayList<>();
        for (int i = start; i < end; i++) {
            DemoBatch demoBatch = new DemoBatch();
            demoBatch.setId((long) i);
            demoBatch.setContent(suffix + "content of demo batch#" + i);
            demoBatch.setName(suffix + "name of demo batch#" + i);
            l.add(demoBatch);
        }
        return l;
    }

    private void executeQuery(StringBuilder sb) {
        Session unwrap = entityManager.unwrap(Session.class);
        unwrap.setJdbcBatchSize(1000);
        try {
            unwrap.getTransaction().begin();
            Query query = entityManager.createNativeQuery(sb.toString());
            query.executeUpdate();
            unwrap.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            unwrap.getTransaction().rollback();
        }
    }
}
