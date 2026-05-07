package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 以图搜图请求类
 *
 * @author WuJiaJun
 */
@Data
public class SearchPictureByPictureRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3877367870543058000L;

    /**
     * 图片 id
     */
    private Long pictureId;

}
