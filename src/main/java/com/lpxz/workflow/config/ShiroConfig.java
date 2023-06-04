package com.lpxz.workflow.config;

import com.lpxz.workflow.shiro.realm.UserRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LPxz
 * @date 2023/5/31
 */
@Configuration
public class ShiroConfig {

    // 设置 Cookie 的域名
    @Value("${shiro.cookie.domain}")
    private String domain;

    // 设置 Cookie 的有效访问路径
    @Value("${shiro.cookie.path}")
    private String path;

    // 设置 HttpOnly 属性
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;

    // 设置 Cookie 的过期时间，秒为单位
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        manager.setRememberMeManager(rememberMeManager());
        return manager;
    }

    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * cookie 属性设置
     */
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge * 24 * 60 * 60);
        return cookie;
    }

    /**
     * 记住我
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
        return cookieRememberMeManager;
    }

    /**
     * 开启Shiro注解通知器
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
