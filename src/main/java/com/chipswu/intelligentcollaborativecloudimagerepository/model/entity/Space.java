package com.chipswu.intelligentcollaborativecloudimagerepository.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 空间实体类
 *
 * @author WuJiaJun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`space`")
public class Space implements Serializable {

    @Serial
    private static final long serialVersionUID = -2502863546919298664L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 空间名称
     */
    @TableField(value = "`space_name`")
    private String spaceName;

    /**
     * 空间级别：0-普通版 1-专业版 2-旗舰版
     */
    @TableField(value = "`space_level`")
    private Integer spaceLevel;

    /**
     * 空间类型：0-私有 1-团队
     */
    @TableField(value = "`space_type`")
    private Integer spaceType;

    /**
     * 空间图片的最大总大小
     */
    @TableField(value = "`max_size`")
    private Long maxSize;

    /**
     * 空间图片的最大数量
     */
    @TableField(value = "`max_count`")
    private Long maxCount;

    /**
     * 当前空间下图片的总大小
     */
    @TableField(value = "`total_size`")
    private Long totalSize;

    /**
     * 当前空间下的图片数量
     */
    @TableField(value = "`total_count`")
    private Long totalCount;

    /**
     * 创建用户 id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField(value = "`create_time`")
    private LocalDateTime createTime;

    /**
     * 编辑时间
     */
    @TableField(value = "`edit_time`")
    private LocalDateTime editTime;

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