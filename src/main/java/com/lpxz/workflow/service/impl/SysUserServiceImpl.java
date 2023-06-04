package com.lpxz.workflow.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.lpxz.workflow.common.BaseException;
import com.lpxz.workflow.constant.UserConstants;
import com.lpxz.workflow.domain.SysUser;
import com.lpxz.workflow.domain.SysUserRole;
import com.lpxz.workflow.mapper.SysRoleMapper;
import com.lpxz.workflow.mapper.SysUserMapper;
import com.lpxz.workflow.mapper.SysUserRoleMapper;
import com.lpxz.workflow.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LPxz
 * @date 2023/5/29
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    @Override
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    @Override
    public SysUser selectUserByAccount(String account) {
        return userMapper.selectUserByAccount(account);
    }

    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    @Override
    public SysUser selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public List<SysUserRole> selectUserRoleByUserId(Long userId) {
        return userRoleMapper.selectUserRoleByUserId(userId);
    }

    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
//        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    @Override
    public int deleteUserByIds(String ids) {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        return userMapper.deleteUserByIds(userIds);
    }

    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
//        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user.getUserId(), user.getRoleIds());
        return rows;
    }


    /**
     * 新增用户角色信息
     *
     * @param userId
     * @param roleIds
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (ArrayUtil.isNotEmpty(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                list.add(new SysUserRole(userId, roleId));
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    @Override
    public boolean registerUser(SysUser user) {
        return userMapper.insertUser(user) > 0;
    }

    @Override
    @Transactional
    // todo
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user.getUserId(), user.getRoleIds());
        // 删除用户与岗位关联
//        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
//        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    @Override
    public int updateUserInfo(SysUser user) {
        return userMapper.updateUser(user);
    }

    @Override
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    @Override
    public int resetUserPwd(SysUser user) {
        return 0;
    }

    @Override
    public String checkAccountUnique(String userAccount) {
        if (userMapper.checkAccountUnique(userAccount) > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = ObjectUtil.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhoneNumber());
        if (ObjectUtil.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = ObjectUtil.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getUserEmail());
        if (ObjectUtil.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public void checkUserAllowed(SysUser user) {
        if (ObjectUtil.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new BaseException("不允许操作超级管理员用户");
        }
    }

    @Override
    public String selectUserRoleGroup(Long userId) {
        return null;
    }

    @Override
    public String selectUserPostGroup(Long userId) {
        return null;
    }

    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        return null;
    }

    @Override
    public int changeStatus(SysUser user) {
        return userMapper.updateUser(user);
    }
}
