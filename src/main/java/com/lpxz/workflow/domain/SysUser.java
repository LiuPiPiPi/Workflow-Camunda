package com.lpxz.workflow.domain;

import com.lpxz.workflow.common.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * @author LPxz
 * @date 2023/5/28
 */
@Data
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID
     */
    @NotNull
    private Long userId;

    /**
     * 角色 ID
     */
    private Long roleId;

    /**
     * 账号
     */
    @NotNull(message = "用户账号不能为空")
    private String userAccount;

    /**
     * 用户名称
     */
    @NotNull(message = "用户名称不能为空")
    private String nickName;

    /**
     * 用户邮箱
     */
    @Email
    private String userEmail;

    /**
     * 手机号码
     */
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式有误")
    private String phoneNumber;

    /**
     * 密码
     */
//    @NotNull(message = "用户密码不能为空")
    private String userPassword;

    /**
     * 用户性别 0=男,1=女,2=未知
     */
    private String userSex;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 随机盐
     */
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;

    /**
     * 最后登陆 IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private Date loginDate;

    /**
     * 角色组
     */
    private List<SysRole> roles;

    /**
     * 角色 ID 组
     */
    private Long[] roleIds;

    public SysUser() {
    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public SysUser(Long userId, String userAccount, String nickName, String userPassword) {
        this.userId = userId;
        this.userAccount = userAccount;
        this.nickName = nickName;
        this.userPassword = userPassword;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
