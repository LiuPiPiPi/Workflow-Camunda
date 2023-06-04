package com.lpxz.workflow.service.impl;

import com.lpxz.workflow.domain.SysRole;
import com.lpxz.workflow.domain.SysRoleMenu;
import com.lpxz.workflow.domain.SysUserRole;
import com.lpxz.workflow.mapper.SysRoleMapper;
import com.lpxz.workflow.mapper.SysRoleMenuMapper;
import com.lpxz.workflow.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author LPxz
 * @date 2023/5/29
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    SysRoleMapper roleMapper;

    @Autowired
    SysRoleMenuMapper roleMenuMapper;

    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }

    @Override
    public Set<String> selectRoleKeys(Long userId) {
        return null;
    }

    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        return null;
    }

    @Override
    public List<SysRole> selectRoleAll() {
        return null;
    }

    @Override
    public SysRole selectRoleById(Long roleId) {
        return null;
    }

    @Override
    public boolean deleteRoleById(Long roleId) {
        return false;
    }

    @Override
    public int deleteRoleByIds(String ids) {
        return 0;
    }

    /**
     * 新增角色信息
     *
     * @param role 角色信息
     * @return
     */
    @Override
    public int insertRole(SysRole role) {
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    @Override
    public int updateRole(SysRole role) {
        return 0;
    }

    @Override
    public int authDataScope(SysRole role) {
        return 0;
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRole role) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    @Override
    public String checkRoleNameUnique(SysRole role) {
        return null;
    }

    @Override
    public String checkRoleKeyUnique(SysRole role) {
        return null;
    }

    @Override
    public void checkRoleAllowed(SysRole role) {

    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return 0;
    }

    @Override
    public int changeStatus(SysRole role) {
        return 0;
    }

    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        return 0;
    }

    @Override
    public int deleteAuthUsers(Long roleId, String userIds) {
        return 0;
    }

    @Override
    public int insertAuthUsers(Long roleId, String userIds) {
        return 0;
    }
}
