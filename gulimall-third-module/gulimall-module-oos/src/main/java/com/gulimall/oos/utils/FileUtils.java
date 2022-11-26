package com.gulimall.oos.utils;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author xiaoning
 * @date 2022/11/20
 */
public class FileUtils {

    private static final String FILE_NAME_PREFIX = "file";

    /**
     * 重命名文件
     *
     * @param file
     * @return
     */
    public static final String renameFile(MultipartFile file) throws IOException {
        String fileNameTemplate = "{}/{}_{}.{}";
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        return StrUtil.format(fileNameTemplate, dateStr, FILE_NAME_PREFIX, IdUtil.getSnowflakeNextId(), getExtension(file));
    }

    /**
     * 获取文件后缀名
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getExtension(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = FileTypeUtil.getType(file.getInputStream());
        }
        return extension;
    }
}
