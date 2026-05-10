package com.chipswu.intelligentcollaborativecloudimagerepository.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.spaceuser.SpaceUserAddRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.spaceuser.SpaceUserQueryRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.SpaceUser;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.SpaceUserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 空间成员相关业务层接口
 *
 * @author WuJiaJun
 */
public interface SpaceUserService extends IService<SpaceUser> {

    /**
     * 添加空间成员
     *
     * @param spaceUserAddRequest 空间成员添加请求信息
     * @return 新增空间成员 ID
     */
    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    /**
     * 校验空间成员对象
     *
     * @param spaceUser 空间成员实体对象
     * @param add       当前操作是否为添加操作
     */
    void validSpaceUser(SpaceUser spaceUser, boolean add);

    /**
     * 获取空间成员查询对象
     *
     * @param spaceUserQueryRequest 空间成员查询请求信息
     * @return 空间成员查询对象
     */
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);

    /**
     * 获取空间成员封装类 - 单个对象
     *
     * @param spaceUser 空间成员实体对象
     * @param request   当前请求信息
     * @return 单个空间成员封装对象
     */
    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    /**
     * 获取空间成员封装类 - 列表对象
     *
     * @param spaceUserList 空间成员实体对象列表
     * @return 空间成员封装对象列表
     */
    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);
}
