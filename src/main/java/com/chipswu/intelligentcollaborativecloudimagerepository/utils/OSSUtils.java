package com.chipswu.intelligentcollaborativecloudimagerepository.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture.ImageInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
    public ImageInfo putPictureObject(String objectName, File file) {
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file);
        // 上传文件
        ossClient.putObject(putObjectRequest);
        // 对图片处理（获取基本信息也被视作为一种图片的处理）
        String url = this.getUrl(objectName);
        return this.getImageInfo(url);
    }

    /**
     * 删除文件或目录
     *
     * @param objectName 文件完整路径（存储路径【不包含 bucketName】 + 文件名称【带文件类型】）
     * @throws OSSException    服务端异常
     * @throws ClientException 客户端异常
     */
    public void delete(String objectName) throws OSSException, ClientException {
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
