package com.llk.generator.utils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>java获取request中的参数、java解析URL问号后的参数<p>
 *
 * @author li_hao
 * @version 1.0
 * @date 2016年12月21日
 */
public class JdbcUrls {

    public static Map<String, String> getParameter(String url) {
        Map<String, String> map = new HashMap<>();
        try {
            final String charset = "utf-8";
            url = URLDecoder.decode(url, charset);
            if (url.indexOf('?') != -1) {
                final String contents = url.substring(url.indexOf('?') + 1);
                String[] keyValues = contents.split("&");
                for (int i = 0; i < keyValues.length; i++) {
                    String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
                    String value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}