package com.chipswu.intelligentcollaborativecloudimagerepository.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.space.SpaceAddRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.space.SpaceQueryRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.Space;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.User;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.SpaceVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 空间相关应用层接口
 *
 * @author WuJiaJun
 */
public interface SpaceService extends IService<Space> {

    /**
     * 创建空间
     *
     * @param spaceAddRequest 创建空间请求信息
     * @param loginUser       当前登录用户信息
     * @return 空间主键
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 获取查询对象
     *
     * @param spaceQueryRequest 空间查询请求信息
     * @return 查询对象
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 获取空间包装类（单条）
     *
     * @param space   空间实体信息
     * @param request 当前请求信息
     * @return 空间包装类（单条）
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 获取空间包装类（分页）
     *
     * @param spacePage 空间分页信息
     * @param request   当前请求信息
     * @return 空间包装类（分页）
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 校验空间信息
     *
     * @param space 空间信息
     * @param add   是否为创建时调用
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别填充空间对象
     *
     * @param space 空间信息
     */
    void fillSpaceBySpaceLevel(Space space);
}
