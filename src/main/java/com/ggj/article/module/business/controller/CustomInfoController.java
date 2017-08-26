package com.ggj.article.module.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ggj.article.module.business.bean.CustomUserInfo;
import com.ggj.article.module.common.utils.UserUtils;
import com.ggj.article.module.sys.entity.DictionaryTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ggj.article.module.base.web.BaseController;
import com.ggj.article.module.business.bean.CustomInfo;
import com.ggj.article.module.business.service.CustomInfoService;
import com.ggj.article.module.common.utils.PageUtils;
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
@RequestMapping("customInfo")
@Slf4j
public class CustomInfoController extends BaseController {
	
	@Autowired
	private PageUtils pageUtils;
	
	@Autowired
	private CustomInfoService customInfoService;
	
	@Autowired
	private DictionaryTableService dictionaryTableService;
	
	@ModelAttribute
	public CustomInfo get(@RequestParam(required = false) Integer id) {
		CustomInfo customInfo = null;
		if (id != null) {
			customInfo = customInfoService.get(id);
		}
		if (customInfo == null) {
			customInfo = new CustomInfo();
		}
		return customInfo;
	}
	
	@RequiresPermissions("bussiness:customInfo:view")
	@RequestMapping(value = "")
	public String list(CustomInfo customInfo, HttpServletRequest request, HttpServletResponse rep, Model model) {
		pageUtils.setPage(request, rep);
		PageInfo<CustomInfo> pageInfo = customInfoService.findPage(customInfo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("customInfo", customInfo);
		model.addAttribute("customStatusList", dictionaryTableService.findList(new DictionaryTable("custom_status")));
		return "bussiness/customInfo/bussiness_customInfo_list";
	}
	@RequiresPermissions("bussiness:customInfo:view")
	@RequestMapping(value = "user")
	public String listUserCustom(CustomInfo customInfo, HttpServletRequest request, HttpServletResponse rep, Model model) {
		pageUtils.setPage(request, rep);
		PageInfo<CustomUserInfo> pageInfo =new PageInfo<CustomUserInfo>(customInfoService.getCustomUser(new CustomUserInfo(UserUtils.getPrincipal().getId())));
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("customInfo", customInfo);
		model.addAttribute("formUrl", "user");
		model.addAttribute("customStatusList", dictionaryTableService.findList(new DictionaryTable("custom_status")));
		return "bussiness/customInfo/bussiness_customInfo_list";
	}

	@RequiresPermissions("bussiness:customInfo:verify")
	@RequestMapping(value = "verify/view")
	public String verifyView(CustomInfo customInfo, HttpServletRequest request, HttpServletResponse rep, Model model) {
		model.addAttribute("customInfo", customInfoService.getRole(customInfo.getId()));
		return "bussiness/customInfo/bussiness_customInfo_verify";
	}

	@RequiresPermissions("bussiness:customInfo:view")
	@RequestMapping(value = "form")
	public String form(CustomInfo customInfo, Model model) {
		model.addAttribute("customInfo", customInfo);
		model.addAttribute("customLevelList", dictionaryTableService.getValueList(new DictionaryTable("custom_level")));
		return "bussiness/customInfo/bussiness_customInfo_form";
	}
	
	@RequiresPermissions("bussiness:customInfo:verify")
	@RequestMapping(value = "approvesave")
	public String approveSave(@Valid CustomInfo customInfo, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "bussiness/customInfo/bussiness_customInfo_verify";
		} else {
			try {
				customInfoService.approveSave(customInfo);
				addMessage(redirectAttributes, "员工成功!");
			} catch (Exception e) {
				addMessage(redirectAttributes, e.getMessage());
			}
		}
		return "redirect:/customInfo/";
	}
	@RequiresPermissions("bussiness:customInfo:edit")
	@RequestMapping(value = "save")
	public String save(@Valid CustomInfo customInfo, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "bussiness/customInfo/bussiness_customInfo_form";
		} else {
			try {
				customInfoService.save(customInfo);
				addMessage(redirectAttributes, "员工保存成功!");
			} catch (Exception e) {
				addMessage(redirectAttributes, e.getMessage());
			}
		}
		if(StringUtils.isNotEmpty(customInfo.getFormUrl())){
			return "redirect:/customInfo/"+customInfo.getFormUrl();
		}
		return "redirect:/customInfo/";
	}

	@RequiresPermissions("bussiness:customInfo:edit")
	@RequestMapping(value = "/delete")
	public String delete(CustomInfo customInfo, RedirectAttributes redirectAttributes) {
		try {
			if (customInfo != null && customInfo.getId() != null) {
				customInfoService.delete(customInfo);
				addMessage(redirectAttributes, "员工删除成功!");
			}
		} catch (Exception e) {
			log.error("删除员工失败！" + e.getLocalizedMessage());
		}
		if(StringUtils.isNotEmpty(customInfo.getFormUrl())){
			return "redirect:/customInfo/"+customInfo.getFormUrl();
		}
		return "redirect:/customInfo/";
	}
}
