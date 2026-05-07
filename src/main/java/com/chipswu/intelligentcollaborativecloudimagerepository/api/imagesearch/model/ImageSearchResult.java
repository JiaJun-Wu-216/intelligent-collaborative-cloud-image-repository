package com.chipswu.intelligentcollaborativecloudimagerepository.api.imagesearch.model;

import lombok.Data;

/**
 * 图片搜索结果类
 *
 * @author WuJiaJun
 */
@Data
public class ImageSearchResult {

    /**
     * 缩略图地址
     */
    private String thumbUrl;

    /**
     * 来源地址
     */
    private String fromUrl;
}
