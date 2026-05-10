package com.chipswu.intelligentcollaborativecloudimagerepository.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 空间用户关联实体
 *
 * @author WuJiaJun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`space_user`")
public class SpaceUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 3156866999724776307L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 空间 id
     */
    @TableField(value = "`space_id`")
    private Long spaceId;

    /**
     * 用户 id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 空间角色：viewer/editor/admin
     */
    @TableField(value = "`space_role`")
    private String spaceRole;

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
}