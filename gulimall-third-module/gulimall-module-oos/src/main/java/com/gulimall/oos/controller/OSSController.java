package com.gulimall.oos.controller;

import com.gulimall.common.core.vo.R;
import com.gulimall.oos.service.IOSSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoning
 * @date 2022/11/19
 */
@RestController
@Api(tags = "Minio对象存储接口")
public class OSSController {

    @Autowired
    private IOSSService minioService;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 获取预签名URL
     *
     * @return
     */
    @GetMapping("/presigned/{fileName}")
    @ApiOperation(value = "获取预签名URL", notes = "获取预签名URL", response = R.class)
    public R getPresignedUrl(@PathVariable("fileName") String fileName) {
        return minioService.getPresignedUrl(bucketName, fileName);
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "上传文件", response = R.class)
    public R upload(MultipartFile file, HttpServletRequest request) {
        return minioService.upload(bucketName, file);
    }

}
