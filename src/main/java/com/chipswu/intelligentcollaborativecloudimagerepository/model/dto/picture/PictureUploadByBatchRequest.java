package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 图片批量上传请求类
 *
 * @author WuJiaJun
 */
@Data
public class PictureUploadByBatchRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -4877777636035481789L;

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 抓取数量
     */
    private Integer count = 10;

    /**
     * 图片名称前缀
     */
    private String namePrefix;
}
