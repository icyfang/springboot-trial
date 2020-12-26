package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.ApplicationContextHolder;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Shanghong Cai
 * @since 2020-09-03
 */
public interface BatchMapper<T> {

    Logger log = LoggerFactory.getLogger(BatchMapper.class);

    @Transactional(rollbackFor = Exception.class)
    default boolean executeBatch(List<T> list, String methodName) {

        if (list.isEmpty()) {
            throw new RuntimeException();
        }
        SqlSessionFactory sessionFactory = ApplicationContextHolder.getApplicationContext()
                                                                   .getBean(SqlSessionFactory.class);
        try (SqlSession session = sessionFactory.openSession(ExecutorType.BATCH, false)) {

            Class<?> mapperClass = this.getClass().getInterfaces()[0];
            BaseMapper mapper = (BaseMapper) session.getMapper(mapperClass);
            Class<?> clazz = getGenericClass(mapperClass);
            for (int i = 0; i < list.size(); i++) {
                Method method = mapper.getClass().getMethod(methodName, clazz);
                if (method == null) {
                    String s = "executeBatch failed. Please complete your custom method " + methodName + " in your mapper.xml";
                    log.error(s);
                    throw new RuntimeException(s);
                }
                try {
                    method.invoke(mapper, list.get(i));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (i % 1000 == 999) {
                    session.commit();
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return true;
    }

    default Class<?> getGenericClass(Class<?> mapperClass) throws ClassNotFoundException {

        Type[] genericInterfaces = mapperClass.getGenericInterfaces();
        Class<?> clazz = null;
        for (Type type : genericInterfaces) {
            ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) type;
            if (BatchMapper.class.equals(parameterizedType.getRawType())) {
                clazz = Class.forName(parameterizedType.getActualTypeArguments()[0].getTypeName());
            }
        }
        if (clazz == null) {
            String message = mapperClass.getName() + " does no extends BatchMapper";
            log.error(message);
            throw new RuntimeException(message);
        }
        return clazz;
    }

    /**
     * for PO with an auto_increment pk, use insertBatch
     *
     * @param list List of Object need to be persisted.
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean insert(List<T> list) {
        try {
            return executeBatch(list, "insertBatch");
        } catch (Exception e) {
            return executeBatch(list, "insert");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    default boolean merge(List<T> list) {
        return executeBatch(list, "merge");
    }

    void insertBatch(T ratePO);
}