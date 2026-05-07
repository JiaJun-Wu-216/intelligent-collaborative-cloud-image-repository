package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 根据图片颜色搜索请求类
 *
 * @author WuJiaJun
 */
@Data
public class SearchPictureByColorRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8193682300634321628L;

    /**
     * 图片主色调
     */
    private String picColor;

    /**
     * 空间 id
     */
    private Long spaceId;

}
