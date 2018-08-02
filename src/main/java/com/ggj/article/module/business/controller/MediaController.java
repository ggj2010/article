package com.ggj.article.module.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ggj.article.module.business.bean.MediaCollect;
import com.ggj.article.module.business.service.MediaCollectService;
import com.ggj.article.module.common.shiro.authc.Principal;
import com.ggj.article.module.common.utils.ExelUtil;
import com.ggj.article.module.common.utils.UserUtils;
import com.ggj.article.module.sys.entity.DictionaryTable;
import com.ggj.article.module.sys.entity.Role;
import com.ggj.article.module.sys.service.DictionaryTableService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ggj.article.module.base.web.BaseController;
import com.ggj.article.module.business.bean.Media;
import com.ggj.article.module.business.service.MediaService;
import com.ggj.article.module.common.utils.PageUtils;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 媒体列表
 *
 * @author:gaoguangjin
 * @date 2017/4/25 22:34
 */
@Controller
@RequestMapping("media")
@Slf4j
public class MediaController extends BaseController {

    @Autowired
    private PageUtils pageUtils;

    @Autowired
    private MediaService mediaService;
    @Autowired
    private MediaCollectService mediaCollectService;

    @Autowired
    private DictionaryTableService dictionaryTableService;

    @ModelAttribute
    public Media get(@RequestParam(required = false) Integer id) {
        Media media = null;
        if (id != null) {
            media = mediaService.get(id);
        }
        if (media == null) {
            media = new Media();
        }
        return media;
    }

    @RequiresPermissions("bussiness:media:view")
    @RequestMapping(value = "")
    public String list(Media media, HttpServletRequest request, HttpServletResponse rep, Model model) {
        pageUtils.setPage(request, rep);
        String typeParam = media.getTypeParam();
        PageInfo<Media> pageInfo = null;
        Principal principal = UserUtils.getPrincipal();
        model.addAttribute("principal", principal);
        if (StringUtils.isNotEmpty(typeParam)) {
            if (typeParam.equals("1")) {
                pageInfo = mediaService.findPage(media);
            } else if (typeParam.equals("2")) {
                media.setUserId(principal.getId());
                pageInfo = mediaService.findPage(media);
            } else if (typeParam.equals("3")) {
                media.setFlag("1");
                model.addAttribute("isRecycle", true);
                pageInfo = mediaService.findPage(media);
            } else if (typeParam.equals("4")) {
                media.setUserId(principal.getId());
                pageInfo = mediaService.findPage(media);
            }
        }
        model.addAttribute("pageInfo", pageInfo);
        addSelectType(model);
        model.addAttribute("media", media);
        return "bussiness/media/bussiness_media_list";
    }

    @RequiresPermissions("bussiness:media:import")
    @RequestMapping(value = "import")
    public String importMedia(Media media, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse rep, Model model, @RequestParam("meidaExelFile") MultipartFile meidaExelFile) {
        if (meidaExelFile != null) {
            try {
                mediaService.saveImport(meidaExelFile.getInputStream());
                addMessage(redirectAttributes, "媒体导入保存成功!");
            } catch (Exception e) {
                log.error("导入表格异常", e);
                addMessage(redirectAttributes, "导入表格异常!");
            }
        }
        pageUtils.setPage(request, rep);
        PageInfo<Media> pageInfo = mediaService.findEditorList(media);
        model.addAttribute("pageInfo", pageInfo);
        addSelectType(model);
        model.addAttribute("media", media);
        return "bussiness/media/bussiness_media_list";
    }

    @RequiresPermissions("bussiness:media:export")
    @RequestMapping(value = "export")
    public String exportMedia(Media media, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse rep, Model model) {
        try {
            List<Media> listMedia = mediaService.findList(media);
            ExelUtil.exportExel(listMedia, rep);
            addMessage(redirectAttributes, "媒体导入保存成功!");
        } catch (Exception e) {
            log.error("导入表格异常", e);
            addMessage(redirectAttributes, "导入表格异常!");
        }
        return null;
    }

    @RequiresPermissions("bussiness:media:view")
    @RequestMapping(value = "/select")
    public String select(Media media, HttpServletRequest request, HttpServletResponse rep, Model model) {
        pageUtils.setPage(request, rep);
        media.setStatus(2);
        PageInfo<Media> pageInfo = mediaService.findPage(media);
        model.addAttribute("pageInfo", pageInfo);
        addSelectType(model);
        model.addAttribute("media", media);
        Principal principal = UserUtils.getPrincipal();
        model.addAttribute("principal", principal);
        return "bussiness/media/bussiness_media_select";
    }

