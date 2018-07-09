package com.ggj.article.module.business.service;

import java.util.Date;
import java.util.List;

import com.ggj.article.module.common.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggj.article.module.business.bean.MediaEditor;
import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.business.bean.UserInfoRole;
import com.ggj.article.module.business.dao.MediaEditorMapper;
import com.ggj.article.module.business.dao.UserInfoMapper;
import com.ggj.article.module.business.dao.UserInfoRoleMapper;
import com.ggj.article.module.common.GlobalConstants;
import com.ggj.article.module.common.crud.CrudService;
import com.ggj.article.module.common.utils.IdGen;

/**
 * @author:gaoguagjin
 * @date 2017/5/14 18:14
 */
@Service
@Transactional(readOnly = true)
public class UserInfoService extends CrudService<UserInfoMapper, UserInfo> {
	
	@Autowired
	protected UserInfoRoleMapper userInfoRoleMapper;
	
	@Autowired
	protected MediaEditorMapper mediaEditorMapper;
	
	@Transactional(readOnly = false)
	public void saveUserInfo(UserInfo userInfo) throws Exception {
		userInfo.setStatus(userInfo.getStatus()<=0?1:userInfo.getStatus());
		String salt = IdGen.getSalt("");
		String enyCrptPassword = new SimpleHash(GlobalConstants.HASH_ALGORITHM_NMAE, userInfo.getPassword(),
				ByteSource.Util.bytes(salt), GlobalConstants.HASH_ITERATIONS).toHex();
		userInfo.setPassword(enyCrptPassword);
		userInfo.setSalt(salt);
		// 空的时候查询订单是否存在
		if (userInfo.getId() == null) {
			List<UserInfo> userinfoList = dao.findList(new UserInfo(userInfo.getLoginName()));
			if (!CollectionUtils.isEmpty(userinfoList)) {
				throw new Exception(userInfo.getLoginName() + "该登录名已存在，请勿重复添加");
			}
			userInfo.setCreateDate(new Date());
			userInfo.setUserType(0l);
			dao.insert(userInfo);
		} else {
			// 删除所有角色权限
			userInfoRoleMapper.delete(new UserInfoRole(userInfo.getId()));
			dao.update(userInfo);
		}
		if (!CollectionUtils.isEmpty(userInfo.getRoleIdList())) {
			userInfo.setCreateDate(new Date());
			userInfoRoleMapper.saveUserInfoRole(userInfo);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(UserInfo userInfo) {
		dao.delete(userInfo);
		userInfoRoleMapper.delete(new UserInfoRole(userInfo.getId()));
		mediaEditorMapper.delete(new MediaEditor(userInfo.getId()));
	}
	
	@Transactional(readOnly = false)
	public void addMedia(UserInfo userInfo) {
		if (!CollectionUtils.isEmpty(userInfo.getMediaIdList())) {
			userInfo.setCreateDate(new Date());
			mediaEditorMapper.delete(new MediaEditor(userInfo.getId()));
			mediaEditorMapper.saveUserInfoMedia(userInfo);
		}
	}

	public UserInfo getMedia(UserInfo userInfo) {
		return dao.getMedia(userInfo.getId());
	}

	public void updatePassword(String password) {
		UserInfo userInfo = UserUtils.getPrincipal().getUserInfo();
		String salt = IdGen.getSalt("");
		String enyCrptPassword = new SimpleHash(GlobalConstants.HASH_ALGORITHM_NMAE, password,
				ByteSource.Util.bytes(salt), GlobalConstants.HASH_ITERATIONS).toHex();
		userInfo.setPassword(enyCrptPassword);
		userInfo.setSalt(salt);
		dao.update(userInfo);
	}
}
