package com.chipswu.intelligentcollaborativecloudimagerepository.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 阿里云 OSS 信息配置类
 *
 * @author WuJiaJun
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
@Validated
public class AliyunOssProperties {
    /**
     * 上传节点（生成环境时为内网节点，其余时候为公网节点）
     */
    @NotBlank(message = "【节点】不能为空")
    private String endpoint;

    /**
     * 外网节点
     */
    @NotBlank(message = "【外网节点】不能为空")
    private String publicEndpoint;

    /**
     * 操作账号 ID
     */
    @NotBlank(message = "【操作账号 ID】不能为空")
    private String accessKeyId;

    /**
     * 操作账号秘钥
     */
    @NotBlank(message = "【操作账号秘钥】不能为空")
    private String accessKeySecret;

    /**
     * 桶名称
     */
    @NotBlank(message = "【桶名称】不能为空")
    private String bucketName;

    /**
     * 地域
     */
    @NotBlank(message = "【地域】不能为空")
    private String region; // 可选
}
