package com.ggj.article.module.business.dao;

import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.common.crud.CrudDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper extends CrudDao<UserInfo> {
	
	UserInfo getMedia(Integer id);

	List<UserInfo> findUserInfoList(@Param("id")Integer id);
}