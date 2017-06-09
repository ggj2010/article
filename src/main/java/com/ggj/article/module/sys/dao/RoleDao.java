package com.ggj.article.module.sys.dao;

import com.ggj.article.module.common.crud.CrudDao;
import com.ggj.article.module.sys.entity.Role;

/**
 * @ClassName:RoleDao.java
 * @Description: 角色 
 * @author gaoguangjin
 * @Date 2015-11-2 下午5:30:17
 */
public interface RoleDao extends CrudDao<Role> {
	
	void saveRoleMenu(Role role);
	
	void deleteRoleMenu(Role role);
	
}
