package com.lpxz.workflow.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LPxz
 * @date 2023/5/31
 */
@Data
@AllArgsConstructor
public class SysUserRole {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
