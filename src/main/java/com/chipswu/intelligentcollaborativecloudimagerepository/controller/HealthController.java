package com.chipswu.intelligentcollaborativecloudimagerepository.controller;

import com.chipswu.intelligentcollaborativecloudimagerepository.common.BaseResponse;
import com.chipswu.intelligentcollaborativecloudimagerepository.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查控制层
 * @author WuJiaJun
 */
@RestController
public class HealthController {


    /**
     * 健康检查
     * @return  <code>"OK"</code>
     */
    @GetMapping("/health")
    public BaseResponse<String> health() {
        return ResultUtils.success("System health");
    }
}
