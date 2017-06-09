package com.ggj.article.module.business.dao;

import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.business.bean.UserInfoRole;
import com.ggj.article.module.common.crud.CrudDao;

public interface UserInfoRoleMapper extends CrudDao<UserInfoRole> {
   void saveUserInfoRole(UserInfo userInfo);
}