package com.chipswu.intelligentcollaborativecloudimagerepository.api.imagesearch.sub;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.BusinessException;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取以图搜图页面地址（step 1）
 *
 * @author WuJiaJun
 */
@Slf4j
public class GetImagePageUrlApi {

    /**
     * 获取以图搜图页面地址
     *
     * @param imageUrl 需要以图搜图的图片地址
     * @return 以图搜图页面地址
     */
    public static String getImagePageUrl(String imageUrl) {
        // 1.准备参数
        Map<String, Object> formData = new HashMap<>();
        formData.put("image", imageUrl);
        formData.put("tn", "pc");
        formData.put("from", "pc");
        formData.put("image_source", "PC_UPLOAD_URL");
        formData.put("sdkParams", """
                {"data":"c29415cf3d16ee10ac88d9cde2883ea574744a4dbf6396fdd02f789a4a43fa72f47947426c3a2c17ed2276ee16f6f279d71aec6db41e264f60754d610779f3ce82e23897b5cce1023aeb6672448e1f0c","key_id":"23","sign":"b9f7b308"}
                """);
        // 获取当前时间戳
        long upTime = System.currentTimeMillis();
        // 请求地址
        String url = "https://graph.baidu.com/upload?uptime=" + upTime;
        try {
            // 2.发送请求
            String body;
            try (HttpResponse response = HttpRequest.post(url)
                    .header("acs-token", RandomUtil.randomString(1))
                    .form(formData)
                    .timeout(5000)
                    .execute()) {
                if (response.getStatus() != HttpStatus.HTTP_OK) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口调用失败");
                }
                // 解析响应
                body = response.body();
            }
            Map<String, Object> result = JSONUtil.toBean(body, new TypeReference<>() {
            }, false);
            // 3.处理响应结果
            if (result == null || !Integer.valueOf(0).equals(result.get("status"))) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口调用失败");
            }
            Map<String, Object> data = JSONUtil.toBean(result.get("data").toString(), new TypeReference<>() {
            }, false);
            String rawUrl = String.valueOf(data.get("url"));
            String searchResultUrl = URLUtil.decode(rawUrl, StandardCharsets.UTF_8);
            // 如果 URL 为空
            if (StrUtil.isBlank(searchResultUrl)) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "未返回有效的结果地址");
            }
            return searchResultUrl;
        } catch (Exception e) {
            log.error("调用百度以图搜图接口失败", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "搜索失败");
        }
    }

    public static void main(String[] args) {
        String imagePageUrl = getImagePageUrl("https://cloud-image-repository.oss-cn-heyuan.aliyuncs.com/public/2049398683725635586/2026-05-05_TI3bU3zVF8BEzrMV.jpg");
        System.out.println("搜索成功，结果 URL：" + imagePageUrl);
    }
}
