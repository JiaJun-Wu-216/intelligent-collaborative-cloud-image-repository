package com.chipswu.intelligentcollaborativecloudimagerepository.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * 图片实体类
 *
 * @author WuJiaJun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`picture`")
public class Picture implements Serializable {

    @Serial
    private static final long serialVersionUID = 7087788744418095563L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 图片 url
     */
    @TableField(value = "`url`")
    private String url;

    /**
     * 图片名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 简介
     */
    @TableField(value = "`introduction`")
    private String introduction;

    /**
     * 分类
     */
    @TableField(value = "`category`")
    private String category;

    /**
     * 标签（JSON 数组）
     */
    @TableField(value = "`tags`")
    private String tags;

    /**
     * 图片体积
     */
    @TableField(value = "`pic_size`")
    private Long picSize;

    /**
     * 图片宽度
     */
    @TableField(value = "`pic_width`")
    private Integer picWidth;

    /**
     * 图片高度
     */
    @TableField(value = "`pic_height`")
    private Integer picHeight;

    /**
     * 图片宽高比例
     */
    @TableField(value = "`pic_scale`")
    private Double picScale;

    /**
     * 图片格式
     */
    @TableField(value = "`pic_format`")
    private String picFormat;

    /**
     * 创建用户 id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 空间 id
     */
    @TableField(value = "`space_id`")
    private Long spaceId;

    /**
     * 状态：0-待审核; 1-通过; 2-拒绝
     */
    @TableField(value = "`review_status`")
    private Integer reviewStatus;

    /**
     * 审核信息
     */
    @TableField(value = "`review_message`")
    private String reviewMessage;

    /**
     * 审核人 id
     */
    @TableField(value = "`reviewer_id`")
    private Long reviewerId;

    /**
     * 审核时间
     */
    @TableField(value = "`review_time`")
    private LocalDateTime reviewTime;

    /**
     * 缩略图 URL
     */
    @TableField(value = "`thumbnail_url`")
    private String thumbnailUrl;

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

    /**
     * 图片主色调
     */
    @TableField(value = "`pic_color`")
    private String picColor;
}