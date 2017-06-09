package com.ggj.article.module.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggj.article.module.common.crud.CrudService;
import com.ggj.article.module.sys.dao.MenuDao;
import com.ggj.article.module.sys.entity.Menu;

@Service
@Transactional(readOnly = true)
public class MenuService extends CrudService<MenuDao, Menu> {
	
	public List<Menu> sortList(List<Menu> sourceList, int parentId, List<Menu> sortList) {
		for(Menu menu : sourceList) {
			if (menu != null && menu.getParentId() == parentId) {
				sortList.add(menu);
				// 是否含有子节点
				for(Menu menuChild : sourceList) {
					if (menuChild != null && menuChild.getParentId() == menu.getId()) {
						sortList(sourceList, menu.getId(), sortList);
						break;
					}
				}
			}
		}
		return sortList;
	}
	
	public List<Menu> findUserRoleMenuList(String roleId) {
		List<Menu> sortList = new ArrayList<Menu>();
		sortList(dao.findUserRoleMenuList(roleId), 1, sortList);
		return sortList;
	}
}
