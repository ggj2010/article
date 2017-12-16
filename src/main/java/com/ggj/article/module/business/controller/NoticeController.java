package com.ggj.article.module.business.controller;

import com.ggj.article.module.base.web.BaseController;
import com.ggj.article.module.business.bean.Notice;
import com.ggj.article.module.business.service.NoticeService;
import com.ggj.article.module.common.utils.PageUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author:gaoguangjin
 * @date:2017/11/5
 */
@Controller
@RequestMapping("/notice")
@Slf4j
public class NoticeController extends BaseController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private PageUtils pageUtils;

    @ModelAttribute
    public Notice get(@RequestParam(required = false) Integer id) {
        Notice notice = null;
        if (id != null) {
            notice = noticeService.get(id);
        }
        if (notice == null) {
            notice = new Notice();
        }
        return notice;
    }

    @RequestMapping("")
    public String index(Model model) {
        List<Notice> list = noticeService.findList(new Notice());
        model.addAttribute("list", list);
        return "notice";
    }

    @RequiresPermissions("bussiness:notice:view")
    @RequestMapping(value = "list")
    public String list(Model model, HttpServletRequest request, HttpServletResponse rep) {
        pageUtils.setPage(request, rep);
        PageInfo<Notice> noticePageInfo = noticeService.findPage(new Notice());
        model.addAttribute("pageInfo", noticePageInfo);
        return "bussiness/notice/bussiness_notice_list";
    }

    @RequiresPermissions("bussiness:notice:edit")
    @RequestMapping(value = "form")
    public String edit(Notice notice, Model model) {
        model.addAttribute("notice", notice);
        return "bussiness/notice/bussiness_notice_form";
    }

    @RequiresPermissions("bussiness:notice:edit")
    @RequestMapping(value = "save")
    public String save(Notice notice, RedirectAttributes redirectAttributes) {
        noticeService.save(notice);
        addMessage(redirectAttributes, "通告保存成功!");
        return "redirect:/notice/list/";
    }

    @RequiresPermissions("bussiness:notice:edit")
    @RequestMapping(value = "/delete")
    public String delete(Notice notice, RedirectAttributes redirectAttributes) {
        noticeService.delete(notice);
        addMessage(redirectAttributes, "通告删除成功!");
        return "redirect:/notice/list/";
    }
}
