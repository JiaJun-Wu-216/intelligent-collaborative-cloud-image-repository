package com.chipswu.intelligentcollaborativecloudimagerepository.controller;

import com.aliyun.core.utils.IOUtils;
import com.aliyun.oss.model.OSSObject;
import com.chipswu.intelligentcollaborativecloudimagerepository.annotation.AuthCheck;
import com.chipswu.intelligentcollaborativecloudimagerepository.common.BaseResponse;
import com.chipswu.intelligentcollaborativecloudimagerepository.common.ResultUtils;
import com.chipswu.intelligentcollaborativecloudimagerepository.constants.UserConstant;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.BusinessException;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.ErrorCode;
import com.chipswu.intelligentcollaborativecloudimagerepository.utils.OSSUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author WuJiaJun
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private OSSUtils ossUtils;

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/upload")
    public BaseResponse<String> testUploadFile(@RequestPart("file") MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String filePath = String.format("test/%s", originalFilename);
        File tempFile = null;
        try {
            // 上传文件
            tempFile = File.createTempFile(filePath, null);
            multipartFile.transferTo(tempFile);
            ossUtils.putObject(filePath, tempFile);
            // 返回访问地址
            return ResultUtils.success(filePath);
        } catch (Exception e) {
            log.error("file upload error,filePath = {}", filePath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件上传失败");
        } finally {
            if (tempFile != null) {
                // 删除临时文件
                boolean deleteResult = tempFile.delete();
                if (!deleteResult) {
                    log.error("file delete error,filePath = {}", filePath);
                }
            }
        }
    }

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/download")
    public void testDownloadFile(String filePath, HttpServletResponse response) throws IOException {
        InputStream objectInput = null;
        try {
            OSSObject object = ossUtils.getObject(filePath);
            objectInput = object.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(objectInput);
            // 设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filePath);
            // 写入响应
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("file download error,filePath = {}", filePath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件下载失败");
        }finally {
            if (objectInput != null) {
                objectInput.close();
            }
        }
    }
}
