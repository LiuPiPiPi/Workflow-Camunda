package com.lpxz.workflow.controller;

import com.lpxz.workflow.domain.SysMenu;
import com.lpxz.workflow.service.ISysMenuService;
import com.lpxz.workflow.shiro.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LPxz
 * @date 2023/6/4
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService menuService;

    //    @RequiresPermissions("system:menu:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysMenu> list(SysMenu menu) {
        Long userId = ShiroUtil.getSysUser().getUserId();
        return menuService.selectMenuList(menu, userId);
    }
}
