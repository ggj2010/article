package com.ggj.article.module.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.business.service.UserInfoService;
import com.ggj.article.module.common.shiro.authc.Principal;
import com.ggj.article.module.common.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ggj.article.module.common.shiro.authc.FormAuthenticationFilter;
import com.ggj.article.module.common.shiro.authc.SystemAuthorizingRealm;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName:LoginController.java
 * @Description:    用户登陆登陆登出
 * @author gaoguangjin
 * @Date 2015-9-8 下午5:29:40
 */
@Controller
@Slf4j
@RequestMapping("/")
public class LoginController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	SystemAuthorizingRealm ream;
	
//	@Autowired
//	private UserUtils userUtils;
	
	@RequestMapping(value = "login", method = { RequestMethod.GET })
	public String redirectToLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 已经登陆过，就直接跳转到主界面
		if (SecurityUtils.getSubject().getPrincipal() != null) {
			return "redirect:";
		}
		return "login";
	}

	@RequestMapping(value = "register", method = { RequestMethod.GET })
	public String redirectToRegister(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 已经登陆过，就直接跳转到主界面
		if (SecurityUtils.getSubject().getPrincipal() != null) {
			return "redirect:";
		}
		return "register";
	}

	/**
	 * @Description:Shiro登陆验证失败跳转到此页面
	 * @param request
	 * @param response
	 * @param model
	 * @return:String
	 */
	@RequestMapping(value = "login", method = { RequestMethod.POST })
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 登陆失败需要将错误信息打印出来哦
		model.addAttribute("error", "用户名或密码错误");
		return "login";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		model.addAttribute("userName", principal.getName());
		model.addAttribute("menuList", principal.getMenuList());
		return "index";
	}


	@RequestMapping(value = "register/save", method = { RequestMethod.POST })
	public String registerSave(UserInfo userInfo, HttpServletResponse response, Model model) throws Exception {
		userInfo.setId(null);
		userInfo.setStatus(2);
		userInfoService.saveUserInfo(userInfo);
		return "redirect:/";
	}
	
	/****************************shiro相关的内容****************************/
	/**
	 * @Description: 自定义退出 
	 * @return:String
	 */
	@RequestMapping(value = "logouts")
	public String logouts() {
		// TODO 清楚一些缓存，session啊等到
		SecurityUtils.getSubject().logout();
		return "redirect:/index.html";
	}
	@RequestMapping(value = "unauthorizedUrl")
	public String unauthorizedUrl() {
		return "error/unauthorizedExceptions";
	}
}
