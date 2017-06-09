package com.ggj.article.module.sys.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName:TagController.java
 * @Description:标签controller    
 * @author gaoguangjin
 * @Date 2015-10-29 上午10:28:05
 */
@Controller
@RequestMapping(value = "/tag")
public class TagController {
	
	/**
	 * @Description:  树状标签
	 * @param url
	 * @return:String
	 */
	@RequiresPermissions("user")
	@RequestMapping("treeselect")
	public String treeselect(String url, String selectIds, Model model, String extId, boolean checked) {
		model.addAttribute("url", url);
		model.addAttribute("selectIds", selectIds);
		model.addAttribute("checked", checked);
		model.addAttribute("extId", extId);
		return "sys/tag/treeselect";
	}
}
