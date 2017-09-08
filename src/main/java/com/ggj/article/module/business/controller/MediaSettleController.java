package com.ggj.article.module.business.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ggj.article.module.base.web.BaseController;
import com.ggj.article.module.business.bean.*;
import com.ggj.article.module.business.service.ArticleService;
import com.ggj.article.module.business.service.CustomInfoService;
import com.ggj.article.module.business.service.MediaSettleMentService;
import com.ggj.article.module.common.utils.PageUtils;
import com.ggj.article.module.common.utils.UserUtils;
import com.ggj.article.module.sys.entity.DictionaryTable;
import com.ggj.article.module.sys.service.DictionaryTableService;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

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
    private ArticleService articleService;

    @Autowired
    private CustomInfoService customInfoService;

    @Autowired
    private DictionaryTableService dictionaryTableService;

    @Autowired
    private MediaSettleMentService mediaSettleMentService;

    /**
     * 顾客结算
     * 员工顾客结算
     *
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
        Article article = new Article();
        if (mediaSettleMent.getArticle() == null) {
            mediaSettleMent.setArticle(article);
        }
        if (mediaSettleMent.getBussinnessType().equals("2")) {
            mediaSettleMent.getArticle().setUserId(UserUtils.getPrincipal().getId());
            List<CustomUserInfo> customUserInfoList = customInfoService.getCustomUser(new CustomUserInfo(UserUtils.getPrincipal().getId()));
            model.addAttribute("customUserInfoList", customUserInfoList);
        } else if (mediaSettleMent.getBussinnessType().equals("3")) {
            List<UserInfo> listUserInfo = articleService.getUserInfo();
            model.addAttribute("userInfoList", listUserInfo);
        } else {
            mediaSettleMent.getArticle().setCustomId(UserUtils.getPrincipal().getId());
        }
        mediaSettleMent.setType("1");
        pageUtils.setPage(request, rep);
        PageInfo<MediaSettleMent> pageInfo = mediaSettleMentService.findPage(mediaSettleMent);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("settle_status")));
        model.addAttribute("timeTypeList", dictionaryTableService.findList(new DictionaryTable("time_type")));
        model.addAttribute("MediaSettleMent", mediaSettleMent);
        model.addAttribute("formUrl", "custom");
        return "bussiness/settle/bussiness_settle_list";
    }

    /**
     * 员工结算
     * 系统员工结算
     *
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
        Article article = new Article();
        if (mediaSettleMent.getArticle() == null) {
            mediaSettleMent.setArticle(article);
        }
        if (mediaSettleMent.getBussinnessType().equals("1")) {
            mediaSettleMent.getArticle().setUserId(UserUtils.getPrincipal().getId());
        }
        // 1、客户结算2、员工结算、3、编辑结算
        mediaSettleMent.setType("2");
        PageInfo<MediaSettleMent> pageInfo = mediaSettleMentService.findPage(mediaSettleMent);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("settle_status")));
        model.addAttribute("timeTypeList", dictionaryTableService.findList(new DictionaryTable("time_type")));
        model.addAttribute("MediaSettleMent", mediaSettleMent);
        if (mediaSettleMent.getBussinnessType().equals("2")) {
            List<UserInfo> listUserInfo = articleService.getUserInfo();
            model.addAttribute("userInfoList", listUserInfo);
        }
        model.addAttribute("formUrl", "user");
        return "bussiness/settle/bussiness_settle_list";
    }

    /**
     * 编辑结算
     *
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
        Article article = new Article();
        if (mediaSettleMent.getArticle() == null) {
            mediaSettleMent.setArticle(article);
        }
        if (mediaSettleMent.getBussinnessType().equals("1")) {
            mediaSettleMent.getArticle().setEditorId(UserUtils.getPrincipal().getId());
        } else {
            if (mediaSettleMent.getArticle() != null && mediaSettleMent.getArticle().getUserId() != null) {
                mediaSettleMent.getArticle().setEditorId(mediaSettleMent.getArticle().getUserId());
                mediaSettleMent.getArticle().setUserId(null);
            }
        }
        // 1、客户结算2、员工结算、3、编辑结算
        mediaSettleMent.setType("3");
        PageInfo<MediaSettleMent> pageInfo = mediaSettleMentService.findPage(mediaSettleMent);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("settle_status")));
        model.addAttribute("timeTypeList", dictionaryTableService.findList(new DictionaryTable("time_type")));
        model.addAttribute("MediaSettleMent", mediaSettleMent);
        model.addAttribute("formUrl", "editor");
        if (mediaSettleMent.getBussinnessType().equals("2")) {
            List<UserInfo> listUserInfo = articleService.getUserInfo();
            model.addAttribute("userInfoList", listUserInfo);
        }
        return "bussiness/settle/bussiness_settle_list";
    }

    @RequiresPermissions("bussiness:settle:edit")
    @RequestMapping(value = "save")
    public String save(MediaSettleMent mediaSettleMent, RedirectAttributes redirectAttributes, String formUrl) {
        try {
            mediaSettleMentService.save(mediaSettleMent);
            addMessage(redirectAttributes, "结算成功!");
        } catch (Exception e) {
            log.error("结算失败！" + e.getLocalizedMessage());
        }
        return "redirect:/settle/" + formUrl;
    }

    @RequiresPermissions("bussiness:settle:edit")
    @RequestMapping(value = "/moresave")
    public String moresave(MediaSettleMentVO mediaSettleMentVO, RedirectAttributes redirectAttributes) {
        try {
            if (mediaSettleMentVO != null) {
                String settleArticleIds = mediaSettleMentVO.getSettleArticleIds();
                String priceStr = mediaSettleMentVO.getPriceStr();
                if (StringUtils.isNotEmpty(settleArticleIds)) {
                    settleArticleIds = settleArticleIds.substring(0, settleArticleIds.lastIndexOf(","));
                    priceStr = priceStr.substring(0, priceStr.lastIndexOf(","));
                    String[] idArray = settleArticleIds.split("\\,");
                    String[] priceArray = priceStr.split("\\,");
                    for (int i = 0; i < idArray.length; i++) {
                        MediaSettleMent mediaSettleMent = new MediaSettleMent();
                        mediaSettleMent.setType(mediaSettleMentVO.getType());
                        mediaSettleMent.setPrice(Integer.parseInt(priceArray[i]));
                        mediaSettleMent.setId(Integer.parseInt(idArray[i]));
                        mediaSettleMent.setRemark(mediaSettleMentVO.getRemark());
                        mediaSettleMentService.save(mediaSettleMent);
                    }
                }
            }
            addMessage(redirectAttributes, "结算成功!");
        } catch (Exception e) {
            log.error("结算失败！" + e.getLocalizedMessage());
        }
        return "redirect:/settle/" + mediaSettleMentVO.getFormUrl();
    }
}
