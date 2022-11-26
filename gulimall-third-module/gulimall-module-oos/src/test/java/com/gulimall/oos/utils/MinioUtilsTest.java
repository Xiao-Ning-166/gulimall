package com.gulimall.oos.utils;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author xiaoning
 * @date 2022/11/19
 */
@SpringBootTest
class MinioUtilsTest {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioUtils minioUtils;

    @Test
    void testMinioClient() {

        // this.minioClient = MinioClient.builder()
        //         .endpoint("http://192.168.111.201:9000")
        //         .credentials("minio", "minio123456")
        //         .build();
        // System.out.println(minioClient);
    }

    /**
     * 测试上传文件
     */
    @Test
    void testUpload() {
        final String bucketName = "gulimall";

        try {
            File file = new File("C:\\Users\\xiaoning\\Pictures\\罗辑-人类文明纪念碑2048×1536.jpg");
            FileInputStream fileInputStream = new FileInputStream(file);

            // 判断桶是否存在
            Boolean isExist = minioUtils.bucketExist(bucketName);
            if (!isExist) {
                minioUtils.makeBucket(bucketName);
            }

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    // 设置上传桶
                    .bucket(bucketName)
                    // 设置文件名
                    .object(file.getName())
                    .stream(fileInputStream, -1, 5242880)
                    .build();
            minioClient.putObject(putObjectArgs);
            String accessUrl = "http://192.168.111.201:9000/" + bucketName + "/" + file.getName();
            System.out.println("访问路径：==> " + accessUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
