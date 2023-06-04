package com.lpxz.workflow.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.lpxz.workflow.constant.UserConstants;
import com.lpxz.workflow.domain.SysMenu;
import com.lpxz.workflow.domain.SysUser;
import com.lpxz.workflow.mapper.SysMenuMapper;
import com.lpxz.workflow.mapper.SysRoleMenuMapper;
import com.lpxz.workflow.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author LPxz
 * @date 2023/6/3
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public List<SysMenu> selectMenusByUser(SysUser user) {
        List<SysMenu> menus;
        // 管理员显示所有菜单信息
        if (user.isAdmin()) {
            menus = menuMapper.selectMenuNormalAll();
        } else {
            menus = menuMapper.selectMenusByUserId(user.getUserId());
        }
        return getChildPerms(menus, 0);
    }

    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenu> menuList;
        if (SysUser.isAdmin(userId)) {
            menuList = menuMapper.selectMenuList(menu);
        } else {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    @Override
    public List<SysMenu> selectMenuAll(Long userId) {
        List<SysMenu> menuList;
        if (SysUser.isAdmin(userId)) {
            menuList = menuMapper.selectMenuAll();
        } else {
            menuList = menuMapper.selectMenuAllByUserId(userId);
        }
        return menuList;
    }

    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        return null;
    }

    @Override
    public Map<String, String> selectPermsAll(Long userId) {
        return null;
    }

    @Override
    public int deleteMenuById(Long menuId) {
        return menuMapper.deleteMenuById(menuId);
    }

    @Override
    public SysMenu selectMenuById(Long menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    @Override
    public int selectCountMenuByParentId(Long parentId) {
        return menuMapper.selectCountMenuByParentId(parentId);
    }

    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
    }

    @Override
    public int insertMenu(SysMenu menu) {
        return menuMapper.insertMenu(menu);
    }

    @Override
    public int updateMenu(SysMenu menu) {
        return menuMapper.updateMenu(menu);
    }

    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        Long menuId = ObjectUtil.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (ObjectUtil.isNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu t : list) {
            // 根据传入的某个父节点 ID，遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param menu
     */
    private void recursionFn(List<SysMenu> list, SysMenu menu) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, menu);
        menu.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (getChildList(list, tChild).size() > 0) {
                // 判断是否有子节点
                for (SysMenu n : childList) {
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu menu) {
        List<SysMenu> menuList = new ArrayList<>();
        for (SysMenu n : list) {
            if (n.getParentId().longValue() == menu.getMenuId().longValue()) {
                menuList.add(n);
            }
        }
        return menuList;
    }
}
