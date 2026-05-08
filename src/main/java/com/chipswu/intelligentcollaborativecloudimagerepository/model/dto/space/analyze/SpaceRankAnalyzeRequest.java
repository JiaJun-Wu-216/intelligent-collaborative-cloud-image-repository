package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.space.analyze;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 空间使用排行分析请求类（仅管理员）
 *
 * @author WuJiaJun
 */
@Data
public class SpaceRankAnalyzeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5957342505861710569L;

    /**
     * 排名前 N 的空间
     */
    private Integer topN = 10;

}
