package com.chipswu.intelligentcollaborativecloudimagerepository.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.chipswu.intelligentcollaborativecloudimagerepository.properties.AliyunOssProperties;
import com.chipswu.intelligentcollaborativecloudimagerepository.utils.OSSUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS 自动配置类
 *
 * @author WuJiaJun
 */
@Configuration
@ConditionalOnClass(OSS.class)
@EnableConfigurationProperties(AliyunOssProperties.class)
public class OssClientConfig {
    /**
     * 初始化 oss 客户端
     *
     * @param properties oss 配置信息
     * @return 初始化完毕的客户端
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public OSS ossClient(AliyunOssProperties properties) {
        return new OSSClientBuilder()
                .build(properties.getEndpoint(),
                        properties.getAccessKeyId(),
                        properties.getAccessKeySecret());
    }

    /**
     * 初始化自定义业务对象，用于操作 oss
     *
     * @param ossClient  oss 客户端
     * @param properties oss 配置信息
     * @return 自定义业务对象
     */
    @Bean
    @ConditionalOnMissingBean
    public OSSUtils ossUtils(OSS ossClient, AliyunOssProperties properties, OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        return new OSSUtils(ossClient, properties.getBucketName(), properties.getPublicEndpoint(), okHttpClient, objectMapper);
    }
}
