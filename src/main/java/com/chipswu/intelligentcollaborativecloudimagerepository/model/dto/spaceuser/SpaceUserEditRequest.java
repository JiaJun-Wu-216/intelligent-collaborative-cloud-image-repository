package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.spaceuser;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 编辑空间成员请求类
 *
 * @author WuJiaJun
 */
@Data
public class SpaceUserEditRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1636970102516305023L;

    /**
     * id
     */
    private Long id;

    /**
     * 空间角色：viewer/editor/admin
     */
    private String spaceRole;

}
