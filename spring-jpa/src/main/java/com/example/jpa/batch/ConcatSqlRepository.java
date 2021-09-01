package com.example.jpa.batch;

import com.example.jpa.ApplicationContextHolder;
import lombok.Data;
import org.hibernate.Session;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.metamodel.EntityType;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * implement batch insert and update by concat sql
 * </p>
 *
 * @author Hodur
 * @date 2021/8/26
 */
public interface ConcatSqlRepository<T, ID> {

    //todo id generated value
    //todo auditor
    //todo multi table
    //todo extends
    Map<Class<?>, EntityManager> entityManagerMap = getEntityManagerMap();

    private static Map<Class<?>, EntityManager> getEntityManagerMap() {
        Map<String, EntityManager> beansOfType = ApplicationContextHolder.getApplicationContext()
                                                                         .getBeansOfType(EntityManager.class);
        Map<Class<?>, EntityManager> map = new HashMap<>();
        beansOfType.forEach((k, v) -> {
            Set<EntityType<?>> entities = v.getEntityManagerFactory().getMetamodel().getEntities();
            for (EntityType<?> entity : entities) {
                Class<?> javaType = entity.getJavaType();
                map.put(javaType, v);
            }
        });
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    default <S extends T> void batchInsert(Iterable<S> s) throws IllegalAccessException {

        Class<?> aClass = getGenericClass(s);
        EntityManager entityManager = entityManagerMap.get(aClass);
        List<ColumnDefinition> columnDefinitions = parsePO(aClass);
        StringBuilder insertColumnSql = buildInsertSql(aClass, columnDefinitions);

        long maxPacketLength = getMaxPacketLength(entityManager);
        int index = 0;
        long lengthCount = insertColumnSql.length();
        List<List<StringBuilder>> builders = new ArrayList<>();
        Iterator<S> iterator = s.iterator();
        while (iterator.hasNext()) {
            S next = iterator.next();
            StringBuilder valueBuilder = new StringBuilder("(");
            for (ColumnDefinition columnDefinition : columnDefinitions) {
                if (!columnDefinition.getInsertable()) {
                    continue;
                }
                Object result = getValue(next, columnDefinition);
                valueBuilder.append(result).append(",");
            }
            valueBuilder.replace(valueBuilder.length() - 1, valueBuilder.length(), "),");
            lengthCount += valueBuilder.length();
            if (lengthCount > maxPacketLength) {
                index++;
                lengthCount = ((long) insertColumnSql.length()) + valueBuilder.length();
            }
            if (builders.size() <= index) {
                builders.add(index, new ArrayList<>());
            }

            builders.get(index).add(valueBuilder);
        }

        execute(entityManager, insertColumnSql, builders);
    }

    private <S extends T> Class<?> getGenericClass(Iterable<S> s) {
        Class<?> aClass;
        Iterator<S> iterator = s.iterator();
        if (iterator.hasNext()) {
            S next = iterator.next();
            aClass = next.getClass();

        } else {
            throw new RuntimeException();
        }
        return aClass;
    }

    private StringBuilder buildInsertSql(Class<?> aClass, List<ColumnDefinition> columnDefinitions) {
        Table annotation = aClass.getAnnotation(Table.class);
        StringBuilder builder = new StringBuilder();
        builder.append("insert into ").append(annotation.name()).append("(");
        for (int i = 0; i < columnDefinitions.size(); i++) {
            ColumnDefinition obj = columnDefinitions.get(i);
            if (obj.getInsertable()) {
                if (i == 0) {
                    builder.append(obj.column);
                } else {
                    builder.append(",").append(obj.column);
                }
            }
        }
        builder.append(")").append("VALUES");
        return builder;
    }

    private <S extends T> Object getValue(S next, ColumnDefinition columnDefinition) throws IllegalAccessException {
        Field param = columnDefinition.getParam();
        Object o = null;

        if (param.isAnnotationPresent(CreatedDate.class)) {
            if (param.getType().equals(LocalDate.class)) {
                o = LocalDate.now();
            } else if (param.getType().equals(LocalDateTime.class)) {
                o = LocalDateTime.now();
            } else if (param.getType().equals(LocalTime.class)) {
                o = LocalTime.now();
            } else if (param.getType().equals(Date.class)) {
                o = new Date();
            }
        } else {
            try {
                param.setAccessible(true);
                o = param.get(next);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw e;
            }
        }

        Object result;
        String value = null;
        if (o instanceof String) {
            value = (String) o;
        } else if (o instanceof LocalDate) {
            LocalDate localDate = (LocalDate) o;
            value = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        } else if (o instanceof LocalDateTime) {
            LocalDateTime localDate = (LocalDateTime) o;
            value = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else if (o instanceof LocalTime) {
            LocalTime localDate = (LocalTime) o;
            value = localDate.format(DateTimeFormatter.ISO_LOCAL_TIME);
        } else if (o instanceof Date) {
            Date localDate = (Date) o;
            value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(localDate);
        }
        if (value == null) {
            result = o;
        } else {
            result = "\"" + value + "\"";
        }
        return result;
    }

    private long getMaxPacketLength(EntityManager entityManager) {
        Query nativeQuery = entityManager
                .createNativeQuery("show VARIABLES WHERE Variable_name LIKE \"max_allowed_packet\"");
        Object singleResult = nativeQuery.getSingleResult();
        Object[] objects = (Object[]) singleResult;
        return Long.parseLong(((String) objects[1]));
    }

    private void execute(EntityManager entityManager, StringBuilder builder, List<List<StringBuilder>> builders) {
        Session unwrap = entityManager.unwrap(Session.class);
        unwrap.getTransaction().begin();

        for (List<StringBuilder> stringBuilders : builders) {
            StringBuilder builderCopy = new StringBuilder(builder);
            for (StringBuilder stringBuilder : stringBuilders) {
                builderCopy.append(stringBuilder);
            }
            builderCopy.delete(builderCopy.length() - 1, builderCopy.length());
            Query query = entityManager.createNativeQuery(builderCopy.toString());
            query.executeUpdate();
        }
        unwrap.getTransaction().commit();
    }

    @Transactional(rollbackFor = Exception.class)
    default <S extends T> Iterable<S> batchUpdate(Iterable<S> s) {

        return s;
    }

    private static List<ColumnDefinition> parsePO(Class<?> clazz) {

        Set<Field> fieldSet = new HashSet<>();
        //todo override
        Class<?> superClass = clazz;
        while (superClass != null) {
            fieldSet.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }
        List<ColumnDefinition> columns = new ArrayList<>();
        for (Field item : fieldSet) {
            if (item.isAnnotationPresent(Transient.class)) {
                continue;
            }
            ColumnDefinition columnDefinition = new ColumnDefinition();
            columnDefinition.setParam(item);

            Column column = item.getAnnotation(Column.class);
            if (column != null) {
                columnDefinition.setColumn(column.name());
                columnDefinition.setInsertable(column.insertable());
                columnDefinition.setUpdatable(column.updatable());
            } else {
                columnDefinition.setColumn(item.getName());
            }

            columns.add(columnDefinition);
        }
        return columns;
    }

    @Data
    class ColumnDefinition {

        private Field param;
        private String column;
        private Boolean insertable = true;
        private Boolean updatable = true;
    }
}
