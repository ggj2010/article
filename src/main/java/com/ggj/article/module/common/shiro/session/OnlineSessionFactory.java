package com.ggj.article.module.common.shiro.session;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;


/**
 * @ClassName:OnlineSessionFactory.java
 * @Description:自定义SessionFactory  创建自定义的session， 添加一些自定义的数据,如 用户登录到的系统ip,用户状态（在线 隐身 强制退出）,当前所在系统等
 * @author gaoguangjin
 * @Date 2015-9-15 下午2:55:54
 */
public class OnlineSessionFactory implements SessionFactory {
	
	@Override
	public Session createSession(SessionContext initData) {
		OnlineSession session = new OnlineSession();
		if (initData != null && initData instanceof WebSessionContext) {
			WebSessionContext sessionContext = (WebSessionContext) initData;
			HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
			if (request != null) {
				session.setUserAgent(request.getHeader("User-Agent"));
				session.setSystemHost(request.getLocalAddr() + ":" + request.getLocalPort());
			}
		}
		return session;
	}
}
