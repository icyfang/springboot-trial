package com.example.mybatisplus.codegen;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入").append(tip).append("：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        genPO();
    }

    public static void insertSql(Class<?> clazz) {

        List<List<String>> lists = parsePO(clazz);
        List<String> columns = lists.get(1);
        List<String> params = lists.get(0);

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

    private static void genPO() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("icyfang");
        gc.setOpen(false);
        gc.setEntityName("%sPO");
        //实体属性 Swagger2 注解
//         gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/ant?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("密码");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.baomidou.ant");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {

            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}

