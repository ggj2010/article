package com.ggj.article.module.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ggj.article.module.business.bean.Article;
import com.ggj.article.module.business.bean.Media;
import com.ggj.article.module.common.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggj.article.module.base.web.BaseController;
import com.ggj.article.module.business.bean.MediaSettleMent;
import com.ggj.article.module.business.service.MediaSettleMentService;
import com.ggj.article.module.common.utils.PageUtils;
import com.ggj.article.module.sys.entity.DictionaryTable;
import com.ggj.article.module.sys.service.DictionaryTableService;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author:gaoguangjin
 * @date 2017/6/6 17:26
 */
@Controller
@RequestMapping("settle")
@Slf4j
public class MediaSettleController extends BaseController {
	
	@Autowired
	private PageUtils pageUtils;
	
	@Autowired
	private DictionaryTableService dictionaryTableService;
	
	@Autowired
	private MediaSettleMentService mediaSettleMentService;

    /**
     * 顾客结算
     * 员工顾客结算
     * @param mediaSettleMent
     * @param request
     * @param rep
     * @param model
     * @return
     */
	@RequiresPermissions("bussiness:settleCustom:view")
	@RequestMapping(value = "custom")
	public String customList(MediaSettleMent mediaSettleMent, HttpServletRequest request, HttpServletResponse rep,
		Model model) {
		pageUtils.setPage(request, rep);
        Article article=new Article();
        if(mediaSettleMent.getBussinnessType().equals("2")) {
            article.setUserId(UserUtils.getPrincipal().getId());
        }else{
            article.setCustomId(UserUtils.getPrincipal().getId());
        }
        mediaSettleMent.setType("1");
        mediaSettleMent.setArticle(article);
		PageInfo<MediaSettleMent> pageInfo = mediaSettleMentService.findPage(mediaSettleMent);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("settle_status")));
		model.addAttribute("MediaSettleMent", mediaSettleMent);
		model.addAttribute("formUrl","custom");
		return "bussiness/settle/bussiness_settle_list";
	}

    /**
     * 员工结算
     * 系统员工结算
     * @param mediaSettleMent
     * @param request
     * @param rep
     * @param model
     * @return
     */
	@RequiresPermissions("bussiness:settleUser:view")
	@RequestMapping(value = "user")
	public String userList(MediaSettleMent mediaSettleMent, HttpServletRequest request, HttpServletResponse rep,
		Model model) {
		pageUtils.setPage(request, rep);

        Article article=new Article();
        if(mediaSettleMent.getBussinnessType().equals("1")) {
            article.setUserId(UserUtils.getPrincipal().getId());
        }
        //1、客户结算2、员工结算、3、编辑结算
        mediaSettleMent.setType("2");
        mediaSettleMent.setArticle(article);
		PageInfo<MediaSettleMent> pageInfo = mediaSettleMentService.findPage(mediaSettleMent);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("settle_status")));
		model.addAttribute("MediaSettleMent", mediaSettleMent);
		model.addAttribute("formUrl","user");
		return "bussiness/settle/bussiness_settle_list";
	}

    /**
     * 编辑结算
     * @param mediaSettleMent
     * @param request
     * @param rep
     * @param model
     * @return
     */
	@RequiresPermissions("bussiness:settleEditor:view")
	@RequestMapping(value = "editor")
	public String editorList(MediaSettleMent mediaSettleMent, HttpServletRequest request, HttpServletResponse rep,
		Model model) {
		pageUtils.setPage(request, rep);
        Article article=new Article();
        if(mediaSettleMent.getBussinnessType().equals("1")) {
            article.setEditorId(UserUtils.getPrincipal().getId());
        }
        //1、客户结算2、员工结算、3、编辑结算
        mediaSettleMent.setType("3");
        mediaSettleMent.setArticle(article);
		PageInfo<MediaSettleMent> pageInfo = mediaSettleMentService.findPage(mediaSettleMent);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("settle_status")));
		model.addAttribute("MediaSettleMent", mediaSettleMent);
		model.addAttribute("formUrl","editor");
		return "bussiness/settle/bussiness_settle_list";
	}

	@RequiresPermissions("bussiness:settle:edit")
	@RequestMapping(value = "save")
	public String save(MediaSettleMent mediaSettleMent,RedirectAttributes redirectAttributes,String formUrl) {
		try{
			mediaSettleMentService.save(mediaSettleMent);
			addMessage(redirectAttributes, "媒体保存成功!");
		} catch (Exception e) {
			log.error("结算失败！" + e.getLocalizedMessage());
		}

		return "redirect:/settle/"+formUrl;
	}
}
