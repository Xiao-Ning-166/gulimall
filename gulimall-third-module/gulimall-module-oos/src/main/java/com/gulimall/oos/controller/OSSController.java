package com.gulimall.oos.controller;

import com.gulimall.common.core.utils.R;
import com.gulimall.oos.service.IOSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoning
 * @date 2022/11/19
 */
@RestController
@RequestMapping("/oss")
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
    public R upload(MultipartFile file, HttpServletRequest request) {
        return minioService.upload(bucketName, file);
    }

}
