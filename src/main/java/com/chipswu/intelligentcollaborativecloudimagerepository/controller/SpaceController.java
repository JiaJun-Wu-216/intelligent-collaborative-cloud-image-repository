package com.chipswu.intelligentcollaborativecloudimagerepository.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chipswu.intelligentcollaborativecloudimagerepository.annotation.AuthCheck;
import com.chipswu.intelligentcollaborativecloudimagerepository.common.BaseResponse;
import com.chipswu.intelligentcollaborativecloudimagerepository.common.DeleteRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.common.ResultUtils;
import com.chipswu.intelligentcollaborativecloudimagerepository.constants.UserConstant;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.BusinessException;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.ErrorCode;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.ThrowUtils;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.space.*;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.Space;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.User;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.enums.SpaceLevelEnum;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.SpaceVO;
import com.chipswu.intelligentcollaborativecloudimagerepository.service.SpaceService;
import com.chipswu.intelligentcollaborativecloudimagerepository.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 空间控制层
 *
 * @author WuJiaJun
 */
@RestController
@RequestMapping("/space")
public class SpaceController {

    @Resource
    private UserService userService;

    @Resource
    private SpaceService spaceService;

    /**
     * 创建空间
     *
     * @param spaceAddRequest 创建空间请求信息
     * @param request         当前请求信息
     * @return 新建空间主键
     */
    @PostMapping("/add")
    public BaseResponse<Long> addSpace(@RequestBody SpaceAddRequest spaceAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(spaceAddRequest == null, ErrorCode.PARAMS_ERROR);
        long newSpaceId = spaceService.addSpace(spaceAddRequest, userService.getLoginUser(request));
        return ResultUtils.success(newSpaceId);
    }

    /**
     * 删除空间
     *
     * @param deleteRequest 删除请求信息
     * @param request       当前请求信息
     * @return 删除结果
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteSpace(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = deleteRequest.getId();
        extracted(request, id);
        // 操作数据库
        boolean result = spaceService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新空间（仅管理员可用）
     *
     * @param spaceUpdateRequest 空间更新请求信息
     * @param request            当前请求信息
     * @return 更新结果
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateSpace(@RequestBody SpaceUpdateRequest spaceUpdateRequest,
                                             HttpServletRequest request) {
        if (spaceUpdateRequest == null || spaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 将实体类和 DTO 进行转换
        Space space = new Space();
        BeanUtils.copyProperties(spaceUpdateRequest, space);
        // 自动填充数据
        spaceService.fillSpaceBySpaceLevel(space);
        // 数据校验
        spaceService.validSpace(space, false);
        // 判断是否存在
        long id = spaceUpdateRequest.getId();
        Space oldSpace = spaceService.getById(id);
        ThrowUtils.throwIf(oldSpace == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = spaceService.updateById(space);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取空间（仅管理员可用）
     *
     * @param id 空间主键
     * @return 空间信息（未脱敏）
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Space> getSpaceById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Space space = spaceService.getById(id);
        ThrowUtils.throwIf(space == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(space);
    }

    /**
     * 根据 id 获取空间（封装类）
     *
     * @param id      空间主键
     * @param request 当前请求信息
     * @return 空间（封装类）
     */
    @GetMapping("/get/vo")
    public BaseResponse<SpaceVO> getSpaceVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Space space = spaceService.lambdaQuery()
                .eq(Space::getId, id)
                .one();
        ThrowUtils.throwIf(space == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(spaceService.getSpaceVO(space, request));
    }

    /**
     * 分页获取空间列表（仅管理员可用）
     *
     * @param spaceQueryRequest 空间查询请求信息
     * @return 空间列表
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Space>> listSpaceByPage(@RequestBody SpaceQueryRequest spaceQueryRequest) {
        long current = spaceQueryRequest.getCurrent();
        long size = spaceQueryRequest.getPageSize();
        // 查询数据库
        Page<Space> spacePage = spaceService.page(new Page<>(current, size),
                spaceService.getQueryWrapper(spaceQueryRequest));
        return ResultUtils.success(spacePage);
    }

    /**
     * 分页获取空间列表（封装类）
     *
     * @param spaceQueryRequest 空间查询请求信息
     * @param request           当前请求信息
     * @return 空间列表（封装类）
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<SpaceVO>> listSpaceVOByPage(@RequestBody SpaceQueryRequest spaceQueryRequest,
                                                         HttpServletRequest request) {
        long current = spaceQueryRequest.getCurrent();
        long size = spaceQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<Space> spacePage = spaceService.page(new Page<>(current, size),
                spaceService.getQueryWrapper(spaceQueryRequest));
        // 获取封装类
        return ResultUtils.success(spaceService.getSpaceVOPage(spacePage, request));
    }

    /**
     * 编辑空间（给用户使用）
     *
     * @param spaceEditRequest 空间编辑请求信息
     * @param request          当前请求信息
     * @return 编辑结果
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editSpace(@RequestBody SpaceEditRequest spaceEditRequest, HttpServletRequest request) {
        if (spaceEditRequest == null || spaceEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 在此处将实体类和 DTO 进行转换
        Space space = new Space();
        BeanUtils.copyProperties(spaceEditRequest, space);
        // 自动填充数据
        spaceService.fillSpaceBySpaceLevel(space);
        // 设置编辑时间
        space.setEditTime(LocalDateTime.now());
        // 数据校验
        spaceService.validSpace(space, false);
        extracted(request, spaceEditRequest.getId());
        // 操作数据库
        boolean result = spaceService.updateById(space);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据主键判断内容是否存在，且当前用户是否具有编辑权限
     *
     * @param request 当前请求信息
     * @param id      主键
     */
    private void extracted(HttpServletRequest request, Long id) {
        User loginUser = userService.getLoginUser(request);
        // 判断是否存在
        Space oldSpace = spaceService.getById(id);
        ThrowUtils.throwIf(oldSpace == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldSpace.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
    }

    /**
     * 获取空间级别列表
     *
     * @return 空间级别列表
     */
    @GetMapping("/list/level")
    public BaseResponse<List<SpaceLevel>> listSpaceLevel() {
        List<SpaceLevel> spaceLevelList = Arrays.stream(SpaceLevelEnum.values()) // 获取所有枚举
                .map(spaceLevelEnum -> new SpaceLevel(
                        spaceLevelEnum.getValue(),
                        spaceLevelEnum.getText(),
                        spaceLevelEnum.getMaxCount(),
                        spaceLevelEnum.getMaxSize()))
                .collect(Collectors.toList());
        return ResultUtils.success(spaceLevelList);
    }
}
