package com.ggj.article.module.business.controller;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ggj.article.module.business.bean.enums.LoginRoleEnum;
import com.ggj.article.module.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aliyun.oss.model.PutObjectResult;
import com.ggj.article.module.base.web.BaseController;
import com.ggj.article.module.business.bean.*;
import com.ggj.article.module.business.service.ArticleService;
import com.ggj.article.module.business.service.CustomInfoService;
import com.ggj.article.module.business.service.MediaService;
import com.ggj.article.module.common.shiro.authc.Principal;
import com.ggj.article.module.common.utils.*;
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

    @Autowired
    private MediaService mediaService;

    private Lock lock = new ReentrantLock();

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
        Principal principal = UserUtils.getPrincipal();
        setArticleParam(article, principal, model);
        pageUtils.setPage(request, rep);
        PageInfo<Article> pageInfo = articleService.findPage(article);
        model.addAttribute("pageInfo", pageInfo);
        List<CustomUserInfo> customUserInfoList = customInfoService.getCustomUser(new CustomUserInfo(UserUtils.getPrincipal().getId()));
        model.addAttribute("customUserInfoList", customUserInfoList);
        model.addAttribute("article", article);
        model.addAttribute("userId", principal.getId());
        model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("article_status")));
        model.addAttribute("timeTypeList", dictionaryTableService.findList(new DictionaryTable("time_type")));
        return getReturnUrl(article, "bussiness/article/bussiness_article_list");
    }

    private String getReturnUrl(Article article, String defaultReturnUrl) {
        String url = "bussiness/article/$_article_list";
        if (StringUtils.isNotEmpty(article.getTypeParam()) && article.getTypeParam().equals(LoginRoleEnum.CUSTOM.getType() + "")) {
            return url.replace("$", LoginRoleEnum.CUSTOM.getName());
        }
        return defaultReturnUrl;
    }

    @RequiresPermissions("bussiness:article:view")
    @RequestMapping(value = "export")
    public String exportArticle(Article article, HttpServletRequest request, RedirectAttributes redirectAttributes,
                                HttpServletResponse rep, Model model) {
        try {
            Principal principal = UserUtils.getPrincipal();
            setArticleParam(article, principal, model);
            List<Article> listArticle = articleService.findList(article);
            ExelUtil.exportArticleExel(listArticle, rep, article.getTypeParam());
            addMessage(redirectAttributes, "媒体导出成功!");
        } catch (Exception e) {
            log.error("导入表格异常", e);
            addMessage(redirectAttributes, "导出表格异常!");
        }
        return null;
    }

    private void setArticleParam(Article article, Principal principal, Model model) {
        if (StringUtils.isEmpty(article.getTimeType())) {
            if (StringUtils.isNotEmpty(article.getBeginTimeStr()) || StringUtils.isNotEmpty(article.getEndTimeStr()))
                article.setTimeType("1");
        }
        String typeParam = article.getTypeParam();
        if (StringUtils.isNotEmpty(typeParam)) {
            // 员工为1
            if (typeParam.equals(LoginRoleEnum.USER.getType() + "")) {
                article.setUserId(principal.getId());
                // 编辑为2
            } else if (typeParam.equals(LoginRoleEnum.EDITOR.getType() + "")) {
                // 待审核的所有编辑都可以看到
                if (article.getStatus() == null
                        || (article.getStatus() != null && (article.getStatus() == 0 || article.getStatus() == 1))) {
                    // 特殊处理
                    article.setStatus(5);
                    Media media = new Media();
                    media.setUserId(principal.getId());
                    List<Media> mediaList = mediaService.findAllEditorList(media);
                    List<String> idList = new ArrayList<String>();
                    if (mediaList != null && mediaList.size() > 0) {
                        for (Media m : mediaList) {
                            idList.add(m.getId() + "");
                        }
                    }
                    if (idList.size() > 0) {
                        article.setMediaIdStr(idList);
                    }
                    // 已经审核的只有当前编辑的编辑才可以看到
                } else {
                    article.setEditorId(principal.getId());
                }
                List<UserInfo> listUserInfo = articleService.getUserInfo();
                model.addAttribute("userInfoList", listUserInfo);
            } else if (typeParam.equals(LoginRoleEnum.CUSTOM.getType() + "")) {
                // 顾客为三
                article.setCustomId(principal.getId());
            }
        } else {
            List<UserInfo> listUserInfo = articleService.getUserInfo();
            model.addAttribute("userInfoList", listUserInfo);
        }
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
        List<CustomUserInfo> customUserInfoList = customInfoService.getCustomUser(new CustomUserInfo(UserUtils.getPrincipal().getId()));
        model.addAttribute("customUserInfoList", customUserInfoList);
        model.addAttribute("article", article);
        model.addAttribute("principal", principal);
        return "bussiness/article/bussiness_article_addsteptwo";
    }

    @RequiresPermissions("bussiness:article:edit")
    @RequestMapping(value = "save")
    public String save(ArticleVO articleVO, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Map<String, String> fileUrlMap = new HashMap<String, String>();
        MultipartHttpServletRequest murequest = (MultipartHttpServletRequest) request;
        if (murequest != null) {
            Map<String, MultipartFile> fileMap = murequest.getFileMap();
            if (fileMap != null && fileMap.size() > 0) {
                for (Iterator<String> iterator = fileMap.keySet().iterator(); iterator.hasNext(); ) {
                    String fileName = iterator.next();
                    MultipartFile multipartFile = fileMap.get(fileName);
                    if (StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
                        String key = "file/" + IdGen.uuid() + multipartFile.getOriginalFilename();
                        try {
                            long beginTime = System.currentTimeMillis();
                            PutObjectResult putObjectResult = aliyunUtil.putObject(null, key,
                                    multipartFile.getInputStream());
                            long endTime = System.currentTimeMillis();
                            log.info("上传文件size{},耗时{}", multipartFile.getSize(), (endTime - beginTime) + "ms");
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
                article.setStatus(4);
                articleService.delete(article);
                addMessage(redirectAttributes, "稿件删除成功!");
            }
        } catch (Exception e) {
            log.error("删除稿件失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("typeParam", article.getTypeParam());
        return "redirect:/article/";
    }

    @RequiresPermissions("bussiness:article:verify")
    @RequestMapping(value = "/verifying")
    public String verifying(Article article, RedirectAttributes redirectAttributes) {
        try {
            if (article != null && article.getId() != null) {
                article.setEditorId(UserUtils.getPrincipal().getId());
                articleService.verifying(article);
                addMessage(redirectAttributes, "审核成功!");
            }
        } catch (BizException e) {
            addMessage(redirectAttributes, e.getMessage());
        } catch (Exception e) {
            log.error("审核稿件失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("typeParam", article.getTypeParam());
        return "redirect:/article/";
    }

    @RequiresPermissions("bussiness:article:view")
    @RequestMapping(value = "/review")
    public String review(Article article, RedirectAttributes redirectAttributes) {
        try {
            if (article != null && article.getId() != null) {
                articleService.review(article);
                addMessage(redirectAttributes, "提取成功!");
            }
        } catch (Exception e) {
            log.error("提取失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("typeParam", article.getTypeParam());
        return "redirect:/article/";
    }

    @RequiresPermissions("bussiness:article:verify")
    @RequestMapping(value = "/updateurl")
    public String updateurl(Article article, RedirectAttributes redirectAttributes) {
        try {
            if (article != null && article.getId() != null) {
                articleService.update(article);
                addMessage(redirectAttributes, "审核成功!");
            }
        } catch (Exception e) {
            log.error("审核稿件失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("typeParam", article.getTypeParam());
        return "redirect:/article/";
    }

    @RequiresPermissions("bussiness:article:verify")
    @RequestMapping(value = "/back")
    public String back(Article article, RedirectAttributes redirectAttributes) {
        try {
            if (article != null && article.getId() != null) {
                article.setEditorId(UserUtils.getPrincipal().getId());
                articleService.back(article);
                addMessage(redirectAttributes, "回退成功!");
            }
        } catch (Exception e) {
            log.error("回退失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("typeParam", article.getTypeParam());
        return "redirect:/article/";
    }

    @RequiresPermissions("bussiness:article:verify")
    @RequestMapping(value = "/verifysave")
    public String verifysave(Article article, RedirectAttributes redirectAttributes) {
        String typeParam = "2";
        try {
            typeParam = article.getTypeParam();
            //防止重复提交
            if (lock.tryLock()) {
                Article newArticle = articleService.get(article.getId());
                if (article != null && article.getId() != null) {
                    article.setStatus(newArticle.getStatus());
                    articleService.verify(article);
                    addMessage(redirectAttributes, "审核成功!");
                }
            }
        } catch (BizException e) {
            addMessage(redirectAttributes, e.getMessage());
        } catch (Exception e) {
            log.error("审核稿件失败！" + e.getLocalizedMessage());
        } finally {
            lock.unlock();
        }
        redirectAttributes.addAttribute("typeParam", typeParam);
        return "redirect:/article/";
    }

    @ResponseBody
    @RequestMapping("/ajax/getCustomInfoList")
    public List<CustomUserInfo> getCustomInfoList(String name) {
        return customInfoService.getCustomUser(new CustomUserInfo(UserUtils.getPrincipal().getId(), name));
    }
}
