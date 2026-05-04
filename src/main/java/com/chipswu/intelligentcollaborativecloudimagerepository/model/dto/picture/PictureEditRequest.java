package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 图片修改请求类
 *
 * @author WuJiaJun
 */
@Data
public class PictureEditRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7048674066104619529L;

    /**
     * id  
     */  
    private Long id;  
  
    /**  
     * 图片名称  
     */  
    private String name;  
  
    /**  
     * 简介  
     */  
    private String introduction;  
  
    /**  
     * 分类  
     */  
    private String category;  
  
    /**  
     * 标签  
     */  
    private List<String> tags;  
  
}
