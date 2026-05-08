package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.space.analyze;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用空间分析请求类
 *
 * @author WuJiaJun
 */
@Data
public class SpaceAnalyzeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -2274790351172638324L;

    /**
     * 空间 ID
     */
    private Long spaceId;

    /**
     * 是否查询公共图库
     */
    private boolean queryPublic;

    /**
     * 全空间分析
     */
    private boolean queryAll;

}
