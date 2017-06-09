package com.ggj.article.module.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ggj.article.module.common.shiro.authc.Principal;
import com.ggj.article.module.common.utils.UserUtils;
import com.ggj.article.module.sys.entity.DictionaryTable;
import com.ggj.article.module.sys.entity.Role;
import com.ggj.article.module.sys.service.DictionaryTableService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ggj.article.module.base.web.BaseController;
import com.ggj.article.module.business.bean.Media;
import com.ggj.article.module.business.service.MediaService;
import com.ggj.article.module.common.utils.PageUtils;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        PageInfo<Media> pageInfo = mediaService.findPage(media);
        model.addAttribute("pageInfo", pageInfo);
        addSelectType(model);
        Principal principal = UserUtils.getPrincipal();
        model.addAttribute("principal", principal);
        model.addAttribute("media", media);
        return "bussiness/media/bussiness_media_list";
    }

    @RequiresPermissions("bussiness:media:view")
    @RequestMapping(value = "editor/list")
    public String editorMedialist(Media media, HttpServletRequest request, HttpServletResponse rep, Model model) {
        pageUtils.setPage(request, rep);
        media.setUserId(UserUtils.getPrincipal().getId());
        PageInfo<Media> pageInfo = mediaService.findEditorList(media);
        model.addAttribute("pageInfo", pageInfo);
        addSelectType(model);
        model.addAttribute("media", media);
        return "bussiness/media/bussiness_media_list";
    }

    @RequiresPermissions("bussiness:media:view")
    @RequestMapping(value = "/select")
    public String select(Media media, HttpServletRequest request, HttpServletResponse rep, Model model) {
        pageUtils.setPage(request, rep);
        PageInfo<Media> pageInfo = mediaService.findPage(media);
        model.addAttribute("pageInfo", pageInfo);
        addSelectType(model);
        model.addAttribute("media", media);
        Principal principal = UserUtils.getPrincipal();
        model.addAttribute("principal", principal);
        return "bussiness/media/bussiness_media_select";
    }

    private void addSelectType(Model model) {
        model.addAttribute("mediaTypeList", dictionaryTableService.getValueList(new DictionaryTable("media_type")));
        model.addAttribute("publishSpeedList", dictionaryTableService.getValueList(new DictionaryTable("publish_speed")));
        model.addAttribute("mediaRegionList", dictionaryTableService.getValueList(new DictionaryTable("media_region")));
        model.addAttribute("collectionTypeList", dictionaryTableService.getValueList(new DictionaryTable("collection_type")));
        model.addAttribute("baiduSeoList", dictionaryTableService.getValueList(new DictionaryTable("baidu_seo")));
    }

    @RequiresPermissions("bussiness:media:view")
    @RequestMapping(value = "form")
    public String form(Media media, Model model) {
        model.addAttribute("media", media);
        addSelectType(model);
        return "bussiness/media/bussiness_media_form";
    }

    @RequiresPermissions("bussiness:media:edit")
    @RequestMapping(value = "save")
    public String save(@Valid Media media, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "bussiness/media/bussiness_media_form";
        } else {
            mediaService.save(media);
            addMessage(redirectAttributes, "媒体保存成功!");
        }
        return "redirect:/media/";
    }

    @RequiresPermissions("bussiness:media:edit")
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
            map.put("name", "【标题】："+media.getName()+"【成本价】："+media.getCostPrice());
            map.put("open", true);
            mapList.add(map);
        }
        return mapList;
    }
}
