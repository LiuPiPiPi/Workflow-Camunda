package com.lpxz.workflow.controller;

import cn.hutool.core.util.StrUtil;
import com.lpxz.workflow.domain.SysUser;
import com.lpxz.workflow.shiro.SysLoginService;
import com.lpxz.workflow.util.Resp;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LPxz
 * @date 2023/5/31
 */
@RestController
@RequestMapping("/system")
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    @PostMapping("/register")
    @ResponseBody
    public Resp register(SysUser user) {
        // todo
//        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
//            return Resp.error("当前系统没有开启注册功能！");
//        }
        return loginService.register(user) ? Resp.success() : Resp.error("注册失败,请联系系统管理人员");
    }

    @PostMapping("/login")
    @ResponseBody
    public Resp login(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return Resp.success();
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return Resp.error(msg);
        }
    }
}
