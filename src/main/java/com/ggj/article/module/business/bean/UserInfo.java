package com.ggj.article.module.business.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ggj.article.module.common.persistence.BaseEntity;
import com.ggj.article.module.sys.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo extends BaseEntity {
	
	private CustomInfo customInfo;
	
	private String password;
	
	private String loginName;
	
	private String userName;
	
	private String phoneNumber;
	
	// 0为员工 1为客户
	private Long userType;
	
	private String salt;
	
	private String roleIds;

	private String mediaIds;

	private List<Media> mediaList = new ArrayList<Media>();
	private List<Integer> mediaIdsList = new ArrayList<Integer>();

	private List<Role> roleList = new ArrayList<Role>();
	private List<Integer> roleIdsList = new ArrayList<Integer>();

	public UserInfo() {
	}
	
	public UserInfo(String loginName) {
		this.loginName = loginName;
	}
	
	public String getRoleIds() {
		return StringUtils.join(getRoleIdList(), ",");
	}
	
	public void setRoleIds(String roleIds) {
		// 防止重复赋值
		roleList = new ArrayList<Role>();
		if (roleIds != null) {
			String[] ids = StringUtils.split(roleIds, ",");
			for(String id : ids) {
				roleList.add(new Role(Integer.parseInt(id)));
			}
		}
	}
	
	/* 根据list获取roleId */
	public List<Integer> getRoleIdList() {
		roleIdsList = new ArrayList<Integer>();
		for(Role role : roleList) {
			roleIdsList.add(role.getId());
		}
		return roleIdsList;
	}




	public String getMediaIds() {
		return StringUtils.join(getMediaIdList(), ",");
	}

	public void setMediaIds(String mediaIds) {
		// 防止重复赋值
		mediaList = new ArrayList<Media>();
		if (mediaIds != null) {
			String[] ids = StringUtils.split(mediaIds, ",");
			for(String id : ids) {
				mediaList.add(new Media(Integer.parseInt(id)));
			}
		}
	}

	/* 根据list获取mediaId */
	public List<Integer> getMediaIdList() {
		mediaIdsList = new ArrayList<Integer>();
		for(Media media : mediaList) {
			mediaIdsList.add(media.getId());
		}
		return mediaIdsList;
	}



}