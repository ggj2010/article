package com.ggj.article.module.common.shiro.authc;

import java.io.Serializable;
import java.util.List;

import com.ggj.article.module.business.bean.UserInfo;

import com.ggj.article.module.sys.entity.Menu;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName:Principal.java
 * @Description:存放用户信息    
 * @author gaoguangjin
 * @Date 2015-9-15 下午4:18:04
 */
@Getter
@Setter
public class Principal implements Serializable {
	
	private UserInfo userInfo;
	private List<Menu> menuList;

	private int id; // 编号
	
	private String loginName; // 登录名
	
	private String name; // 姓名
	
	private Long userType; // 姓名
	
	private String level; // 会员等级
	
	public Principal(String email, UserInfo userInfo) {
		this.loginName = email;
		this.userInfo = userInfo;
		this.name = userInfo.getUserName();
		this.userType = userInfo.getUserType();
		this.id = userInfo.getId();
		if (userInfo.getCustomInfo() != null) {
			this.level = userInfo.getCustomInfo().getLevel();
		}
	}
}
