package com.wzp.module.core.note;

import com.wzp.module.core.CoreConstant;
import com.wzp.module.core.dto.ResultDataModel;
import com.wzp.module.core.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/open")
public class NoteController {

    /**
     * 下载软件安装包
     * @param  type
     * @return ResultDataModel
     */
    @GetMapping("/download/{type}")
    public ResultDataModel downloadInstallationPackage(@PathVariable String type) throws IOException {
        String path = FileUtil.getWebappPath() + CoreConstant.UPLOAD_DIR + "/" + CoreConstant.MODULE_CODE + "/" + CoreConstant.SOFTWARE + "/";
        switch (type) {
            case CoreConstant.JDK:
                path += CoreConstant.SOFTWARE_JDK;
                break;
            case CoreConstant.TOMCAT:
                path += CoreConstant.SOFTWARE_TOMCAT;
                break;
            case CoreConstant.REDIS:
                path += CoreConstant.SOFTWARE_REDIS;
                break;
            case CoreConstant.NGINX:
                path += CoreConstant.SOFTWARE_NGINX;
                break;
            case CoreConstant.MYSQL:
                path += CoreConstant.SOFTWARE_MYSQL;
                break;
            case CoreConstant.MAVEN:
                path += CoreConstant.SOFTWARE_MAVEN;
                break;
            default:
                return ResultDataModel.handleFailureResult("不存在该软件");
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setContentType("multipart/form-data");
            String name = path.substring(path.lastIndexOf("/") + 1);
            response.setHeader("Content-Disposition","fileName="+ URLEncoder.encode(name,"UTF-8"));
            File file = new File(path);
            byte[] buffer = new byte[1024];
            in = new FileInputStream(file);
            out = response.getOutputStream();
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);//将缓冲区的数据输出到客户端浏览器
            }
        } catch (Exception e) {
            log.error("下载{}安装包异常,异常为：{}",type,e.getMessage());
            e.printStackTrace();
            return ResultDataModel.handleFailureResult(e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (out != null) {
                out.flush();
            }
        }
        return ResultDataModel.handleSuccessResult();
    }
}
