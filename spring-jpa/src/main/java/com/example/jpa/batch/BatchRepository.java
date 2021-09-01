package com.example.jpa.batch;

import com.example.jpa.ApplicationContextHolder;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author Hodur
 * @date 2021/8/13
 */
public interface BatchRepository<T, ID> {

    int BATCH_SIZE = 1000;

    EntityManager entityManager = ApplicationContextHolder.getApplicationContext().getBean(EntityManager.class);

    default <S extends T> Iterable<S> batchInsert(Iterable<S> s) {

        batchExecute(s, entityManager::persist);
        return s;
    }

    private <S extends T> void batchExecute(Iterable<S> s, Consumer<S> consumer) {
        Session unwrap = entityManager.unwrap(Session.class);
        try {
            unwrap.getTransaction().begin();
            Iterator<S> iterator = s.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                consumer.accept(iterator.next());
                index++;
                if (index % BATCH_SIZE == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }
            if (index % BATCH_SIZE != 0) {
                entityManager.flush();
                entityManager.clear();
            }
            unwrap.getTransaction().commit();
        } catch (Exception e) {
            unwrap.getTransaction().rollback();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    default <S extends T> Iterable<S> batchUpdate(Iterable<S> s) {

        batchExecute(s, entityManager::merge);
        return s;
    }

}
