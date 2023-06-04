package com.lpxz.workflow.domain;

import com.lpxz.workflow.common.domain.BaseEntity;
import lombok.Data;

/**
 * @author LPxz
 * @date 2023/6/4
 */
@Data
public class SysRoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;
}
