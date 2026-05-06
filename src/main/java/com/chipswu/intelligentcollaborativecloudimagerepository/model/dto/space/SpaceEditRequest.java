package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.space;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 空间编辑请求类
 *
 * @author WuJiaJun
 */
@Data
public class SpaceEditRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7997387337727929667L;

    /**
     * 空间 id
     */
    private Long id;

    /**
     * 空间名称
     */
    private String spaceName;

}
