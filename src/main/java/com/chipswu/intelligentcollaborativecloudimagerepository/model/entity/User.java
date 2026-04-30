package com.chipswu.intelligentcollaborativecloudimagerepository.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author WuJiaJun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`user`")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号
     */
    @TableField(value = "`user_account`")
    private String userAccount;

    /**
     * 密码
     */
    @TableField(value = "`user_password`")
    private String userPassword;

    /**
     * 用户昵称
     */
    @TableField(value = "`username`")
    private String username;

    /**
     * 用户头像
     */
    @TableField(value = "`user_avatar`")
    private String userAvatar;

    /**
     * 用户简介
     */
    @TableField(value = "`user_profile`")
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    @TableField(value = "`user_role`")
    private String userRole;

    /**
     * 会员过期时间
     */
    @TableField(value = "`vip_expire_time`")
    private LocalDateTime vipExpireTime;

    /**
     * 会员兑换码
     */
    @TableField(value = "`vip_code`")
    private String vipCode;

    /**
     * 会员编号
     */
    @TableField(value = "`vip_number`")
    private Long vipNumber;

    /**
     * 分享码
     */
    @TableField(value = "`share_code`")
    private String shareCode;

    /**
     * 邀请用户 id
     */
    @TableField(value = "`invite_user`")
    private Long inviteUser;

    /**
     * 编辑时间
     */
    @TableField(value = "`edit_time`")
    private LocalDateTime editTime;

    /**
     * 创建时间
     */
    @TableField(value = "`create_time`")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "`update_time`")
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField(value = "`is_delete`")
    private Integer isDelete;
}