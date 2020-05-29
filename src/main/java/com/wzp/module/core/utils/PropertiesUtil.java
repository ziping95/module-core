package com.wzp.module.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {
    /**
     * 根据路径获取配置文件，获取配置文件中的值
     * @param classfilePath
     * @param key
     * @return
     */
    public static String getValue(String classfilePath, String key) {
        String result = null;

        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getResourceAsStream(classfilePath);
            if(in != null){
                properties.load(in);
                result= properties.getProperty(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 获得map
     * @param classfilePath
     * @param keyStart
     * @return
     */
    public static Map<String, String> getMap(String classfilePath, String keyStart) {

        Map<String, String> result = new HashMap<>();

        InputStream in = null;
        try {
            // 读取配置文件
            Properties properties = new Properties();
            in = PropertiesUtil.class.getResourceAsStream(classfilePath);
            properties.load(in);

            // 获取所有key
            Set<Object> keys = properties.keySet();

            // 获得keyStart开头的key
            if (StringUtil.isNotEmpty(keyStart)) {
                for (Object keyObject : keys) {
                    String key = (String) keyObject;
                    if (key.startsWith(keyStart)) {
                        result.put(key, properties.getProperty(key));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
