package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture;

import com.chipswu.intelligentcollaborativecloudimagerepository.api.aliyunai.model.CreateOutPaintingTaskRequest;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 创建扩图任务请求类
 *
 * @author WuJiaJun
 */
@Data
public class CreatePictureOutPaintingTaskRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5103433872542806138L;

    /**
     * 图片 id
     */
    private Long pictureId;

    /**
     * 扩图参数
     */
    private CreateOutPaintingTaskRequest.Parameters parameters;

}
