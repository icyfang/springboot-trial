package com.example.mybatisplus.codegen;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Hodur
 * @date 2021/6/20
 */
public class SqlGenerator {

    /**
     * insertSql is used to generate sql useGeneratedKeys of which is set to false to support batch insert.
     *
     * @param clazz
     */
    public static void insertSql(Class<?> clazz) {

        List<Field> fieldList = new ArrayList<>();
        Class<?> superClass = clazz;
        while (superClass != null) {
            fieldList.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }
        List<String> columns = new ArrayList<>();
        List<String> params = new ArrayList<>();
        for (Field item : fieldList) {

            TableField column = item.getAnnotation(TableField.class);
            if (column != null && !columns.contains(column.value())) {
                columns.add(column.value());
                if (item.getType() == (Date.class)) {
                    params.add(item.getName() + ", jdbcType = TIMESTAMP");
                } else {
                    params.add(item.getName());
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        TableName annotation = clazz.getAnnotation(TableName.class);
        builder.append("<insert id =\"insert\" useGeneratedKeys=\"false\">\n");
        builder.append("insert into ").append(annotation.value()).append("(");
        for (int i = 0; i < columns.size(); i++) {
            if (i == 0) {
                builder.append(columns.get(i)).append("\n");
            } else {
                builder.append(",").append(columns.get(i)).append("\n");
            }
        }
        builder.append(")\n").append("VALUES(\n");
        for (int i = 0; i < params.size(); i++) {
            if (i == 0) {
                builder.append("#{").append(params.get(i)).append("}\n");
            } else {

                builder.append(",#{").append(params.get(i)).append("}\n");
            }
        }
        builder.append(",'SYSTEM'\n,getdate()\n)\n").append("</insert>");
        System.out.println(builder.toString());
    }

    /**
     * mergeSql is used to generate merge sql clause.
     *
     * @param clazz
     * @param pks
     */
    public static void mergeSql(Class<?> clazz, List<String> pks) {

        List<List<String>> lists = parsePO(clazz);
        List<String> columns = lists.get(1);
        List<String> params = lists.get(0);

        StringBuilder builder = new StringBuilder();
        StringBuilder update = new StringBuilder();
        StringBuilder insert = new StringBuilder();
        StringBuilder insertValue = new StringBuilder();
        TableName annotation = clazz.getAnnotation(TableName.class);
        builder.append("<insert id=\"merge\">\n");
        builder.append("merge into ").append(annotation.value()).append(" a using \n(\n").append("select\n");
        for (int i = 0; i < columns.size(); i++) {
            String str = columns.get(i);
            if (i == 0) {
                builder.append("#{").append(params.get(i)).append("} as ").append(str).append("\n");
            } else {
                builder.append(",#{").append(params.get(i)).append("} as ").append(str).append("\n");
            }
            if (!pks.contains(str)) {
                update.append("a.").append(str).append(" = b.").append(str).append(", ");
            }
            insert.append(str).append(", ");
            insertValue.append("b.").append(str).append(", ");
        }
        update.replace(update.length() - 2, update.length(), "");
        insert.replace(insert.length() - 2, insert.length(), ")");
        insertValue.replace(insertValue.length() - 2, insertValue.length(), ")");
        builder.append(") b\n").append("on (");
        for (int i = 0; i < pks.size(); i++) {
            if (i == 0) {
                builder.append("a.").append(pks.get(i)).append(" = b.").append(pks.get(i)).append("\n");
            } else {
                builder.append("and a.").append(pks.get(i)).append(" = b.").append(pks.get(i)).append("\n");
            }
        }
        builder.append(")\n").append("when matched\n").append("then\n").append("update set ").append(update)
               .append("\n");
        builder.append("when not matched\n").append("then\n").append("insert (").append(insert).append("\n");
        builder.append("values (").append(insertValue);
        builder.append("</insert>");
        System.out.println(builder.toString());
    }

    public static void main(String[] args) {

    }

    private static List<List<String>> parsePO(Class<?> clazz) {

        List<Field> fieldList = new ArrayList<>();
        Class<?> superClass = clazz;
        while (superClass != null) {
            fieldList.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }
        List<String> columns = new ArrayList<>();
        List<String> params = new ArrayList<>();
        for (Field item : fieldList) {

            TableField column = item.getAnnotation(TableField.class);
            if (column != null && !columns.contains(column.value())) {
                columns.add(column.value());
                if (item.getType() == (Date.class)) {
                    params.add(item.getName() + ", jdbcType = TIMESTAMP");
                } else {
                    params.add(item.getName());
                }
            }
        }
        return Arrays.asList(params, columns);
    }
}
