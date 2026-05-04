package com.chipswu.intelligentcollaborativecloudimagerepository.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * OKHttp 客户端
 *
 * @author WuJiaJun
 */
@Configuration
public class OKHttpConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
