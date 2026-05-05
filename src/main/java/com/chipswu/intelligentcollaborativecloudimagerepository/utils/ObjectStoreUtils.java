package com.chipswu.intelligentcollaborativecloudimagerepository.utils;

/**
 * 对象存储工具类
 *
 * @author WuJiaJun
 */
public class ObjectStoreUtils {
    /**
     * 提取对象键
     *
     * @param url    文件访问地址
     * @param bucket 桶名
     * @return 对象键
     */
    public static String extractObjectKey(String url, String bucket) {
        // 假设 URL 格式为 http://host:port/bucket/path/to/file
        // 我们要提取的是从 "/car-rental/" 开始的部分
        String prefix = "/" + bucket + "/";
        int startIndex = url.indexOf(prefix);
        if (startIndex == -1) {
            throw new IllegalArgumentException("URL 不包含桶路径:/" + bucket + "/");
        }
        // 跳过 "/{bucket}/" 的长度，得到后面的部分
        int objectKeyStart = startIndex + prefix.length();
        return url.substring(objectKeyStart);
    }

    /**
     * 提取 OSS 地址中的对象键
     *
     * @param url OSS 文件访问地址
     * @return 对象键
     */
    public static String extractOSSObjectKey(String url) {
        // 假设 URL 格式为 https://car-rental-chips-wu.oss-cn-heyuan.aliyuncs.com/path/xxx.png
        return url.split("aliyuncs.com/")[1];
    }

    /**
     * 提取对象键（包含桶名称）
     *
     * @param url 文件访问地址
     * @return 对象键
     */
    public static String extractObjectKeySimple(String url) {
        // 示例：http://192.168.200.129:9000/car-rental/user/avatar/xxx.jpg
        // 先去掉协议部分
        return url.replaceAll("^https?://[^/]+", "");
    }
}
