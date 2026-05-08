package com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.space.analyze;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户上传行为分析响应类
 *
 * @author WuJiaJun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceUserAnalyzeResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -5731604730852328245L;

        /**
         * 时间区间
         */
        private String period;

        /**
         * 上传数量
         */
        private Long count;

}
