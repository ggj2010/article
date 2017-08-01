package com.ggj.article.module.configure;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.ggj.article.module.common.GlobalConstants;
import com.ggj.article.module.common.shiro.authc.FormAuthenticationFilter;
import com.ggj.article.module.common.shiro.authc.SystemAuthorizingRealm;
import com.ggj.article.module.common.shiro.session.CacheSessionDAO;
import com.ggj.article.module.common.shiro.session.SessionManager;

/**
 * @author:gaoguangjin
 * @date 2017/5/14 14:26
 */
@Configuration
public class ShiroConfiguration {
	//
	// @Bean("logout")
	// public LogoutFilter getLogoutFilter() {
	// LogoutFilter logoutFilter = new LogoutFilter();
	// logoutFilter.setRedirectUrl("/");
	// return logoutFilter;
	// }
	
	@Bean(name = "shiroEhcacheManager")
	public EhCacheManager getEhCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		return em;
	}
	
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}
	
	@Bean(name = "systemAuthorizingRealm")
	public SystemAuthorizingRealm getSystemAuthorizingRealm() {
		SystemAuthorizingRealm sar = new SystemAuthorizingRealm();
		sar.setCachingEnabled(true);
		sar.setAuthenticationCachingEnabled(false);
		sar.setAuthenticationCacheName("authenticationCache");
		sar.setAuthorizationCachingEnabled(true);
		sar.setAuthorizationCacheName("authorizationCache");
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		hcm.setHashAlgorithmName(GlobalConstants.HASH_ALGORITHM_NMAE);
		hcm.setHashIterations(GlobalConstants.HASH_ITERATIONS);
		sar.setCredentialsMatcher(hcm);
		return sar;
	}
	
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager() {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setRealm(getSystemAuthorizingRealm());
		dwsm.setCacheManager(getEhCacheManager());
		dwsm.setSessionManager(getSessionManager());
		return dwsm;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(getDefaultWebSecurityManager());
		return new AuthorizationAttributeSourceAdvisor();
	}
	
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的连接
		shiroFilterFactoryBean.setSuccessUrl("/");
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorizedUrl");
		Map<String, Filter> filters = new HashMap<String, Filter>();
		FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
		formAuthenticationFilter.setUsernameParam("userName");
		filters.put("authc", formAuthenticationFilter);
		shiroFilterFactoryBean.setFilters(filters);
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/login/assets/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/image/** ", "anon");
		filterChainDefinitionMap.put("/login", "authc");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/**", "user");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	
	public SessionManager getSessionManager() {
		SessionManager sessionManager = new SessionManager();
		// sessionManager.setSessionFactory(new OnlineSessionFactory());
		sessionManager.setGlobalSessionTimeout(1800000);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		QuartzSessionValidationScheduler qvs = new QuartzSessionValidationScheduler();
		qvs.setSessionValidationInterval(1200000);
		qvs.setSessionManager(sessionManager);
		sessionManager.setSessionValidationScheduler(qvs);
		CacheSessionDAO sessionDAO = new CacheSessionDAO();
		sessionDAO.setCacheManager(getEhCacheManager());
		sessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
		sessionDAO.setActiveSessionsCacheName("activeSessionsCache");
		sessionManager.setSessionDAO(sessionDAO);
		SimpleCookie sessionIdCookie = new SimpleCookie("sid");
		sessionIdCookie.setMaxAge(180000);
		sessionIdCookie.setHttpOnly(true);
		sessionManager.setSessionIdCookie(sessionIdCookie);
		return sessionManager;
	}
}
