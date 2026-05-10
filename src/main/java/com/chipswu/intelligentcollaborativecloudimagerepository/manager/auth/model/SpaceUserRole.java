package com.chipswu.intelligentcollaborativecloudimagerepository.manager.auth.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 空间成员角色类
 *
 * @author WuJiaJun
 */
@Data
public class SpaceUserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 6141660390995497302L;

    /**
     * 角色键
     */
    private String key;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 权限键列表
     */
    private List<String> permissions;

    /**
     * 角色描述
     */
    private String description;

}
