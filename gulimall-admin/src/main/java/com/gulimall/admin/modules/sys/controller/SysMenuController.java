/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.gulimall.admin.modules.sys.controller;

import com.gulimall.admin.common.annotation.SysLog;
import com.gulimall.admin.modules.sys.service.ShiroService;
import com.gulimall.admin.common.exception.RRException;
import com.gulimall.admin.common.utils.Constant;
import com.gulimall.admin.common.utils.R;
import com.gulimall.admin.modules.sys.entity.SysMenuEntity;
import com.gulimall.admin.modules.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private ShiroService shiroService;

	/**
	 * 导航菜单
	 */
	@GetMapping("/nav")
	public R nav(){
		// 获取当前用户的菜单列表
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
		return R.ok().put("data", menuList);
	}

	/**
	 * 权限列表
	 */
	@GetMapping("/permissions")
	public R permissions(){
		Set<String> permissions = shiroService.getUserPermissions(getUserId());
		return R.ok().put("data", permissions);
	}

	/**
	 * 所有菜单列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public R list(SysMenuEntity sysMenuEntity){
		List<SysMenuEntity> menuList = sysMenuService.listWithTree(sysMenuEntity);
		// List<SysMenuEntity> menuList = sysMenuService.list();
		// HashMap<Long, SysMenuEntity> menuMap = new HashMap<>(12);
		// for (SysMenuEntity s : menuList) {
		// 	menuMap.put(s.getMenuId(), s);
		// }
		// for (SysMenuEntity s : menuList) {
		// 	SysMenuEntity parent = menuMap.get(s.getParentId());
		// 	if (Objects.nonNull(parent)) {
		// 		s.setParentName(parent.getName());
		// 	}
		//
		// }


		return R.ok().put("data", menuList);
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@GetMapping("/select")
	public R select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

		return R.ok().put("data", menuList);
	}

	/**
	 * 菜单信息
	 */
	@GetMapping("/info/{menuId}")
	public R info(@PathVariable("menuId") Long menuId){
		SysMenuEntity menu = sysMenuService.getById(menuId);
		return R.ok().put("menu", menu);
	}

	/**
	 * 保存
	 */
	@SysLog("保存菜单")
	@PostMapping("/save")
	public R save(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.save(menu);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@PostMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public R update(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.updateById(menu);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@DeleteMapping("/delete/{menuId}")
	// @RequiresPermissions("sys:menu:delete")
	public R delete(@PathVariable("menuId") long menuId){
		/*
		if(menuId <= 31){
			return R.error("系统菜单，不能删除");
		}
		*/

		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		sysMenuService.delete(menuId);

		return R.ok();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}

		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}

		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getPath())){
				throw new RRException("菜单路径不能为空");
			}
		}

		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}

		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}

		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
