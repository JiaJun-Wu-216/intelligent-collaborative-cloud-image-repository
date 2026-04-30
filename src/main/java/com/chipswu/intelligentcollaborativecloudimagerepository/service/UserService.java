package com.chipswu.intelligentcollaborativecloudimagerepository.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.user.UserLoginRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.user.UserQueryRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.user.UserRegisterRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.User;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.LoginUserVO;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户相关应用层接口
 *
 * @author WuJiaJun
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param registerInfo 用户注册信息
     * @return 注册后生成的用户 ID
     */
    long userRegister(UserRegisterRequest registerInfo);

    /**
     * 获取加密后的密码
     *
     * @param userPassword 原密码
     * @return 加密后的密码
     */
    String getEncryptPassword(String userPassword);

    /**
     * 用户登陆
     *
     * @param loginInfo 登陆信息
     * @param request   请求信息
     * @return 用户脱敏后的信息
     */
    LoginUserVO userLogin(UserLoginRequest loginInfo, HttpServletRequest request);

    /**
     * 获得脱敏后的用户登陆信息
     *
     * @param user 未脱敏的用户信息
     * @return 脱敏后的用户登陆信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取当前登录用户
     *
     * @param request 请求信息
     * @return 当前登录用户信息
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户退出登录
     *
     * @param request 请求信息
     * @return 退出登录结果
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏后的用户信息
     *
     * @param user 未脱敏的用户信息
     * @return 脱敏后的用户信息
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏后的用户信息列表
     *
     * @param userList 未脱敏的用户信息列表
     * @return 脱敏后的用户信息列表
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 用户查询请求类
     * @return 查询条件
     */
    LambdaQueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