    private void addSelectType(Model model) {
        model.addAttribute("mediaChannelList", dictionaryTableService.getValueList(new DictionaryTable("media_channel")));
        model.addAttribute("mediaTypeList", dictionaryTableService.getValueList(new DictionaryTable("media_type")));
        model.addAttribute("publishSpeedList", dictionaryTableService.getValueList(new DictionaryTable("publish_speed")));
        model.addAttribute("mediaRegionList", dictionaryTableService.getValueList(new DictionaryTable("media_region")));
        model.addAttribute("collectionTypeList", dictionaryTableService.getValueList(new DictionaryTable("collection_type")));
        model.addAttribute("baiduSeoList", dictionaryTableService.getValueList(new DictionaryTable("baidu_seo")));
        model.addAttribute("readsList", dictionaryTableService.getValueList(new DictionaryTable("reads_type")));
        model.addAttribute("fansList", dictionaryTableService.getValueList(new DictionaryTable("fans_type")));
    }

    @RequiresPermissions("bussiness:media:form")
    @RequestMapping(value = "form")
    public String form(Media media, Model model) {
        model.addAttribute("media", media);
        addSelectType(model);
        return "bussiness/media/bussiness_media_form";
    }

    @RequiresPermissions("bussiness:media:edit")
    @RequestMapping(value = "edit")
    public String edit(Media media, Model model) {
        model.addAttribute("media", media);
        addSelectType(model);
        return "bussiness/media/bussiness_media_form";
    }

    @RequiresPermissions("bussiness:media:edit")
    @RequestMapping(value = "restore")
    public String restore(Media media, RedirectAttributes redirectAttributes) {
        media.setFlag("0");
        mediaService.save(media);
        addMessage(redirectAttributes, "媒体还原成功!");
        redirectAttributes.addAttribute("typeParam", media.getTypeParam());
        return "redirect:/media/";
    }

    @RequiresPermissions("bussiness:media:edit")
    @RequestMapping(value = "save")
    public String save(@Valid Media media, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "bussiness/media/bussiness_media_form";
        } else {
            Principal principal = UserUtils.getPrincipal();
            media.setUserId(principal.getId());
            mediaService.save(media);
            addMessage(redirectAttributes, "媒体保存成功!");
        }
        redirectAttributes.addAttribute("typeParam", media.getTypeParam());
        return "redirect:/media/";
    }

    @RequiresPermissions("bussiness:media:delete")
    @RequestMapping(value = "/delete")
    public String delete(Media media, RedirectAttributes redirectAttributes) {
        try {
            if (media != null && media.getId() != null) {
                mediaService.delete(media);
                addMessage(redirectAttributes, "媒体删除成功!");
            }
        } catch (Exception e) {
            log.error("删除媒体失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("typeParam", media.getTypeParam());
        return "redirect:/media/";
    }

    @RequiresPermissions("bussiness:media:approve")
    @RequestMapping(value = "/approve")
    public String approve(Media media, RedirectAttributes redirectAttributes) {
        try {
            if (media != null && media.getId() != null) {
                mediaService.approve(media.getId());
                addMessage(redirectAttributes, "媒体审核成功!");
            }
        } catch (Exception e) {
            log.error("删除媒体失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("typeParam", media.getTypeParam());
        return "redirect:/media/";
    }

    @RequiresPermissions("bussiness:media:settop")
    @RequestMapping(value = "/settop")
    public String setTop(Media media, RedirectAttributes redirectAttributes) {
        try {
            mediaService.setTop(media);
            addMessage(redirectAttributes, media.getTopType() == 1 ? "取消置顶成功!" : "置顶成功!");
        } catch (Exception e) {
            log.error("置顶失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("typeParam", media.getTypeParam());
        redirectAttributes.addAttribute("mediaChannel", media.getMediaChannel());
        return "redirect:/media/";
    }

    @RequiresPermissions("bussiness:media:view")
    @RequestMapping(value = "/collect")
    public String collect(Media media, RedirectAttributes redirectAttributes) {
        try {
            MediaCollect mediaCollect = new MediaCollect();
            mediaCollect.setMediaId(media.getId());
            mediaCollect.setUserId(UserUtils.getPrincipal().getId());
            mediaCollectService.save(mediaCollect);
            addMessage(redirectAttributes, "收藏成功!");
        } catch (Exception e) {
            log.error("收藏失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("typeParam", media.getTypeParam());
        return "redirect:/media/";
    }
    @RequiresPermissions("bussiness:media:view")
    @RequestMapping(value = "/uncollect")
    public String unCollect(Media media, RedirectAttributes redirectAttributes) {
        try {
            MediaCollect mediaCollect = new MediaCollect();
            mediaCollect.setMediaId(media.getId());
            mediaCollect.setUserId(UserUtils.getPrincipal().getId());
            mediaCollectService.delete(mediaCollect);
            addMessage(redirectAttributes, "取消收藏成功!");
        } catch (Exception e) {
            log.error("取消收藏失败！" + e.getLocalizedMessage());
        }
        redirectAttributes.addAttribute("typeParam", media.getTypeParam());
        return "redirect:/media/";
    }


    @ResponseBody
    @RequiresPermissions("user")
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> getMediaTreeData(Media mediaParmas) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        List<Media> list = mediaService.findList(mediaParmas);
        for (Media media : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", media.getId());
            map.put("pId", "");
            map.put("name", "【标题】：" + media.getName() + "【成本价】：" + media.getCostPrice());
            map.put("open", true);
            mapList.add(map);
        }
        return mapList;
    }
}
