package com.example.jpa.batch;

import com.example.jpa.ApplicationContextHolder;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author Hodur
 * @date 2021/8/13
 */
public interface BatchRepository<T, ID> {

    int BATCH_SIZE = 1000;

    EntityManager MANAGER_SECONDARY = ApplicationContextHolder.getApplicationContext()
            .getBean("entityManagerSecondary", EntityManager.class);

    /**
     * Two implements: batch size or reflection
     *
     * @param s
     * @param <S>
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    default <S extends T> Iterable<S> batchInsert(Iterable<S> s) {

        try {
            Method persist = EntityManager.class.getMethod("persist", Object.class);
            batchExecute(s, persist);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return s;
    }

    @Transactional(rollbackFor = Exception.class)
    default <S extends T> Iterable<S> batchUpdate(Iterable<S> s) {

        batchExecute(s, MANAGER_SECONDARY::merge);
        return s;
    }

    private <S extends T> void batchExecute(Iterable<S> s, Method m) {
        Session session = MANAGER_SECONDARY.unwrap(Session.class);
        session.setJdbcBatchSize(BATCH_SIZE);
        try {
            session.getTransaction().begin();
            Iterator<S> iterator = s.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                try {
                    m.invoke(MANAGER_SECONDARY, iterator.next());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                index++;
                if (index % BATCH_SIZE == 0) {
                    MANAGER_SECONDARY.flush();
                    MANAGER_SECONDARY.clear();
                }
            }
            if (index % BATCH_SIZE != 0) {
                MANAGER_SECONDARY.flush();
                MANAGER_SECONDARY.clear();
            }
            session.getTransaction().commit();
        } finally {
            session.getTransaction().rollback();
        }
    }

    private <S extends T> void batchExecute(Iterable<S> s, Consumer<S> consumer) {
        Session session = MANAGER_SECONDARY.unwrap(Session.class);
        try {
            session.getTransaction().begin();
            Iterator<S> iterator = s.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                consumer.accept(iterator.next());
                index++;
                if (index % BATCH_SIZE == 0) {
                    MANAGER_SECONDARY.flush();
                    MANAGER_SECONDARY.clear();
                }
            }
            if (index % BATCH_SIZE != 0) {
                MANAGER_SECONDARY.flush();
                MANAGER_SECONDARY.clear();
            }
            session.getTransaction().commit();
        } finally {
            session.getTransaction().rollback();
        }
    }

}
