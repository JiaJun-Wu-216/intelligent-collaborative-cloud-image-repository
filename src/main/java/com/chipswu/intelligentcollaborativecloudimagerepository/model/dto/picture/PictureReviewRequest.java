package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 图片审核请求类
 *
 * @author WuJiaJun
 */
@Data
public class PictureReviewRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6808550592248169575L;

    /**
     * 图片主键
     */
    private Long id;

    /**
     * 状态：0-待审核, 1-通过, 2-拒绝
     */
    private Integer reviewStatus;

    /**
     * 审核信息
     */
    private String reviewMessage;

}
