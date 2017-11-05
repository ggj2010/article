package com.ggj.article.module.business.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ggj.article.module.business.bean.enums.SettlementTypeEnum;
import com.ggj.article.module.business.service.*;
import com.ggj.article.module.common.GlobalConstants;
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
     * 员工客户结算	/settle/custom?bussinnessType=2		  
     * 客户结算	/settle/custom?bussinnessType=1
     * 系统客户结算	/settle/custom?bussinnessType=3
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
        mediaSettleMent.setType(SettlementTypeEnum.CUSTOM.getType()+"");
        Article article = new Article();
        if (mediaSettleMent.getArticle() == null) {
            mediaSettleMent.setArticle(article);
        }
        //员工客户结算
        if (mediaSettleMent.getBussinnessType().equals("2")) {
            mediaSettleMent.getArticle().setUserId(UserUtils.getPrincipal().getId());
            List<CustomUserInfo> customUserInfoList = customInfoService.getCustomUser(new CustomUserInfo(UserUtils.getPrincipal().getId(), true));
            //默认未结算
            if (StringUtils.isEmpty(mediaSettleMent.getStatus())) {
                mediaSettleMent.setStatus(GlobalConstants.SETTLEMENT_STATUS_NO+"");
            } else {
                if (mediaSettleMent.getStatus().equals(GlobalConstants.SETTLEMENT_STATUS_YES)) {
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
     * 员工结算	settle/user?bussinnessType=1
     *
     * 系统员工结算	/settle/user?bussinnessType=2 展示客户已经结算过的数据
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
        mediaSettleMent.setType(SettlementTypeEnum.USER.getType() + "");
        pageUtils.setPage(request, rep);
        Article article = new Article();
        if (mediaSettleMent.getArticle() == null) {
            mediaSettleMent.setArticle(article);
        }
        if (mediaSettleMent.getBussinnessType().equals("1")) {
            mediaSettleMent.getArticle().setUserId(UserUtils.getPrincipal().getId());
        }
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
     * 系统编辑结算	/settle/editor?bussinnessType=2		 	  
     * 编辑结算	/settle/editor?bussinnessType=1
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
        mediaSettleMent.setType(SettlementTypeEnum.EDITOR.getType() + "");
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
        addRedirectAttributes(mediaSettleMent,redirectAttributes);
        return "redirect:/settle/" + formUrl;
    }

    private void addRedirectAttributes(MediaSettleMent mediaSettleMent, RedirectAttributes redirectAttributes) {
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
            addRedirectAttributes(mediaSettleMent,redirectAttributes);
        } catch (Exception e) {
            log.error("结算失败！" + e.getLocalizedMessage());
        }
        return "redirect:/settle/" + mediaSettleMentVO.getFormUrl();
    }

    @RequiresPermissions("bussiness:settle:delete")
    @RequestMapping(value = "/delete")
    public String delete(MediaSettleMent mediaSettleMent, RedirectAttributes redirectAttributes) {
        try {
            if (mediaSettleMent != null && mediaSettleMent.getId() != null) {
                mediaSettleMentService.deleteMediaSettleMent(mediaSettleMent);
                addMessage(redirectAttributes, "结算删除成功!");
            }
        } catch (Exception e) {
            log.error("删除稿件失败！" + e.getLocalizedMessage());
        }
        return "redirect:/settle/user?bussinnessType=2";
    }
}
