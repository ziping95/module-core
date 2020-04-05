package com.wzp.module.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 字符串工具
 *
 * @author windf
 */
public class StringUtil extends StringUtils {
    public static final String UTF8 = "UTF-8";
    private static final String ALL_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 如果对象为null返回空字符串，否则返回该字符串
     *
     * @param str
     * @return
     */
    public static String fixNull(Object str) {
        return fixNull(str, "");
    }

    /**
     * 如果对象为null返回指定字符串，否则返回该字符串
     * @param str
     * @return
     */
    public static String fixNull(Object str, String defaultStr) {
        return str == null ? defaultStr : str.toString();
    }


    /**
     * 如果字符串为null返回指定字符串，否则返回该字符串
     * @param str
     * @return
     */
    public static String fixEmpty(String str, String defaultStr) {
        return StringUtil.isEmpty(str) ? defaultStr : str.toString();
    }

    /**
     * 将以长串压缩
     *
     * @param data
     * @return
     */
    public static int getHashCode(String data) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", data);
        return map.hashCode();
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String firstLetterUppercase(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    /**
     * 将驼峰命名法的字符串，单词之间用指定字符隔开，切单词首字母小写
     *
     * @param str
     * @param separator
     * @return
     */
    public static String splitCamelCase(String str, String separator) {
        StringBuffer result = new StringBuffer();

        if (StringUtil.isNotEmpty(str)) {
            String[] ss = str.split("(?=[A-Z])");
            for (int i = 0; i < ss.length; i++) {
                if (result.length() > 0) {
                    result.append(separator);
                }
                result.append(ss[i].toLowerCase());
            }
        }

        return result.toString();
    }

    /**
     * 将集合转换为字符串，中间用指定字符隔开
     *
     * @param ss
     * @param separator
     * @return
     */
    public static String join(String[] ss, String separator) {
        List<String> collection = Arrays.asList(ss);
        return StringUtils.join(collection, separator);
    }

    /**
     * 获得句子的第一个单词，
     * 如果句子中没有空格，数字第一返回null，数组第二个是参数本身
     *
     * @param s "第一个单词","剩下的句子"
     * @return
     */
    public static String[] getFirstWord(String s) {
        int firstBlank = s.indexOf(" ");
        String firstWord = null;
        if (firstBlank > -1) {
            firstWord = s.substring(0, firstBlank);
            s = s.substring(firstBlank + 1);
        }
        return new String[]{firstWord, s};
    }

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random =new Random();
        int len = ALL_CHARS.length();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHARS.charAt(random.nextInt(len)));
        }
        return sb.toString();
    }

    /**
     * 如果obj不为null,调用toString，否则返回null
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(String a, String b) {
        // 如果a=null，则是否相等，决定于b是否为null
        if (a == null) {
            return b == null;
        }

        // 如果a部位null，则正常比较
        return a.equals(b);
    }

    /**
     * 从流中读取字符串
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readStrFromInputStream(InputStream inputStream) throws Exception {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String s;
            while ((s = in.readLine()) != null) {
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
