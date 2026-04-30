package com.chipswu.intelligentcollaborativecloudimagerepository.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义权限校验注解
 *
 * @author WuJiaJun
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    /**
     * 必须具有的角色
     *
     * @return 角色信息
     */
    String mustRole() default "";
}
