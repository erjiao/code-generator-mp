package com.llk.generator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Configure {

	private static final Logger log = LoggerFactory.getLogger(Configure.class);

	static Properties pp;
 
	static{
		pp = new Properties();
		String workPath = System.getProperty("user.dir");
		log.info("work path: {}", workPath);
//		String path = Configure.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			InputStream fps = new FileInputStream(workPath + "/conf/generator.properties");
			pp.load(fps);
			fps.close();
		} catch (Exception e) {
			log.error("读取 generator.properties 文件失败", e);
		}
	}
	public static String value(String key) {
		return pp.getProperty(key, "");
	}
}
