package com.ggj.article.module.sys.service;

import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggj.article.module.common.crud.CrudService;
import com.ggj.article.module.sys.dao.RoleDao;
import com.ggj.article.module.sys.entity.Role;

/**
 * @ClassName:RoleService.java
 * @Description:  角色  
 * @author gaoguangjin
 * @Date 2015-11-2 下午5:34:17
 */
@Service
@Transactional(readOnly = true)
public class RoleService extends CrudService<RoleDao, Role> {
	
	@Transactional(readOnly = false)
	public void save(Role role) {
		if (role.getId() != null) {
			dao.deleteRoleMenu(role);
			dao.update(role);
		} else {
			role.setCreateDate(new Date());
			dao.insert(role);
		}
		if (!CollectionUtils.isEmpty(role.getMenuIdList())) {
			role.setCreateDate(new Date());
			dao.saveRoleMenu(role);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Role role) {
		super.delete(role);
		dao.deleteRoleMenu(role);
	}
}
