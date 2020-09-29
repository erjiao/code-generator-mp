package com.llk.generator.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.llk.generator.bean.GenConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author llk
 * @date 2019-10-02 02:25
 */
public class GenUtils {


    public static void generatorCode(GenConfig config) {

        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setOutputDir(config.getJavaOutputDir())
                .setAuthor(config.getAuthor())
                .setDateType(DateType.ONLY_DATE)
                .setOpen(true)
                .setFileOverride(true)
                .setSwagger2(true)
                .setIdType(IdType.AUTO)
                .setBaseColumnList(true)
                .setBaseResultMap(true)
                .setServiceName("%sService");

        mpg.setGlobalConfig(gc);

        // 数据源配置
        // jdbc:mysql://localhost:3306/ant?useUnicode=true&useSSL=false&characterEncoding=utf8
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(config.getUrl())
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername(config.getUsername())
                .setPassword(config.getPassword());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(config.getPackageName());
        mpg.setPackageInfo(pc);


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
                this.setMap(map);
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/custom/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        if (!"".equals(config.getXmlOutputDir())) {
            focList.add(new FileOutConfig(templatePath) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return config.getXmlOutputDir()
                            + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            });
        }
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
//        templateConfig.setController("/templates/custom/controller.java");
        templateConfig.setController(null);
        templateConfig.setService("/templates/custom/service.java");
        templateConfig.setServiceImpl("/templates/custom/serviceImpl.java");
        templateConfig.setEntity("/templates/custom/entity.java");
        templateConfig.setMapper("/templates/custom/mapper.java");
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));

        // table 设置了值再使用指定配置
        String table = config.getTable();
        if (Objects.nonNull(table) && !table.trim().equals("")) {
            strategy.setInclude(table.split(","));
        }
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(config.getTablePrefix());
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());


        mpg.execute();
    }

}
