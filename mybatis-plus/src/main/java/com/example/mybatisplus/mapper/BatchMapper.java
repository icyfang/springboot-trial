package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.ApplicationContextHolder;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Shanghong Cai
 * @since 2020-09-03
 */
public interface BatchMapper<T> {

    @Transactional(rollbackFor = Exception.class)
    default void insertBatch(List<T> list) {

        if (list.isEmpty()) {
            throw new RuntimeException();
        }
        SqlSessionFactory sessionFactory = ApplicationContextHolder.getApplicationContext()
                                                                   .getBean(SqlSessionFactory.class);
        try (SqlSession session = sessionFactory.openSession(ExecutorType.BATCH, false)) {

            Class<?> mapperClass = this.getClass().getInterfaces()[0];
            BaseMapper mapper = (BaseMapper) session.getMapper(mapperClass);
            for (int i = 0; i < list.size(); i++) {
                mapper.insert(list.get(i));
                if (i % 1000 == 999) {
                    session.commit();
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }
    }
}
