package com.lpxz.workflow.controller;

import com.lpxz.workflow.constant.UserConstants;
import com.lpxz.workflow.domain.SysRole;
import com.lpxz.workflow.domain.SysUser;
import com.lpxz.workflow.service.ISysRoleService;
import com.lpxz.workflow.service.ISysUserService;
import com.lpxz.workflow.shiro.ShiroUtil;
import com.lpxz.workflow.shiro.SysPasswordService;
import com.lpxz.workflow.util.Resp;
import lombok.extern.java.Log;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LPxz
 * @date 2023/5/29
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPasswordService passwordService;

    //    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public Resp list(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        return Resp.success(list);
    }

    //    @RequiresPermissions("system:user:insert")
    @PostMapping("/insert")
    @ResponseBody
    public Resp insertUser(@Validated SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkAccountUnique(user.getUserAccount()))) {
            return Resp.error("新增用户'" + user.getUserAccount() + "'失败，登录账号已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return Resp.error("新增用户'" + user.getUserAccount() + "'失败，手机号码已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return Resp.error("新增用户'" + user.getUserAccount() + "'失败，邮箱账号已存在");
        }
        user.setSalt(ShiroUtil.randomSalt());
        user.setUserPassword(passwordService.encryptPassword(user.getUserAccount(), user.getUserPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtil.getSysUser().getUserAccount());
        return Resp.row(userService.insertUser(user));
    }

    //    @RequiresPermissions("system:user:update")
    @PostMapping("/update")
    @ResponseBody
    public Resp updateUser(@Validated SysUser user) {
        userService.checkUserAllowed(user);
        if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return Resp.error("修改用户'" + user.getUserAccount() + "'失败，手机号码已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return Resp.error("修改用户'" + user.getUserAccount() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(ShiroUtil.getSysUser().getUserAccount());
        return Resp.row(userService.updateUser(user));
    }

    /**
     * 用户授权角色
     */
//    @RequiresPermissions("system:user:insert")
    @PostMapping("/insertRole")
    @ResponseBody
    public Resp insertRole(Long userId, Long[] roleIds) {
        userService.insertUserAuth(userId, roleIds);
        return Resp.success();
    }

    @DeleteMapping("/delete")
    public Resp delete(SysRole role) {
        return null;
    }
}
