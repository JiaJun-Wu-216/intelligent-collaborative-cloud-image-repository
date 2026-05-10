package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.spaceuser;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询空间成员请求类
 *
 * @author WuJiaJun
 */
@Data
public class SpaceUserQueryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7762922981150892126L;

    /**
     * ID
     */
    private Long id;

    /**
     * 空间 ID
     */
    private Long spaceId;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 空间角色：viewer/editor/admin
     */
    private String spaceRole;

}
