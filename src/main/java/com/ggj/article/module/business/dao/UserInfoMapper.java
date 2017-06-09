package com.ggj.article.module.business.dao;

import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.common.crud.CrudDao;

public interface UserInfoMapper extends CrudDao<UserInfo> {
	
	UserInfo getMedia(Integer id);
}