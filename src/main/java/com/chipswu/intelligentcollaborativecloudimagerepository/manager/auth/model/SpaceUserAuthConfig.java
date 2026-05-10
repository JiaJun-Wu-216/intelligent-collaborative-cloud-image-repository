package com.chipswu.intelligentcollaborativecloudimagerepository.manager.auth.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 权限配置类
 *
 * @author WuJiaJun
 */
@Data
public class SpaceUserAuthConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = -7525851258569608195L;

    /**
     * 权限列表
     */
    private List<SpaceUserPermission> permissions;

    /**
     * 角色列表
     */
    private List<SpaceUserRole> roles;

}
