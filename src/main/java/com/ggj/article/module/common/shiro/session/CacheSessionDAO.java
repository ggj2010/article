package com.ggj.article.module.common.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统安全认证实现类
 * @author ThinkGem
 * @version 2014-7-24
 */
@Slf4j
public class CacheSessionDAO extends EnterpriseCacheSessionDAO {
	private final static String url = ".css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.htm,.html,.crx,.xpi,.exe,.ipa,.apk";
	private final static String[] staticFiles = StringUtils.split(url, ",");
	
	public CacheSessionDAO() {
		super();
	}
	
	@Override
	protected void doUpdate(Session session) {
		if (session == null || session.getId() == null) {
			return;
		}
		HttpServletRequest request = getRequest();
		if (request != null) {
			String uri = request.getServletPath();
			// 如果是静态文件，则不更新SESSION
			if (StringUtils.endsWithAny(uri, staticFiles)) {
				return;
			}
			// 如果是视图文件，则不更新SESSION
			if (StringUtils.startsWith(uri, "/view") && StringUtils.endsWith(uri, (".jsp"))) {
				return;
			}
		}
		super.doUpdate(session);
		log.debug("update {} {}", session.getId(), request != null ? request.getRequestURI() : "");
	}
	
	@Override
	protected void doDelete(Session session) {
		if (session == null || session.getId() == null) {
			return;
		}
		
		super.doDelete(session);
		log.debug("delete {} ", session.getId());
	}
	
	// 【2】createsession
	@Override
	protected Serializable doCreate(Session session) {
		HttpServletRequest request = getRequest();
		if (request != null) {
			String uri = request.getServletPath();
			// 如果是静态文件，则不创建SESSION
			if (StringUtils.endsWithAny(uri, staticFiles)) {
				return null;
			}
		}
		super.doCreate(session);
		log.debug("doCreate {} {}", session, request != null ? request.getRequestURI() : "");
		return session.getId();
	}
	
	@Override
	protected Session doReadSession(Serializable sessionId) {
		return super.doReadSession(sessionId);
	}
	
	// 【1】先read
	@Override
	public Session readSession(Serializable sessionId) throws UnknownSessionException {
		try {
			Session s = null;
			HttpServletRequest request = getRequest();
			if (request != null) {
				String uri = request.getServletPath();
				// 如果是静态文件，则不创建SESSION
				if (StringUtils.endsWithAny(uri, staticFiles)) {
					return null;
				}
				s = (Session) request.getAttribute("session_" + sessionId);
			}
			if (s != null) {
				return s;
			}
			
			Session session = super.readSession(sessionId);
			log.debug("readSession {} {}", sessionId, request != null ? request.getRequestURI() : "");
			
			if (request != null && session != null) {
				request.setAttribute("session_" + sessionId, session);
			}
			
			return session;
		} catch (UnknownSessionException e) {
			return null;
		}
	}
	
	/**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @return
	 */
	public Collection<Session> getActiveSessions(boolean includeLeave) {
		return getActiveSessions(includeLeave, null, null);
	}
	
	/**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @param principal 根据登录者对象获取活动会话
	 * @param filterSession 不为空，则过滤掉（不包含）这个会话。
	 * @return
	 */
	public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession) {
		// 如果包括离线，并无登录者条件。
		if (includeLeave && principal == null) {
			return getActiveSessions();
		}
		Set<Session> sessions = new HashSet<Session>();
		for (Session session : getActiveSessions()) {
			boolean isActiveSession = false;
			// 不包括离线并符合最后访问时间小于等于3分钟条件。
			isActiveSession = true;
			// 符合登陆者条件。
			if (principal != null) {
				PrincipalCollection pc = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				if (principal.toString().equals(pc != null ? pc.getPrimaryPrincipal().toString() : StringUtils.EMPTY)) {
					isActiveSession = true;
				}
			}
			// 过滤掉的SESSION
			if (filterSession != null && filterSession.getId().equals(session.getId())) {
				isActiveSession = false;
			}
			if (isActiveSession) {
				sessions.add(session);
			}
		}
		return sessions;
	}
	
	/**
	 * 获取当前请求对象
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		try {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e) {
			return null;
		}
	}
	
}
