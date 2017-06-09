package com.ggj.article.module.business.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aliyun.oss.model.PutObjectResult;
import com.ggj.article.module.base.web.BaseController;
import com.ggj.article.module.business.bean.Article;
import com.ggj.article.module.business.bean.ArticleVO;
import com.ggj.article.module.business.bean.CustomUserInfo;
import com.ggj.article.module.business.service.ArticleService;
import com.ggj.article.module.business.service.CustomInfoService;
import com.ggj.article.module.common.shiro.authc.Principal;
import com.ggj.article.module.common.utils.AliyunUtil;
import com.ggj.article.module.common.utils.IdGen;
import com.ggj.article.module.common.utils.PageUtils;
import com.ggj.article.module.common.utils.UserUtils;
import com.ggj.article.module.sys.entity.DictionaryTable;
import com.ggj.article.module.sys.service.DictionaryTableService;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 文章列表
 *
 * @author:gaoguangjin
 * @date 2017/4/25 22:34
 */
@Controller
@RequestMapping("article")
@Slf4j
public class ArticleController extends BaseController {
	
	@Autowired
	AliyunUtil aliyunUtil;
	
	@Autowired
	private PageUtils pageUtils;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CustomInfoService customInfoService;
	
	@Autowired
	private DictionaryTableService dictionaryTableService;
	
	@ModelAttribute
	public Article get(@RequestParam(required = false) Integer id) {
		Article article = null;
		if (id != null) {
			article = articleService.get(id);
		}
		if (article == null) {
			article = new Article();
		}
		return article;
	}
	
	@RequiresPermissions("bussiness:article:view")
	@RequestMapping(value = "")
	public String list(Article article, HttpServletRequest request, HttpServletResponse rep, Model model) {
		pageUtils.setPage(request, rep);
		Principal principal = UserUtils.getPrincipal();
		PageInfo<Article> pageInfo = articleService.findPage(article);
		model.addAttribute("pageInfo", pageInfo);
		String typeParam=article.getTypeParam();
		if(StringUtils.isNotEmpty(typeParam)){
			if(typeParam.equals("1")){
				article.setUserId(principal.getId());
			}else if(typeParam.equals("2")){
				article.setEditorId(principal.getId());
			}else if(typeParam.equals("3")){
				article.setCustomId(principal.getId());
			}
		}
		List<CustomUserInfo> customUserInfoList = customInfoService.getCustomUser(principal.getId());
		model.addAttribute("customUserInfoList", customUserInfoList);
		model.addAttribute("article", article);
		model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("article_status")));
		return "bussiness/article/bussiness_article_list";
	}

	@RequiresPermissions("bussiness:article:view")
	@RequestMapping(value = "form")
	public String form(Article article, Model model) {
		model.addAttribute("article", article);
		return "bussiness/article/bussiness_article_form";
	}
	
	@RequiresPermissions("bussiness:article:add")
	@RequestMapping(value = "add")
	public String add(Article article, Model model) {
		return "bussiness/article/bussiness_article_add";
	}
	
	@RequiresPermissions("bussiness:article:add")
	@RequestMapping(value = "addsteptwo")
	public String addStepTwo(Article article, Model model) {
		Principal principal = UserUtils.getPrincipal();
		List<CustomUserInfo> customUserInfoList = customInfoService.getCustomUser(principal.getId());
		model.addAttribute("customUserInfoList", customUserInfoList);
		model.addAttribute("article", article);
		model.addAttribute("principal", principal);
		return "bussiness/article/bussiness_article_addsteptwo";
	}
	
	@RequiresPermissions("bussiness:article:edit")
	@RequestMapping(value = "save")
	public String save(ArticleVO articleVO, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, String> fileUrlMap = new HashMap<String, String>();
		MultipartHttpServletRequest murequest = (MultipartHttpServletRequest)request;
		if (murequest != null) {
			Map<String, MultipartFile> fileMap = murequest.getFileMap();
			if (fileMap != null && fileMap.size() > 0) {
				for(Iterator<String> iterator = fileMap.keySet().iterator(); iterator.hasNext();) {
					String fileName = iterator.next();
					MultipartFile multipartFile = fileMap.get(fileName);
					if (StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
						String key = "file/" + IdGen.uuid() + multipartFile.getOriginalFilename();
						try {
							PutObjectResult putObjectResult = aliyunUtil.putObject(null, key,
									multipartFile.getInputStream());
							fileUrlMap.put(fileName,
									aliyunUtil.getEndpoint() + "/" + aliyunUtil.getBucket() + "/" + key);
						} catch (Exception e) {
							log.error("上传失败");
						}
					}
				}
			}
		}
		articleService.save(fileUrlMap, articleVO);
		addMessage(redirectAttributes, "稿件保存成功!");
		return "redirect:/article/addsteptwo";
	}
	
	@RequiresPermissions("bussiness:article:delete")
	@RequestMapping(value = "/delete")
	public String delete(Article article, RedirectAttributes redirectAttributes) {
		try {
			if (article != null && article.getId() != null) {
				articleService.delete(article);
				addMessage(redirectAttributes, "稿件删除成功!");
			}
		} catch (Exception e) {
			log.error("删除稿件失败！" + e.getLocalizedMessage());
		}
		return "redirect:/article/";
	}
	
	@RequiresPermissions("bussiness:article:verify")
	@RequestMapping(value = "/verifying")
	public String verifying(Article article, RedirectAttributes redirectAttributes) {
		try {
			if (article != null && article.getId() != null) {
				articleService.verifying(article);
				addMessage(redirectAttributes, "审核成功!");
			}
		} catch (Exception e) {
			log.error("审核稿件失败！" + e.getLocalizedMessage());
		}
		return "redirect:/article/";
	}
	@RequiresPermissions("bussiness:article:verify")
	@RequestMapping(value = "/back")
	public String back(Article article, RedirectAttributes redirectAttributes) {
		try {
			if (article != null && article.getId() != null) {
				articleService.back(article);
				addMessage(redirectAttributes, "回退成功!");
			}
		} catch (Exception e) {
			log.error("回退失败！" + e.getLocalizedMessage());
		}
		return "redirect:/article/";
	}

	@RequiresPermissions("bussiness:article:verify")
	@RequestMapping(value = "/verifysave")
	public String verifysave(Article article, RedirectAttributes redirectAttributes) {
		try {
			if (article != null && article.getId() != null) {
				articleService.verify(article);
				addMessage(redirectAttributes, "审核成功!");
			}
		} catch (Exception e) {
			log.error("审核稿件失败！" + e.getLocalizedMessage());
		}
		return "redirect:/article/";
	}
}
