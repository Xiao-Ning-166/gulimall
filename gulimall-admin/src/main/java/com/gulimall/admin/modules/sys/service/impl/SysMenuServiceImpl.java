/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.gulimall.admin.modules.sys.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.admin.common.utils.Constant;
import com.gulimall.admin.common.utils.MapUtils;
import com.gulimall.admin.modules.sys.dao.SysMenuDao;
import com.gulimall.admin.modules.sys.entity.SysMenuEntity;
import com.gulimall.admin.modules.sys.service.SysMenuService;
import com.gulimall.admin.modules.sys.service.SysRoleMenuService;
import com.gulimall.admin.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        // 查询不包含按钮的菜单集合
        List<SysMenuEntity> notButtonList = query().ne("type", 2).list();

        List<SysMenuEntity> topMenuList = notButtonList.stream().filter((menu) -> {
            return menu.getParentId() == 0;
        }).map(menu -> {
            // 查询子菜单
            menu.setChildren(getChildrenById(menu.getMenuId(), notButtonList));
            return menu;
        }).sorted((m1, m2) -> {
            // 进行排序
            return (m1.getOrderNum() == null ? 0 : m1.getOrderNum()) - (m2.getOrderNum() == null ? 0 : m2.getOrderNum());
        }).collect(Collectors.toList());

        return topMenuList;
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            return getMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getMenuList(menuIdList);
    }

    /**
     * 获取拥有的菜单列表
     * @param menuIdList
     * @return
     */
    private List<SysMenuEntity> getMenuList(List<Long> menuIdList) {
        // 查询拥有的所有菜单
        List<SysMenuEntity> menus = this.baseMapper.selectList(new QueryWrapper<SysMenuEntity>()
                .in(Objects.nonNull(menuIdList), "menu_id", menuIdList).in("type", 0, 1));
        // 将id和菜单绑定
        HashMap<Long, SysMenuEntity> menuMap = new HashMap<>(12);
        for (SysMenuEntity s : menus) {
            menuMap.put(s.getMenuId(), s);
        }
        // 使用迭代器,组装菜单的层级关系
        Iterator<SysMenuEntity> iterator = menus.iterator();
        while (iterator.hasNext()) {
            SysMenuEntity menu = iterator.next();
            SysMenuEntity parent = menuMap.get(menu.getParentId());
            if (Objects.nonNull(parent)) {
                parent.getChildren().add(menu);
                // 将这个菜单从当前节点移除
                iterator.remove();
            }
        }
        Collections.sort(menus, new Comparator<SysMenuEntity>() {
            @Override
            public int compare(SysMenuEntity o1, SysMenuEntity o2) {
                return (o1.getOrderNum() == null ? 0 : o1.getOrderNum()) - (o2.getOrderNum() == null ? 0 : o2.getOrderNum());
            }
        });
        return menus;
    }

    @Override
    public void delete(Long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        sysRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
    }

    /**
     * 以树形结构查询菜单列表
     *
     * @return
     */
    @Override
    public List<SysMenuEntity> listWithTree(SysMenuEntity sysMenuEntity) {
        // 1、查询所有的菜单
        List<SysMenuEntity> menuList = query()
                .like(StrUtil.isNotBlank(sysMenuEntity.getName()), "name", sysMenuEntity.getName())
                .eq(sysMenuEntity.getType() != null, "type", sysMenuEntity.getType())
                .list();

        // 2、过滤出顶级菜单
        List<SysMenuEntity> topMenuList = menuList.stream().filter(menu -> {
            // 过滤出顶级菜单
            return menu.getParentId() == 0;
        }).map(menu -> {
            // 查询菜单的所有子菜单
            menu.setChildren(getChildrenById(menu.getMenuId(), menuList));
            // 查询父菜单
            SysMenuEntity parentMenu = queryParent(menu.getParentId());
            menu.setParentName(parentMenu.getName());
            return menu;
        }).sorted((m1, m2) -> {
        	// 进行排序
            return (m1.getOrderNum() == null ? 0 : m1.getOrderNum()) - (m2.getOrderNum() == null ? 0 : m2.getOrderNum());
        }).collect(Collectors.toList());

        // 3、返回顶级菜单
        return topMenuList;
    }

    /**
     * 查询父菜单
     *
     * @param parentId 父菜单id
     * @return
     */
    private SysMenuEntity queryParent(Long parentId) {
        SysMenuEntity sysMenuEntity;
        if (parentId == 0) {
            sysMenuEntity = new SysMenuEntity();
            sysMenuEntity.setName("一级菜单");
            sysMenuEntity.setMenuId(0L);
        } else {
            sysMenuEntity = getById(parentId);
        }
        return sysMenuEntity;
    }

    /**
     * 递归获取菜单的所有子菜单
     *
     * @param menuId   菜单id
     * @param menuList 菜单列表
     * @return
     */
    private List<SysMenuEntity> getChildrenById(Long menuId, List<SysMenuEntity> menuList) {
		List<SysMenuEntity> subMenuList = menuList.stream().filter(menu -> {
			// 找到次层子菜单集合
			return menuId.equals(menu.getParentId());
		}).map((menu) -> {
			// 找到本层菜单的子菜单
			menu.setChildren(getChildrenById(menu.getMenuId(), menuList));
            // 查询父菜单
            SysMenuEntity parentMenu = queryParent(menu.getParentId());
            menu.setParentName(parentMenu.getName());
			return menu;
		}).sorted((m1, m2) -> {
			return (m1.getOrderNum() == null ? 0 : m1.getOrderNum()) - (m2.getOrderNum() == null ? 0 : m2.getOrderNum());
		}).collect(Collectors.toList());

		return subMenuList;
	}

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

        for (SysMenuEntity entity : menuList) {
            //目录
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
                entity.setChildren(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }
}
