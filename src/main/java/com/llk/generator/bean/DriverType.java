package com.llk.generator.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author llk
 * @date 2020-09-29 16:19
 */
public enum DriverType {

    MYSQL("mysql", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://host:port/database?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai"),
    POSTGRE_SQL("postgresql", "org.postgresql.Driver", "jdbc:postgresql://host:port/database?currentSchema=public"),
    ;


    private static Map<String, DriverType> map = new HashMap<>();

    static {
        for (DriverType driverType : values()) {
            map.put(driverType.getType(), driverType);
        }
    }

    DriverType(String type, String driver, String url) {
        this.type = type;
        this.driver = driver;
        this.url = url;
    }

    private String type;

    private String driver;

    private String url;


    public String getType() {
        return type;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public static List<String> getTypes() {
        return new ArrayList<>(map.keySet());
    }

    public static DriverType getByType(String type) {
        return map.get(type);
    }

}

