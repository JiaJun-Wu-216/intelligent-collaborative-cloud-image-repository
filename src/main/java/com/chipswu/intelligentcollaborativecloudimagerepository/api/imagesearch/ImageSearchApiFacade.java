package com.chipswu.intelligentcollaborativecloudimagerepository.api.imagesearch;

import com.chipswu.intelligentcollaborativecloudimagerepository.api.imagesearch.model.ImageSearchResult;
import com.chipswu.intelligentcollaborativecloudimagerepository.api.imagesearch.sub.GetImageFirstUrlApi;
import com.chipswu.intelligentcollaborativecloudimagerepository.api.imagesearch.sub.GetImageListApi;
import com.chipswu.intelligentcollaborativecloudimagerepository.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 以图搜图 API（门面模式）
 *
 * @author WuJiaJun
 */
@Slf4j
public class ImageSearchApiFacade {
    /**
     * 搜索图片
     *
     * @param imageUrl 需要以图搜图的图片地址
     * @return 搜寻到的图片列表
     */
    public static List<ImageSearchResult> searchImage(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.getImagePageUrl(imageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        return GetImageListApi.getImageList(imageFirstUrl);
    }

    public static void main(String[] args) {
        List<ImageSearchResult> imageSearchResults = searchImage("https://cloud-image-repository.oss-cn-heyuan.aliyuncs.com/public/2049398683725635586/2026-05-05_TI3bU3zVF8BEzrMV.jpg");
        log.info("{}", imageSearchResults);
    }
}
