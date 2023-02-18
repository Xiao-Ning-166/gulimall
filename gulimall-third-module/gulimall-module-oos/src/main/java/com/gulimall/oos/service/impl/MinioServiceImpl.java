package com.gulimall.oos.service.impl;

import com.gulimall.common.core.vo.R;
import com.gulimall.oos.service.IOSSService;
import com.gulimall.oos.utils.FileUtils;
import com.gulimall.oos.utils.MinioUtils;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoning
 * @date 2022/11/20
 */
@Slf4j
@Service
public class MinioServiceImpl implements IOSSService {

    private final MinioClient minioClient;

    private final MinioUtils minioUtils;

    public MinioServiceImpl(MinioClient minioClient, MinioUtils minioUtils) {
        this.minioClient = minioClient;
        this.minioUtils = minioUtils;
    }

    /**
     * 获取预签名URL
     *
     * @param bucketName 桶名称
     * @param fileName   上传文件名
     * @return
     */
    @Override
    public R getPresignedUrl(String bucketName, String fileName) {
        try {
            // 文件名前缀
            String fileNamePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString();
            // 1、判断桶是否存在
            Boolean found = minioUtils.bucketExist(bucketName);
            if (!found) {
                // 2、桶不存在，创建桶
                minioUtils.makeBucket(bucketName);
            }
            // 3、设置参数
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    // 允许的方法类型
                    .method(Method.PUT)
                    // 对象名称
                    .object(fileNamePrefix + "/" + "file_" + fileName)
                    // 设置签名有效期为5分钟
                    .expiry(5, TimeUnit.MINUTES)
                    .build();
            // 4、获取预签名URL
            String presignedObjectUrl = minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
            // 5、返回预签名URL
            return R.ok(presignedObjectUrl);
        } catch (Exception e) {
            log.error("获取预签名异常，原因：{}", e, e.getMessage());
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    /**
     * 上传文件
     *
     * @param bucketName
     * @param file
     * @return
     */
    @Override
    public R upload(String bucketName, MultipartFile file) {

        try {
            String accessUrl = minioUtils.upload(bucketName, file, FileUtils.renameFile(file));
            return R.ok(accessUrl);
        } catch (Exception e) {
            log.error("文件上传失败，原因：{}", e, e.getMessage());
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
}
