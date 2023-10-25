package com.lw.swTest.core.controller;

import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.core.utils.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 文件上传 前端控制器
 * </p>
 *
 * @author lw
 * @since 4/19/2023 9:30 AM
 */
@RestController
@RequestMapping("/common/upload")
@Api(tags = "文件上传", value = "文件上传接口")
public class UploadController {

    @PostMapping("/single")
    @ApiOperation(value = "单文件上传", notes = "单文件上传", httpMethod = "POST")
    public ResponseResult single (@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        String path = UploadUtil.uploadFile(file, request);
        return ResponseResult.ok(ResponseCodeEnum.UPLOAD_SUCCESS, path);
    }

    @PostMapping("/multiple")
    @ApiOperation(value = "多文件上传", notes = "多文件上传", httpMethod = "POST")
    public ResponseResult multiple (@RequestPart("file") MultipartFile[] files, HttpServletRequest request) {
        List<String> paths = UploadUtil.uploadFiles(files, request);
        return ResponseResult.ok(ResponseCodeEnum.UPLOAD_SUCCESS, paths);
    }

}
