package com.ggj.article.module.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.common.shiro.authc.Principal;

/**
 * @ClassName:UserUtils.java
 * @Description:用户信息工具类    
 * @author gaoguangjin
 * @Date 2015-9-24 上午10:44:46
 */
public class UserUtils {
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null) {
				return principal;
			}
		} catch (UnavailableSecurityManagerException e) {
		} catch (InvalidSessionException e) {
		}
		return null;
	}
	
	/**
	 * @Description:  获取当前用户登陆信息
	 * @return:UserInfo
	 */
	public static UserInfo getCurrentUserInfo() {
		Principal currentUser = getPrincipal();
		if (currentUser != null)
			return currentUser.getUserInfo();
		return null;
	}
	
	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
		} catch (InvalidSessionException e) {
		}
		return null;
	}
}
