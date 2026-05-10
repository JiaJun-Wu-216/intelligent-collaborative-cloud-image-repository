package com.chipswu.intelligentcollaborativecloudimagerepository.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.chipswu.intelligentcollaborativecloudimagerepository.common.BaseResponse;
import com.chipswu.intelligentcollaborativecloudimagerepository.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author WuJiaJun
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Sa-Token 未登录异常处理器
     *
     * @param e 未登录异常
     * @return 错误信息
     */
    @ExceptionHandler(NotLoginException.class)
    public BaseResponse<?> notLoginException(NotLoginException e) {
        log.error("NotLoginException", e);
        return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR, e.getMessage());
    }

    /**
     * Sa-Token 无权限异常处理器
     *
     * @param e 无权限异常
     * @return 错误信息
     */
    @ExceptionHandler(NotPermissionException.class)
    public BaseResponse<?> notPermissionExceptionHandler(NotPermissionException e) {
        log.error("NotPermissionException", e);
        return ResultUtils.error(ErrorCode.NO_AUTH_ERROR, e.getMessage());
    }

    /**
     * 业务异常处理器
     *
     * @param e 业务异常信息
     * @return 处理结果
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 运行时异常处理器
     *
     * @param e 运行时异常信息
     * @return 处理结果
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }
}

