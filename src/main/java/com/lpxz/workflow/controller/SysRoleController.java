package com.lpxz.workflow.controller;

import com.lpxz.workflow.constant.UserConstants;
import com.lpxz.workflow.domain.SysRole;
import com.lpxz.workflow.service.ISysRoleService;
import com.lpxz.workflow.service.ISysUserService;
import com.lpxz.workflow.shiro.ShiroUtil;
import com.lpxz.workflow.util.Resp;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LPxz
 * @date 2023/6/4
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    //    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public Resp list(SysRole role) {
        List<SysRole> list = roleService.selectRoleList(role);
        return Resp.success(list);
    }

    //    @RequiresPermissions("system:role:insert")
    @PostMapping("/insert")
    public Resp insert(SysRole role) {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return Resp.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return Resp.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(ShiroUtil.getSysUser().getUserAccount());
        ShiroUtil.clearCachedAuthorizationInfo();
        return Resp.row(roleService.insertRole(role));
    }

    @PostMapping("/update")
    public Resp update(SysRole role) {
        return null;
    }

    @DeleteMapping("/delete")
    public Resp delete(SysRole role) {
        return null;
    }
}
