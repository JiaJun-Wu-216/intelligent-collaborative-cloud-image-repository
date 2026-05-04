package com.chipswu.intelligentcollaborativecloudimagerepository.template;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.BusinessException;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.ErrorCode;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.file.UploadPictureResult;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture.ImageInfo;
import com.chipswu.intelligentcollaborativecloudimagerepository.utils.OSSUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Date;

/**
 * 图片上传模板
 *
 * @author WuJiaJun
 */
@Slf4j
public abstract class PictureUploadTemplate {

    @Resource
    protected OSSUtils ossUtils;

    /**
     * 模板方法，定义上传流程
     *
     * @param inputSource      文件输入源
     * @param uploadPathPrefix 上传地址前缀
     * @return 上传图片结果
     */
    public final UploadPictureResult uploadPicture(Object inputSource, String uploadPathPrefix) {
        // 1. 校验图片
        validPicture(inputSource);

        // 2. 图片上传地址
        String uuid = RandomUtil.randomString(16);
        String originFilename = getOriginFilename(inputSource);
        // 自定义文件名称
        String uploadFilename = String.format(
                "%s_%s.%s",
                DateUtil.formatDate(new Date()),
                uuid,
                FileUtil.getSuffix(originFilename));
        String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFilename);
        // 解析结果并返回
        File tempFile = null;
        try {
            // 3. 创建临时文件
            tempFile = File.createTempFile(uploadPath, null);
            // 处理文件来源（本地或 URL）
            processFile(inputSource, tempFile);
            // 4. 上传图片到对象存储
            ImageInfo imageInfo = ossUtils.putPictureObject(uploadPath, tempFile);
            // 5. 封装返回结果
            return this.buildResult(originFilename, tempFile, uploadPath, imageInfo);
        } catch (Exception e) {
            log.error("图片上传到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            // 6. 清理临时文件
            this.deleteTempFile(tempFile);
        }
    }

    /**
     * 校验输入源（本地文件或 URL）
     *
     * @param inputSource 文件输入源
     */
    protected abstract void validPicture(Object inputSource);

    /**
     * 获取输入源的原始文件名
     *
     * @param inputSource 文件输入源
     * @return 输入源的原始文件名
     */
    protected abstract String getOriginFilename(Object inputSource);

    /**
     * 处理输入源并生成本地临时文件
     *
     * @param inputSource 文件输入源
     * @param tempFile    临时文件
     * @throws Exception 错误信息
     */
    protected abstract void processFile(Object inputSource, File tempFile) throws Exception;

    /**
     * 封装返回结果
     *
     * @param originFilename 文件原始名
     * @param tempFile       临时文件
     * @param uploadPath     上传路径
     * @param imageInfo      图片信息
     * @return 图片上传结果信息
     */
    private UploadPictureResult buildResult(String originFilename, File tempFile, String uploadPath, ImageInfo imageInfo) {
        UploadPictureResult uploadPictureResult = new UploadPictureResult();
        int picWidth = Integer.parseInt(imageInfo.getImageWidth());
        int picHeight = Integer.parseInt(imageInfo.getImageHeight());
        double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
        uploadPictureResult.setPicName(FileUtil.mainName(originFilename));
        uploadPictureResult.setPicWidth(picWidth);
        uploadPictureResult.setPicHeight(picHeight);
        uploadPictureResult.setPicScale(picScale);
        uploadPictureResult.setPicFormat(imageInfo.getFormat());
        uploadPictureResult.setPicSize(FileUtil.size(tempFile));
        uploadPictureResult.setUrl(ossUtils.getUrl(uploadPath));
        return uploadPictureResult;
    }

    /**
     * 删除临时文件
     *
     * @param tempFile 临时文件
     */
    public void deleteTempFile(File tempFile) {
        if (tempFile == null) {
            return;
        }
        boolean deleteResult = tempFile.delete();
        if (!deleteResult) {
            log.error("file delete error, filepath = {}", tempFile.getAbsolutePath());
        }
    }
}