package com.gulimall.oos.service;

import com.gulimall.common.core.vo.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaoning
 * @date 2022/11/20
 */
public interface IOSSService {

    /**
     * 获取预签名URL
     *
     * @param bucketName 桶名称
     * @param fileName   上传文件名
     * @return
     */
    R getPresignedUrl(String bucketName, String fileName);

    /**
     * 上传文件
     *
     * @param bucketName
     * @param file
     * @return
     */
    R upload(String bucketName, MultipartFile file);
}
