package com.ggj.article.module.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ggj.article.module.base.web.BaseController;
import com.ggj.article.module.business.bean.CustomInfo;
import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.business.service.UserInfoService;
import com.ggj.article.module.common.utils.PageUtils;
import com.ggj.article.module.sys.entity.DictionaryTable;
import com.ggj.article.module.sys.service.DictionaryTableService;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 员工列表
 *
 * @author:gaoguangjin
 * @date 2017/4/25 22:34
 */
@Controller
@RequestMapping("userInfo")
@Slf4j
public class UserInfoController extends BaseController {
	
	@Autowired
	private PageUtils pageUtils;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private DictionaryTableService dictionaryTableService;
	
	@ModelAttribute
	public UserInfo get(@RequestParam(required = false) Integer id) {
		UserInfo userInfo = null;
		if (id != null) {
			userInfo = userInfoService.get(id);
		}
		if (userInfo == null) {
			userInfo = new UserInfo();
		}
		return userInfo;
	}
	
	@RequiresPermissions("bussiness:userInfo:view")
	@RequestMapping(value = "")
	public String list(UserInfo userInfo, HttpServletRequest request, HttpServletResponse rep, Model model) {
		pageUtils.setPage(request, rep);
		userInfo.setUserType(0l);
		PageInfo<UserInfo> pageInfo = userInfoService.findPage(userInfo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("userInfo", userInfo);
		return "bussiness/userInfo/bussiness_userInfo_list";
	}
	
	@RequiresPermissions("bussiness:userInfo:view")
	@RequestMapping(value = "form")
	public String form(UserInfo userInfo, Model model) {
		model.addAttribute("userInfo", userInfo);
		return "bussiness/userInfo/bussiness_userInfo_form";
	}
	
	@RequiresPermissions("bussiness:userInfo:view")
	@RequestMapping(value = "addmedia")
	public String addMedia(UserInfo userInfo, Model model) {
		model.addAttribute("userInfo", userInfo);
		return "bussiness/userInfo/bussiness_userInfo_media";
	}
	
	@RequiresPermissions("bussiness:userInfo:view")
	@RequestMapping(value = "savemedia")
	public String saveMedia(UserInfo userInfo, RedirectAttributes redirectAttributes) {
		try {
			userInfoService.addMedia(userInfo);
			addMessage(redirectAttributes, "保存关联媒体成功!");
		} catch (Exception e) {
			addErrorMessage(redirectAttributes, e.getMessage());
		}
		return "redirect:/userInfo/";
	}
	
	@RequiresPermissions("bussiness:userInfo:view")
	@RequestMapping(value = "addcustom")
	public String addCustom(UserInfo userInfo, Model model) {
		model.addAttribute("customInfo", new CustomInfo(userInfo.getId()));
		model.addAttribute("customLevelList", dictionaryTableService.getValueList(new DictionaryTable("custom_level")));
		return "bussiness/customInfo/bussiness_customInfo_form";
	}
	@RequiresUser
	@RequestMapping(value = "changepwd")
	public String changePassWord(UserInfo userInfo, Model model) {
		return "bussiness/userInfo/bussiness_userInfo_changepwd";
	}
	@RequiresUser
	@RequestMapping(value = "changepwd/save")
	public String changePassword(String password, Model model) {
		userInfoService.updatePassword(password);
		model.addAttribute("message", "密码修改成功!");
		return "bussiness/userInfo/bussiness_userInfo_changepwd";
	}

	@RequiresPermissions("bussiness:userInfo:edit")
	@RequestMapping(value = "save")
	public String save(@Valid UserInfo userInfo, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "bussiness/userInfo/bussiness_userInfo_form";
		} else {
			try {
				userInfoService.saveUserInfo(userInfo);
				addMessage(redirectAttributes, "员工保存成功!");
			} catch (Exception e) {
				addErrorMessage(redirectAttributes, e.getMessage());
			}
		}
		return "redirect:/userInfo/";
	}
	
	@RequiresPermissions("bussiness:userInfo:edit")
	@RequestMapping(value = "/delete")
	public String delete(UserInfo userInfo, RedirectAttributes redirectAttributes) {
		try {
			if (userInfo != null && userInfo.getId() != null) {
				userInfoService.delete(userInfo);
				addMessage(redirectAttributes, "员工删除成功!");
			}
		} catch (Exception e) {
			log.error("删除员工失败！" + e.getLocalizedMessage());
		}
		return "redirect:/userInfo/";
	}
}
