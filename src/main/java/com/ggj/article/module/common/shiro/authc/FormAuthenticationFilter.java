package com.ggj.article.module.common.shiro.authc;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName:FormAuthenticationFilter.java
 * @Description:  shiro登陆from表单重写，捕获错误信息  
 * @author gaoguangjin
 * @Date 2015-9-24 上午10:26:01
 */
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
	public static final String DEFAULT_MESSAGE_PARAM = "message";
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return super.onLoginSuccess(token, subject, request, response);
	}
	
	/**
	 * 登录失败调用事件,这样在方法里面可以进行捕获
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName();
		String message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)) {
			message = "密码错误, 请重试";
		} else if (UnknownAccountException.class.getName().equals(className)) {
			message = "帐号错误, 请重试";
		} else if (AuthenticationException.class.getName().equals(className)) {
			message = e.getMessage();
		} else {
			message = "系统出现问题，请稍后再试！";
		}
		request.setAttribute(getFailureKeyAttribute(), className);
		request.setAttribute(DEFAULT_MESSAGE_PARAM, message);
		return true;
	}
}
