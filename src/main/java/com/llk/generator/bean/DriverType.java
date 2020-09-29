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

    MYSQL("mysql", "com.mysql.cj.jdbc.Driver"),
    POSTGRE_SQL("postgresql", "org.postgresql.Driver"),
    ;


    private static Map<String, DriverType> map = new HashMap<>();

    static {
        for (DriverType driverType : values()) {
            map.put(driverType.getType(), driverType);
        }
    }

    DriverType(String type, String driver) {
        this.type = type;
        this.driver = driver;
    }

    private String type;

    private String driver;


    public String getType() {
        return type;
    }

    public String getDriver() {
        return driver;
    }

    public static List<String> getTypes() {
        return new ArrayList<>(map.keySet());
    }

    public static DriverType getByType(String type) {
        return map.get(type);
    }

}

