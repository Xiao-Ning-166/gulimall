package com.gulimall.common.core.constant;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * 日期格式化类，提供常用的日期格式化对象
 *
 * @author xiaoning
 * @date 2023/02/08
 */
public class DatePattern {

    /**
     * 标准日期时间正则，每个字段支持单个数字或2个数字，包括：
     * <pre>
     *     yyyy-MM-dd HH:mm:ss.SSS
     *     yyyy-MM-dd HH:mm:ss
     *     yyyy-MM-dd HH:mm
     *     yyyy-MM-dd
     * </pre>
     *
     * @since 5.3.6
     */
    public static final Pattern REGEX_NORM = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}(\\s\\d{1,2}:\\d{1,2}(:\\d{1,2})?)?(.\\d{1,3})?");

    /**
     * 标准日期格式：yyyy-MM-dd
     */
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 标准日期格式化：yyyy-MM-dd
     */
    public static final DateTimeFormatter NORM_DATE_FORMAT = DateTimeFormatter.ofPattern(NORM_DATE_PATTERN);

    /**
     * 标准日期时间格式，精确到分：yyyy-MM-dd HH:mm
     */
    public static final String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 标准日期时间格式化，精确到分：yyyy-MM-dd HH:mm
     */
    public static final DateTimeFormatter NORM_DATETIME_MINUTE_FORMAT = DateTimeFormatter.ofPattern(NORM_DATETIME_MINUTE_PATTERN);

    /**
     * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准日期时间格式化，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter NORM_DATETIME_FORMAT = DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN);

}
