package com.ggj.article.module.sys.dao;

import com.ggj.article.module.common.crud.CrudDao;
import com.ggj.article.module.sys.entity.Menu;

import java.util.List;

public interface MenuDao extends CrudDao<Menu> {
    List<Menu> findUserRoleMenuList(String roleId);
}
