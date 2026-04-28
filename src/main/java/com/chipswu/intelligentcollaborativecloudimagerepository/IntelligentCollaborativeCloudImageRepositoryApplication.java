package com.chipswu.intelligentcollaborativecloudimagerepository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/*@OpenAPIDefinition(
        info = @Info(
                title = "智能协同云图库接口文档",
                version = "v1.0.0",
                description = "基于 SpringBoot + Redis + OSS + AI + WebSocket 的企业级智能协同云图库平台。分为公共图库、私有图库和团队共享图库三大模块。"
                        + "用户可在平台公开上传和检索图片；"
                        + "管理员可以上传、审核和管理分析图片，个人用户可将图片上传至私有空间进行批量管理、多维检索、编辑和分析;"
                        + "企业可开通团队空间并邀请成员，共享和实时协同编辑图片",
                contact = @Contact(
                        name = "WuJiaJun",
                        email = "wujiajun303@foxmail.com"
                )
        )
)*/
@SpringBootApplication
@MapperScan("com.chipswu.intelligentcollaborativecloudimagerepository.mapper")
@EnableAspectJAutoProxy(exposeProxy = true) // 启用 AOP 代理
public class IntelligentCollaborativeCloudImageRepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentCollaborativeCloudImageRepositoryApplication.class, args);
    }

}
