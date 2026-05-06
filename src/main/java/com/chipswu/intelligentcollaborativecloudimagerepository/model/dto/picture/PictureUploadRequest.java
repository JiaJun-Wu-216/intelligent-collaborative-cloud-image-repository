package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 图片上传请求类
 *
 * @author WuJiaJun
 */
@Data
public class PictureUploadRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 828609575804234739L;

    /**
     * 图片主键（用于修改）
     */
    private Long id;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 空间主键
     */
    private Long spaceId;

}
