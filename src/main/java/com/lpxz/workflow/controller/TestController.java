package com.lpxz.workflow.controller;

import com.lpxz.workflow.domain.SysUser;
import com.lpxz.workflow.service.ISysUserService;
import com.lpxz.workflow.shiro.ShiroUtil;
import com.lpxz.workflow.shiro.SysPasswordService;
import com.lpxz.workflow.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LPxz
 * @date 2023/6/3
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPasswordService passwordService;

    @PostMapping("/insertUser")
    @ResponseBody
    public Resp insertAdmin() {
        SysUser[] users = new SysUser[]{
                new SysUser(1L, "admin", "admin", "admin123"),
                new SysUser(2L, "ry", "ry", "admin123")
        };
        for (SysUser user : users) {
            user.setSalt(ShiroUtil.randomSalt());
            user.setUserPassword(passwordService.encryptPassword(user.getUserAccount(), user.getUserPassword(), user.getSalt()));
            user.setCreateBy("admin");
            userService.insertUser(user);
        }
        return Resp.success();
    }
}
