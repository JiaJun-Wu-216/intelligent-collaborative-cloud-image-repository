package com.chipswu.intelligentcollaborativecloudimagerepository.utils;

import cn.hutool.core.io.FileUtil;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture.ImageInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * OSS 工具类
 *
 * @author WuJiaJun
 */
@AllArgsConstructor
public class OSSUtils {
    /**
     * oss 客户端
     */
    private final OSS ossClient;

    /**
     * 桶名称
     */
    private final String bucketName;

    /**
     * 节点
     */
    private final String publicEndpoint;

    private OkHttpClient okHttpClient;

    private ObjectMapper objectMapper;

    /**
     * 文件流式上传
     *
     * @param objectName  文件完整路径（存储路径【不包含 bucketName】 + 文件名称【带文件类型】）
     * @param inputStream 文件输入流
     * @return 文件访问地址
     * @throws OSSException    服务端异常
     * @throws ClientException 客户端异常
     */
    public String upload(String objectName, InputStream inputStream) throws OSSException, ClientException {
        try (inputStream) {
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getUrl(objectName);
    }

    public PutObjectResult putObject(String objectName, File file) {
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file);
        // 上传文件。
        return ossClient.putObject(putObjectRequest);
    }

    public OSSObject getObject(String objectName) {
        // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
        // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
        return ossClient.getObject(new GetObjectRequest(bucketName, objectName));
    }

    /**
     * 上传对象（附带图片信息）
     *
     * @param objectName 文件对象名（路径）
     * @param file       文件信息
     * @return 图像信息
     */
    public Map<String, Object> putPictureObject(String objectName, File file) {
        Map<String, Object> result = new HashMap<>();
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file);
        // 上传文件
        ossClient.putObject(putObjectRequest);
        result.put("objectName", objectName);
        // 图片压缩（转换成 webp 格式）
        int lastDotIndex = objectName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            // 图片处理 - 转换 webp 格式
            // 截取从开头到最后一个 '.' 的部分，并拼接 ".webp"
            String webpObjectName = objectName.substring(0, lastDotIndex) + ".webp";
            String styleType = "image/format,webp";
            // 将处理后的图片命名为example-resize.png并保存到当前Bucket。
            String sbStyle = String.format("%s|sys/saveas,o_%s,b_%s", styleType,
                    // 填写Object完整路径。Object完整路径中不能包含Bucket名称。
                    BinaryUtil.toBase64String(webpObjectName.getBytes()),
                    BinaryUtil.toBase64String(bucketName.getBytes()));
            ProcessObjectRequest request = new ProcessObjectRequest(bucketName, objectName, sbStyle);
            ossClient.processObject(request);
            result.put("webpObjectName", webpObjectName);

            // 图片处理 - 缩略图（仅对 > 20 KB 的图片生成缩略图）
            if (file.length() > 20 * 1024) {
                String thumbnailObjectName = objectName.substring(0, lastDotIndex) + "_thumbnail." + FileUtil.getSuffix(objectName);
                styleType = String.format("image/resize,m_%s,w_%s,h_%s", "lfit", 256, 256);
                // 将处理后的图片命名为example-resize.png并保存到当前Bucket。
                sbStyle = String.format("%s|sys/saveas,o_%s,b_%s", styleType,
                        // 填写Object完整路径。Object完整路径中不能包含Bucket名称。
                        BinaryUtil.toBase64String(thumbnailObjectName.getBytes()),
                        BinaryUtil.toBase64String(bucketName.getBytes()));
                ProcessObjectRequest thumbnailRequest = new ProcessObjectRequest(bucketName, objectName, sbStyle);
                ossClient.processObject(thumbnailRequest);
                result.put("thumbnailObjectName", thumbnailObjectName);
            } else {
                result.put("thumbnailObjectName", null);
            }
            // 对图片处理（获取基本信息也被视作为一种图片的处理）
            String url = this.getUrl(webpObjectName);
            ImageInfo imageInfo = this.getImageInfo(url);
            result.put("imageInfo", imageInfo);
            return result;
        } else {
            result.put("webpObjectName", null);
        }
        // 对图片处理（获取基本信息也被视作为一种图片的处理）
        String url = this.getUrl(objectName);
        ImageInfo imageInfo = this.getImageInfo(url);
        result.put("imageInfo", imageInfo);
        return result;
    }

    /**
     * 删除文件或目录
     *
     * @param url 文件访问路径
     * @throws OSSException    服务端异常
     * @throws ClientException 客户端异常
     */
    public void delete(String url) throws OSSException, ClientException {
        String objectName = ObjectStoreUtils.extractOSSObjectKey(url);
        ossClient.deleteObject(bucketName, objectName);
    }

    /**
     * 获取访问地址
     *
     * @param objectName 文件完整路径（存储路径【不包含 bucketName】 + 文件名称【带文件类型】）
     * @return 访问地址
     */
    public String getUrl(String objectName) {
        // 确保 objectName 不以 / 开头，避免双斜杠
        String normalizedObjectName = objectName.startsWith("/")
                ? objectName.substring(1) : objectName;
        return "https://" + bucketName + "." + publicEndpoint + "/" + normalizedObjectName;
    }

    /**
     * 获取公有读图片的信息
     *
     * @param publicImageUrl 图片的完整公开 URL
     * @return ImageInfo 对象
     */
    public ImageInfo getImageInfo(String publicImageUrl) {
        if (publicImageUrl == null || publicImageUrl.isEmpty()) {
            throw new IllegalArgumentException("Public image URL must not be null or empty");
        }

        // 直接拼接 ?x-oss-process=image/info
        String infoUrl = publicImageUrl.contains("?")
                ? publicImageUrl + "&x-oss-process=image/info"
                : publicImageUrl + "?x-oss-process=image/info";

        Request request = new Request.Builder().url(infoUrl).build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to fetch image info: HTTP " + response.code());
            }

            // 使用 Jackson 直接反序列化为 ImageInfo

            if (response.body() != null) {
                return objectMapper.readValue(response.body().byteStream(), ImageInfo.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
