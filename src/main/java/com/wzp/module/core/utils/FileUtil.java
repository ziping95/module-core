package com.wzp.module.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileUtil {
    private static String classPath = null;

    /**
     * 获取类文件路径 e: 项目路径/WEB-INF/classes/
     *
     * @return
     */
    public static String getClassPath() {
        if (classPath == null) {
            classPath = new File(FileUtil.class.getClassLoader().getResource("").getPath()).getPath() + "/";
        }
        return classPath;
    }

    /**
     * 获取项目的部署路径
     */
    public static String getWebappPath() {
        String classPath = getClassPath();
        return classPath.substring(0, classPath.lastIndexOf("WEB-INF"));
    }

    /**
     * 获得WEB-INF文件位置
     *
     * @return
     */
    public static String getWEBINFPath() {
        return getWebappPath() + "WEB-INF/";
    }

    /**
     * 获得文件的真实路径
     *
     * @param filePath
     * @return
     */
    public static String getFileRealPath(String filePath) {
        String result = filePath;

        String realPath = getWebappPath();
        if (!filePath.startsWith(realPath)) {
            result = realPath + filePath;
        }

        return result;
    }

    /**
     * 获取web文件的路径,如果文件时绝对路径的话
     * 如果不是绝对路径，直接返回原始路径
     * @param filePath
     * @return
     */
    public static String getWebPath(String filePath) {
        if (filePath.indexOf(FileUtil.getWebappPath()) != -1) {
            return filePath.substring(FileUtil.getWebappPath().length() - 1).replaceAll("\\\\", "/");
        }
        return filePath;
    }

    /**
     * 获得文件,根据是否已webapp路径开头，判断是否是相对路径
     *
     * @param filePath
     * @return
     */
    public static File getFile(String filePath) {
        return getFile(filePath, false);
    }

    /**
     * 获得文件
     *
     * @param filePath
     * @return
     */
    public static File getFile(String filePath, boolean isRealPath) {
        String realPath = filePath;
        if (!isRealPath) {
            realPath = FileUtil.getFileRealPath(filePath);
        }
        File file = new File(realPath);
        return file;
    }

    /**
     * 复制文件夹
     * <p>
     * 如果目标目录不存在创建 复制文件、递归复制文件夹
     *
     * @param source 源目录
     * @param target 目标目录
     */
    public static List<File> copyFolder(String source, String target) {

        List<File> list = new LinkedList<File>();

        /*
         * 获取目标目录，如果目标目录不存在创建
         */
        File targetFolder = new File(target);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        /*
         * 遍历源目录，复制文件、递归复制文件夹
         */
        File sourceFolder = new File(source);
        File[] sourceFiles = sourceFolder.listFiles();
        if (sourceFiles != null) {
            for (File file : sourceFiles) {
                // 不遍历隐藏文件
                if (file.getName().startsWith(".")) {
                    continue;
                }

                if (file.isFile()) {
                    String targetFilePath = target + "/" + file.getName();
                    boolean success = copyFile(file.getPath(), targetFilePath);

                    /*
                     * 添加复制的文件到文件列表
                     */
                    if (success) {
                        File targetFile = new File(targetFilePath);
                        list.add(targetFile);
                    }
                } else if (file.isDirectory()) {
                    String targetDirectoryPath = target + "/" + file.getName();
                    List<File> tempList = copyFolder(file.getPath(), targetDirectoryPath);

                    /*
                     * 添加复制的文件夹到文件列表
                     */
                    if (tempList != null && tempList.size() > 0) {
                        File targetDirectory = new File(targetDirectoryPath);
                        list.add(targetDirectory);
                    }

                    /*
                     * 添加复制的所有文件到文件列表
                     */
                    list.addAll(tempList);

                }
            }
        }

        return list;

    }

    /**
     * 复制文件
     *
     * @param source
     * @param target
     */
    public static boolean copyFile(String source, String target) {
        boolean result = false;

        /*
         * 复制文件
         */
        BufferedReader bufferedReader = null;
        PrintStream printStream = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
            printStream = new PrintStream(new FileOutputStream(target));
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                printStream.println(s);
                printStream.flush();
            }
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
                if (printStream != null)
                    printStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return result;

    }

    /**
     * 给文件重命名
     *
     * @param file
     * @param newName
     * @return
     */
    public static void rename(File file, String newName) {
        String directory = file.getParentFile().getAbsolutePath();
        file.renameTo(new File(directory + "/" + newName));
    }

    /**
     * 获取路径中的后缀名
     *
     * @param fileName
     * @return
     */
    public static String getSimpleName(String fileName) {
        // 是否是文件全名
        int lastSeparator = fileName.indexOf("/");
        if (lastSeparator > -1) {
            fileName = fileName.substring(lastSeparator);
        }

        // 去掉后缀名
        int lastPoint = fileName.lastIndexOf(".");
        if (lastPoint > -1) {
            fileName = fileName.substring(0, lastPoint);
        }
        return fileName;
    }

    /**
     * 获取路径中的后缀名
     *
     * @param fileName
     * @return
     */
    public static String getPrefix(String fileName) {
        int lastPointIndex = fileName.lastIndexOf(".");
        String prefix = null;
        if (lastPointIndex > 0) {
            prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return prefix;
    }

    /**
     * 如果文件不存在，创建
     *
     * @param files
     */
    public static void createFile(File... files) {
        for (File file : files) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 如果目录不存在，创建
     * 如果文件不存在，不会创建
     *
     * @param files
     */
    public static void createPath(File... files) {
        for (File file : files) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
        }
    }

    /**
     * 如果文件不存在，创建
     *
     * @param fileList
     */
    public static void createFile(List<File> fileList) {
        for (File file : fileList) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * html传入的文件转存到服务器
     *
     * @param multipartFileList
     * @param path
     */
    public static void saveUploadFile(List<MultipartFile> multipartFileList, String path) throws IOException {
        File file = new File(FileUtil.getWebappPath() + path);
        if (!file.exists()) {
            file.mkdirs();
        }
        for (MultipartFile multipartFile : multipartFileList) {
            multipartFile.transferTo(new File(FileUtil.getWebappPath() + path + multipartFile.getOriginalFilename()));
        }
    }

    /**
     * 获取文件路径下所有文件名称
     *
     * @param path
     * @return
     */
    public static List<String> getFileName(String path) {
        File file = new File(path);
        return Arrays.asList(file.list());
    }

    /**
     * 获取文件夹下所有文件
     *
     * @param path
     * @param fileName
     */
    public static void getAllFileName(String path, List<String> fileName) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = file.list();
        if (names != null)
            fileName.addAll(Arrays.asList(names));
        for (File a : files) {
            if (a.isDirectory()) {
                getAllFileName(a.getAbsolutePath(), fileName);
            }
        }
    }

    /**
     * 获取文件的web路径
     *
     * @param file
     * @return
     */
    public static String getWebPath(File file) {
        String realPath = file.getAbsolutePath();
        String webBasePath = FileUtil.getWebappPath();
        return realPath.substring(webBasePath.length());
    }

    /**
     * 文件合并
     *
     * @param path
     * @param newFile
     */
    public static void merge(File path, File newFile) {
        RandomAccessFile in = null;
        RandomAccessFile out = null;
        try {
            // 如果新文件不存在，创建
            FileUtil.createFile(newFile);

            // 获取要合并的文件列表
            File[] files = path.listFiles();
            List<File> fileList = Arrays.asList(files);
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            in = new RandomAccessFile(newFile, "rw");
            in.setLength(0);
            in.seek(0);
            byte[] bytes = new byte[1024];
            int len;
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName());
                out = new RandomAccessFile(files[i], "rw");

                while ((len = out.read(bytes)) != -1) {
                    in.write(bytes, 0, len);
                }
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 写入文件
     * @param path
     * @param contents
     * @param append
     * @return
     */
    public static File fileWrite(String path, List<String> contents, boolean append) {
        File file = new File(path);

        createFile(file);

        FileWriter fileWritter = null;
        try {
            fileWritter = new FileWriter(file,append);
            for (String content : contents) {
                fileWritter.write(content + "\r\n");
                fileWritter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWritter != null) {
                try {
                    fileWritter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }

    /**
     * 创建txt
     *
     * @param content
     * @param title
     * @return
     * @throws IOException
     */
    public static String createTxt(String path,String content, String title) throws IOException {
        byte[] bom = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String address = path + "/" + title + ".txt";
        File file = new File(address);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOut = new FileOutputStream(file);
        fileOut.write(bom);  // 在文件头设置utf-8-bom格式
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fileOut, StandardCharsets.UTF_8));
        out.write(content);
        out.flush();
        out.close();
        return address;
    }

    /**
     * 读取json文件返回json对象
     * @param path
     * @return
     */
    public static<T> T readJsonFile(String path,Class<T> clazz) throws IOException, IllegalAccessException {
        File jsonFile = new File(path);
        FileReader fileReader = new FileReader(jsonFile);
        Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
        try {
            int ch = 0;
            StringBuilder stringBuffer = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                stringBuffer.append((char) ch);
            }
            if(clazz == JSONObject.class) {
                return (T) JSONObject.parseObject(stringBuffer.toString());
            } else if (clazz == JSONArray.class) {
                return (T) JSONArray.parseArray(stringBuffer.toString());
            }
            throw new IllegalAccessException("类型必须为JSONObject或JSONArray");
        } finally {
            fileReader.close();
            reader.close();
        }
    }

    /**
     * 通过读取流解析xml
     * @param inputStream
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String,Object> readXmlToMap(InputStream inputStream) throws IOException, DocumentException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buffer = new byte[1024];
        while ((inputStream.read(buffer)) > 0) {
            stringBuilder.append(new String(buffer, StandardCharsets.UTF_8));
        }
        return readXmlToMap(stringBuilder.toString().trim());
    }

    /**
     * 通过字符串解析xml
     * @param content
     * @return
     * @throws DocumentException
     */
    public static Map<String,Object> readXmlToMap(String content) throws DocumentException {
        Map<String,Object> map = new HashMap<>();
        Document doc = null;
        doc = DocumentHelper.parseText(content);
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
            Element e = (Element) iterator.next();
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    /**
     * 生成xml
     * @param params
     * @return
     */
    public static String createXmlFromMap(Map<String,Object> params) {
        // 创建根节点
        Document document = DocumentHelper.createDocument();
        Element xml = DocumentHelper.createElement("xml");
        document.setRootElement(xml);
        params.forEach((k,v) -> {
            Element element = xml.addElement(k);
            element.addCDATA((String) v);
        });
        return document.getRootElement().asXML();
    }


}
