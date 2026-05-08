package com.chipswu.intelligentcollaborativecloudimagerepository.controller;

import com.chipswu.intelligentcollaborativecloudimagerepository.common.BaseResponse;
import com.chipswu.intelligentcollaborativecloudimagerepository.common.ResultUtils;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.ErrorCode;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.ThrowUtils;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.space.analyze.*;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.Space;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.space.analyze.*;
import com.chipswu.intelligentcollaborativecloudimagerepository.service.SpaceAnalyzeService;
import com.chipswu.intelligentcollaborativecloudimagerepository.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 空间控制层
 *
 * @author WuJiaJun
 */
@RestController
@RequestMapping("/space/analyze")
public class SpaceAnalyzeController {

    @Resource
    private UserService userService;

    @Resource
    private SpaceAnalyzeService spaceAnalyzeService;

    /**
     * 获取空间的使用状态
     *
     * @param spaceUsageAnalyzeRequest 空间资源使用分析请求信息
     * @param request                  当前请求信息
     * @return 空间的使用状态
     */
    @PostMapping("/usage")
    public BaseResponse<SpaceUsageAnalyzeResponse> getSpaceUsageAnalyze(@RequestBody SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest,
                                                                        HttpServletRequest request) {
        ThrowUtils.throwIf(spaceUsageAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        SpaceUsageAnalyzeResponse result = spaceAnalyzeService.getSpaceUsageAnalyze(spaceUsageAnalyzeRequest,
                userService.getLoginUser(request));
        return ResultUtils.success(result);
    }

    /**
     * 获取空间图片分类分析
     *
     * @param spaceCategoryAnalyzeRequest 空间图片分类分析请求信息
     * @param request                     当前请求信息
     * @return 空间图片分类分析
     */
    @PostMapping("/category")
    public BaseResponse<List<SpaceCategoryAnalyzeResponse>> getSpaceCategoryAnalyze(@RequestBody SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest,
                                                                                    HttpServletRequest request) {
        ThrowUtils.throwIf(spaceCategoryAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        List<SpaceCategoryAnalyzeResponse> result = spaceAnalyzeService.getSpaceCategoryAnalyze(spaceCategoryAnalyzeRequest, userService.getLoginUser(request));
        return ResultUtils.success(result);
    }

    /**
     * 获取空间图片标签分析
     *
     * @param spaceTagAnalyzeRequest 空间图片标签分析请求信息
     * @param request                当前请求信息
     * @return 空间图片标签分析
     */
    @PostMapping("/tag")
    public BaseResponse<List<SpaceTagAnalyzeResponse>> getSpaceTagAnalyze(@RequestBody SpaceTagAnalyzeRequest spaceTagAnalyzeRequest,
                                                                          HttpServletRequest request) {
        ThrowUtils.throwIf(spaceTagAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        List<SpaceTagAnalyzeResponse> result = spaceAnalyzeService.getSpaceTagAnalyze(spaceTagAnalyzeRequest, userService.getLoginUser(request));
        return ResultUtils.success(result);
    }

    /**
     * 获取空间图片大小分析
     *
     * @param spaceSizeAnalyzeRequest 空间图片大小分析请求信息
     * @param request                 当前请求信息
     * @return 空间图片大小分析
     */
    @PostMapping("/size")
    public BaseResponse<List<SpaceSizeAnalyzeResponse>> getSpaceSizeAnalyze(@RequestBody SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest,
                                                                            HttpServletRequest request) {
        ThrowUtils.throwIf(spaceSizeAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        List<SpaceSizeAnalyzeResponse> resultList = spaceAnalyzeService.getSpaceSizeAnalyze(spaceSizeAnalyzeRequest, userService.getLoginUser(request));
        return ResultUtils.success(resultList);
    }

    /**
     * 获取空间用户上传行为分析
     *
     * @param spaceUserAnalyzeRequest 用户上传行为分析请求信息
     * @param request                 当前请求信息
     * @return 用户上传行为分析
     */
    @PostMapping("/user")
    public BaseResponse<List<SpaceUserAnalyzeResponse>> getSpaceUserAnalyze(@RequestBody SpaceUserAnalyzeRequest spaceUserAnalyzeRequest,
                                                                            HttpServletRequest request) {
        ThrowUtils.throwIf(spaceUserAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        List<SpaceUserAnalyzeResponse> resultList = spaceAnalyzeService.getSpaceUserAnalyze(spaceUserAnalyzeRequest, userService.getLoginUser(request));
        return ResultUtils.success(resultList);
    }

    /**
     * 获取空间使用排行分析
     *
     * @param spaceRankAnalyzeRequest 空间使用排行分析请求信息
     * @param request                 当前请求信息
     * @return 空间使用排行分析
     */
    @PostMapping("/rank")
    public BaseResponse<List<Space>> getSpaceRankAnalyze(@RequestBody SpaceRankAnalyzeRequest spaceRankAnalyzeRequest,
                                                         HttpServletRequest request) {
        ThrowUtils.throwIf(spaceRankAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        List<Space> resultList = spaceAnalyzeService.getSpaceRankAnalyze(spaceRankAnalyzeRequest, userService.getLoginUser(request));
        return ResultUtils.success(resultList);
    }

}
