package com.ggj.article.module.business.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ggj.article.module.business.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ggj.article.module.base.web.BaseController;
import com.ggj.article.module.business.bean.*;
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

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

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
                             Model model) throws InterruptedException, ExecutionException {
        Article article = new Article();
        if (mediaSettleMent.getArticle() == null) {
            mediaSettleMent.setArticle(article);
        }
        if (mediaSettleMent.getBussinnessType().equals("2")) {
            mediaSettleMent.getArticle().setUserId(UserUtils.getPrincipal().getId());
            List<CustomUserInfo> customUserInfoList = customInfoService.getCustomUser(new CustomUserInfo(UserUtils.getPrincipal().getId(), true));
            //默认未结算
            if (StringUtils.isEmpty(mediaSettleMent.getStatus())) {
                mediaSettleMent.setStatus("0");
            } else {
                if (mediaSettleMent.getStatus().equals("1")) {
                    customUserInfoList = customInfoService.getCustomUser(new CustomUserInfo(UserUtils.getPrincipal().getId()));

                }
            }
            model.addAttribute("customUserInfoList", customUserInfoList);
        } else if (mediaSettleMent.getBussinnessType().equals("3")) {
            List<UserInfo> listUserInfo = articleService.getUserInfo();
            model.addAttribute("userInfoList", listUserInfo);
        } else {
            mediaSettleMent.getArticle().setCustomId(UserUtils.getPrincipal().getId());
        }
        mediaSettleMent.setType("1");
        pageUtils.setPage(request, rep);
        List<Future<PageInfo<MediaSettleMent>>> futureList = getFutureTaksList(request, rep, model, mediaSettleMent);
        for (Future<PageInfo<MediaSettleMent>> pageInfoFuture : futureList) {
            PageInfo<MediaSettleMent> pageInfo = pageInfoFuture.get();
            if (pageInfo != null) {
                model.addAttribute("pageInfo", pageInfo);
                model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("settle_status")));
                model.addAttribute("timeTypeList", dictionaryTableService.findList(new DictionaryTable("time_type")));
                model.addAttribute("MediaSettleMent", mediaSettleMent);
                model.addAttribute("formUrl", "custom");
            }
        }
        return "bussiness/settle/bussiness_settle_list";
    }

    private List<Future<PageInfo<MediaSettleMent>>> getFutureTaksList(HttpServletRequest request, HttpServletResponse rep, Model model, MediaSettleMent mediaSettleMent) throws InterruptedException {
        List<Callable<PageInfo<MediaSettleMent>>> list = new ArrayList<Callable<PageInfo<MediaSettleMent>>>();
        list.add(new MediaSettleMentTask(mediaSettleMentService, mediaSettleMent, request, rep, pageUtils));
        list.add(new MediaSettleMentStatisticsTask(mediaSettleMentService, mediaSettleMent, model));
        return threadPoolTaskExecutor.getThreadPoolExecutor().invokeAll(list);
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
                           Model model) throws ExecutionException, InterruptedException {
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
        List<Future<PageInfo<MediaSettleMent>>> futureList = getFutureTaksList(request, rep, model, mediaSettleMent);
        for (Future<PageInfo<MediaSettleMent>> pageInfoFuture : futureList) {
            PageInfo<MediaSettleMent> pageInfo = pageInfoFuture.get();
            if (pageInfo != null) {
                model.addAttribute("pageInfo", pageInfo);
                model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("settle_status")));
                model.addAttribute("timeTypeList", dictionaryTableService.findList(new DictionaryTable("time_type")));
                model.addAttribute("MediaSettleMent", mediaSettleMent);
                if (mediaSettleMent.getBussinnessType().equals("2")) {
                    List<UserInfo> listUserInfo = articleService.getUserInfo();
                    model.addAttribute("userInfoList", listUserInfo);
                }
                model.addAttribute("formUrl", "user");
            }
        }
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
                             Model model) throws ExecutionException, InterruptedException {
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
        List<Future<PageInfo<MediaSettleMent>>> futureList = getFutureTaksList(request, rep, model, mediaSettleMent);
        for (Future<PageInfo<MediaSettleMent>> pageInfoFuture : futureList) {
            PageInfo<MediaSettleMent> pageInfo = pageInfoFuture.get();
            if (pageInfo != null) {
                model.addAttribute("pageInfo", pageInfo);
                model.addAttribute("articleStatusList", dictionaryTableService.findList(new DictionaryTable("settle_status")));
                model.addAttribute("timeTypeList", dictionaryTableService.findList(new DictionaryTable("time_type")));
                model.addAttribute("MediaSettleMent", mediaSettleMent);
                model.addAttribute("formUrl", "editor");
                if (mediaSettleMent.getBussinnessType().equals("2")) {
                    List<UserInfo> listUserInfo = articleService.getUserInfo();
                    model.addAttribute("userInfoList", listUserInfo);
                }
            }
        }
        return "bussiness/settle/bussiness_settle_list";
    }

    @RequiresPermissions("bussiness:settle:edit")
    @RequestMapping(value = "save")
    public String save(MediaSettleMent mediaSettleMent, HttpServletRequest request, RedirectAttributes redirectAttributes, String formUrl) {
        try {
            mediaSettleMentService.save(mediaSettleMent);
            addMessage(redirectAttributes, "结算成功!");
        } catch (Exception e) {
            log.error("结算失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("pageNum", request.getParameter("pageNum"));
        redirectAttributes.addAttribute("pageSize", request.getParameter("pageSize"));

        if (mediaSettleMent != null) {
            if (mediaSettleMent.getArticle() != null) {
                Article article = mediaSettleMent.getArticle();
                redirectAttributes.addAttribute("article.customId", article.getCustomId());
                redirectAttributes.addAttribute("article.beginTimeStr", article.getBeginTimeStr());
                redirectAttributes.addAttribute("article.endTimeStr", article.getEndTimeStr());
                redirectAttributes.addAttribute("article.timeType", article.getTimeType());
                redirectAttributes.addAttribute("status", "0");
            }
        }

        return "redirect:/settle/" + formUrl;
    }

    @RequiresPermissions("bussiness:settle:edit")
    @RequestMapping(value = "/moresave")
    public String moresave(MediaSettleMentVO mediaSettleMentVO, HttpServletRequest request, RedirectAttributes redirectAttributes, MediaSettleMent mediaSettleMent) {
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
                        MediaSettleMent msm = new MediaSettleMent();
                        msm.setType(mediaSettleMentVO.getType());
                        msm.setPrice(Integer.parseInt(priceArray[i]));
                        msm.setId(Integer.parseInt(idArray[i]));
                        msm.setRemark(mediaSettleMentVO.getRemark());
                        mediaSettleMentService.save(msm);
                    }
                }
            }
            addMessage(redirectAttributes, "结算成功!");
        } catch (Exception e) {
            log.error("结算失败！" + e.getLocalizedMessage());
        }
        if (mediaSettleMent != null) {
            if (mediaSettleMent.getArticle() != null) {
                Article article = mediaSettleMent.getArticle();
                redirectAttributes.addAttribute("article.customId", article.getCustomId());
                redirectAttributes.addAttribute("article.beginTimeStr", article.getBeginTimeStr());
                redirectAttributes.addAttribute("article.endTimeStr", article.getEndTimeStr());
                redirectAttributes.addAttribute("article.timeType", article.getTimeType());
                redirectAttributes.addAttribute("status", "0");
            }
        }
        return "redirect:/settle/" + mediaSettleMentVO.getFormUrl();
    }
}
