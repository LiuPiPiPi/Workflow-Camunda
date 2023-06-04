package com.lpxz.workflow.shiro;

import cn.hutool.core.util.ObjectUtil;
import com.lpxz.workflow.domain.SysUser;
import com.lpxz.workflow.shiro.realm.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;

import java.security.Principal;

/**
 * @author LPxz
 * @date 2023/5/31
 */
public class ShiroUtil {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static String getIp() {
        return getSubject().getSession().getHost();
    }

    public static SysUser getSysUser() {
        SysUser user = null;
        Object obj = getSubject().getPrincipal();
        if (ObjectUtil.isNotNull(obj)) {
            user = (SysUser) ObjectUtil.cloneByStream(obj);
        }
        return user;
    }

    public static void setSysUser(SysUser user) {
        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }

    /**
     * 生成随机盐
     */
    public static String randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        return new SecureRandomNumberGenerator().nextBytes(3).toHex();
    }
}
