package com.example.jpa.batch;

import com.example.jpa.ApplicationContextHolder;
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
    private CommentRepository commentRepository;

    final EntityManager entityManager = ApplicationContextHolder.getApplicationContext()
            .getBean("entityManagerSecondary", EntityManager.class);

    @PostMapping("/saveAll")
    public void saveAll() {
        List<CommentPO> l = getDemoBatches(0, 5000, "");
        commentRepository.saveAll(l);
    }

    @PostMapping("/batchInsert")
    public void batchInsert() {
        List<CommentPO> l = getDemoBatches(5000, 10000, "");
        commentRepository.batchInsert(l);
    }

    @PostMapping("/concatInsertClause")
    public void insertUsingConcat() {
        StringBuilder sb = new StringBuilder("insert into demo_batch(id, content, name) values ");
        List<CommentPO> l = new ArrayList<>();
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

    @PutMapping("/updateAll")
    public void updateAll() {
        List<CommentPO> l = getDemoBatches(0, 5000, "new");
        commentRepository.saveAll(l);
    }

    @PutMapping("/concatUpdateClause")
    public void updateUsingConcat() {
        List<CommentPO> l = getDemoBatches(5000, 10000, "new");
        StringBuilder sb = new StringBuilder("update demo_batch set content = case");
        for (CommentPO commentPO : l) {
            sb.append(" when id = ").append(commentPO.getId()).append(" then '").append(commentPO.getContent())
                    .append("'");
        }
        sb.append(" else content end")
                .append(" where id in (")
                .append(l.stream().map(i -> String.valueOf(i.getId())).collect(Collectors.joining(",")))
                .append(")");
        executeQuery(sb);
    }

    private List<CommentPO> getDemoBatches(int start, int end, String suffix) {
        List<CommentPO> l = new ArrayList<>();
        for (int i = start; i < end; i++) {
            CommentPO commentPO = new CommentPO();
            commentPO.setId((long) i);
            commentPO.setContent(suffix + "content of demo batch#" + i);
            commentPO.setName(suffix + "name of demo batch#" + i);
            l.add(commentPO);
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
