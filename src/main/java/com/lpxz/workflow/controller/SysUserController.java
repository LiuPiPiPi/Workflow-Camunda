package com.lpxz.workflow.controller;

import com.lpxz.workflow.common.BaseController;
import com.lpxz.workflow.constant.UserConstants;
import com.lpxz.workflow.domain.SysRole;
import com.lpxz.workflow.domain.SysUser;
import com.lpxz.workflow.service.ISysUserService;
import com.lpxz.workflow.shiro.ShiroUtils;
import com.lpxz.workflow.shiro.SysPasswordService;
import com.lpxz.workflow.util.Resp;
import io.swagger.annotations.ApiOperation;
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
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPasswordService passwordService;

    //    @RequiresPermissions("system:role:list")
    @ApiOperation("查询用户列表")
    @PostMapping("/list")
    public Resp list(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        return success(list);
    }

    //    @RequiresPermissions("system:user:insert")
    @ApiOperation("注册新用户")
    @PostMapping("/insert")
    public Resp insert(@Validated SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkAccountUnique(user.getUserAccount()))) {
            return Resp.error("新增用户'" + user.getUserAccount() + "'失败，登录账号已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return Resp.error("新增用户'" + user.getUserAccount() + "'失败，手机号码已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return Resp.error("新增用户'" + user.getUserAccount() + "'失败，邮箱账号已存在");
        }
        user.setSalt(ShiroUtils.randomSalt());
        user.setUserPassword(passwordService.encryptPassword(user.getUserAccount(), user.getUserPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getSysUser().getUserAccount());
        return toResp(userService.insertUser(user), "insert user error.");
    }

    //    @RequiresPermissions("system:user:update")
    @ApiOperation("编辑用户信息")
    @PostMapping("/update")
    public Resp update(@Validated SysUser user) {
        userService.checkUserAllowed(user);
        if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return error("修改用户'" + user.getUserAccount() + "'失败，手机号码已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("修改用户'" + user.getUserAccount() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(ShiroUtils.getSysUser().getUserAccount());
        return toResp(userService.updateUser(user), "update user error.");
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/delete")
    public Resp delete(@Validated SysUser user) {
        userService.deleteUserById(user.getUserId());
        return success("删除成功");
    }

//    @RequiresPermissions("system:user:insert")
    @ApiOperation("用户授权角色")
    @PostMapping("/updateUserRole")
    public Resp updateUserRole(Long userId, Long[] roleIds) {
        userService.insertUserAuth(userId, roleIds);
        return success();
    }
}
