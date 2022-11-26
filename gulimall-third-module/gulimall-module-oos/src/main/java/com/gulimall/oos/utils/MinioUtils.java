package com.gulimall.oos.utils;

import com.gulimall.oos.config.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * minio工具类
 *
 * @author xiaoning
 * @date 2022/11/19
 */
@Slf4j
@Component
@ConditionalOnBean(MinioClient.class)
public class MinioUtils {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    public MinioUtils(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    private static final String MINIO_POLICY_FILE = "minioPolicyJson.json";

    public static final String BUCKET_PARAM = "${bucketName}";

    /**
     * 查看指定桶是否存在
     *
     * @param bucketName 桶的名称
     * @return
     */
    public Boolean bucketExist(String bucketName) {
        boolean isExists = false;
        try {
            BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
                    .bucket(bucketName).build();
            isExists = minioClient.bucketExists(bucketExistsArgs);
        } catch (Exception e) {
            log.error("Minio 查看桶是否存在时发生异常: {}", e, e.getMessage());
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return isExists;
    }

    /**
     * 创建指定名称的桶
     *
     * @param bucketName 桶的名称
     * @return
     */
    public Boolean makeBucket(String bucketName) {
        try {
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                    // 指定桶名称
                    .bucket(bucketName)
                    .build();
            minioClient.makeBucket(makeBucketArgs);
            // 设置桶策略
            setBucketPolicy(bucketName);
        } catch (Exception e) {
            log.error("Minio 创建桶时发生异常: {}", e, e.getMessage());
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 删除指定名称的桶
     *
     * @param bucketName 桶的名称
     * @return
     */
    public Boolean removeBucket(String bucketName) {
        try {
            RemoveBucketArgs removeBucketArgs = RemoveBucketArgs.builder()
                    .bucket(bucketName).build();
            minioClient.removeBucket(removeBucketArgs);
        } catch (Exception e) {
            log.error("Minio 删除桶时发生异常: {}", e, e.getMessage());
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 设置桶策略
     *
     * @param bucketName 桶名称
     */
    public void setBucketPolicy(String bucketName) {
        try {
            // 1、获取策略字符串
            String policyStr = getPolicyStr(bucketName);
            // 2、构建参数
            SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs.builder()
                    .bucket(bucketName)
                    .config(policyStr)
                    .build();
            // 3、设置策略
            minioClient.setBucketPolicy(setBucketPolicyArgs);
        } catch (Exception e) {
            log.error("Minio 设置桶策略时失败：{}", e, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取策略字符串
     *
     * @param bucketName 桶名称
     * @return
     */
    public String getPolicyStr(String bucketName) {
        StringBuilder sb = new StringBuilder();
        ClassPathResource resource = new ClassPathResource(MINIO_POLICY_FILE);
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = resource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            // 返回策略字符串
            return sb.toString().replace(BUCKET_PARAM, bucketName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 上传文件。
     *
     * @param bucketName 存储桶的名称
     * @param file       上传文件
     * @param fileName   文件名称
     * @return 访问地址
     */
    public String upload(String bucketName, MultipartFile file, String fileName) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        // 1、判断桶是否存在
        Boolean found = bucketExist(bucketName);
        if (!found) {
            // 2、桶不存在，创建桶
            makeBucket(bucketName);
        }
        // 3、构造上传参数
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                // 指定存储桶
                .bucket(bucketName)
                // 指定文件名
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                // 设置类型
                .contentType(file.getContentType())
                .build();
        // 4、上传
        minioClient.putObject(putObjectArgs);
        // 5、返回访问路径
        return minioProperties.getEndpoint() + "/" + bucketName + "/" + fileName;
    }


}
