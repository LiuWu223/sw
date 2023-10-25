package com.lw.swTest.core.utils;

import cn.hutool.core.io.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传工具类
 * @author lw
 * @since 4/19/2023 9:30 AM
 */
public class UploadUtil {

    /**
     * 单个文件上传
     * @param multipartFile 上传对象
     * @param request 请求头
     * @return
     */
    public static String uploadFile (MultipartFile multipartFile, HttpServletRequest request) {
        String uploadPath = "/upload";
        try {
            // 获取文件的名称
            String originalFilename = multipartFile.getOriginalFilename();
            // 文件后缀 例如：.png
            String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // uuid 生成文件名
            String uuid = String.valueOf(UUID.randomUUID());
            // 文件名字加文件后缀
            String fileName = uuid + fileSuffix;
            // 获取当前工作目录
            String basePath = request.getSession().getServletContext().getRealPath(uploadPath);
            // File.separator == /
            String folder = basePath + File.separator;
            System.out.println("文件上传路径"+basePath);
            // 判断该目录是否存在
            if (!FileUtil.exist(folder)) {
                FileUtil.mkdir(folder);
            }
            String path = folder + fileName;
            // 将文件保存到本地目录下
            File file = FileUtil.writeBytes(multipartFile.getBytes(), path);
            if (file.length() > 0) {
                return uploadPath + "/" + fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 多个文件上传
     * @param multipartFiles 文件上传集合
     * @param request 请求头
     * @return
     */
    public static List<String> uploadFiles (MultipartFile[] multipartFiles, HttpServletRequest request) {
        List<String> paths = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String path = uploadFile(multipartFile, request);
            paths.add(path);
        }
        return paths;
    }

}
