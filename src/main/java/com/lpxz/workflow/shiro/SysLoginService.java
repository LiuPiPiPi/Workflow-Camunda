package com.lpxz.workflow.shiro;

import cn.hutool.core.util.StrUtil;
import com.lpxz.workflow.common.BaseException;
import com.lpxz.workflow.constant.UserConstants;
import com.lpxz.workflow.domain.SysUser;
import com.lpxz.workflow.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Logger;

/**
 * @author LPxz
 * @date 2023/5/31
 */
@Service
public class SysLoginService {

    private final static Logger logger = Logger.getLogger(SysLoginService.class.getName());

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPasswordService passwordService;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    public boolean register(SysUser user) {
        String username = user.getUserAccount(), password = user.getUserPassword();
        if (StrUtil.isEmpty(username)) {
            throw new BaseException("用户名不能为空");
        } else if (StrUtil.isEmpty(password)) {
            throw new BaseException("用户密码不能为空");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkAccountUnique(username))) {
            throw new BaseException("保存用户[" + username + "]失败，注册账号已存在");
        } else {
            user.setSalt(ShiroUtils.randomSalt());
            user.setUserPassword(passwordService.encryptPassword(user.getUserAccount(), user.getUserPassword(), user.getSalt()));
        }
        return userService.registerUser(user);
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    public SysUser login(String account, String password) {
        // 用户名或密码为空 错误
        if (StrUtil.isEmpty(account) || StrUtil.isEmpty(password)) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            logger.warning("username or password is null.");
            throw new BaseException();
        }
        // 查询用户信息
        SysUser user = userService.selectUserByAccount(account);

        if (user == null && maybeMobilePhoneNumber(account)) {
            user = userService.selectUserByPhoneNumber(account);
        }
        if (user == null && maybeEmail(account)) {
            user = userService.selectUserByEmail(account);
        }
        if (user == null) {
            logger.warning("user is null.");
            throw new BaseException();
        }
        passwordService.validate(user, password);
        recordLoginInfo(user);
        return user;
    }

    private boolean maybeEmail(String account) {
        return account.matches(UserConstants.EMAIL_PATTERN);
    }

    private boolean maybeMobilePhoneNumber(String account) {
        return account.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN);
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user) {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(new Date());
        userService.updateUserInfo(user);
    }
}
