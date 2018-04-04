package com.ggj.article.module.business.controller;

import com.ggj.article.module.business.bean.ArticleReport;
import com.ggj.article.module.business.bean.Notice;
import com.ggj.article.module.business.service.NoticeService;
import com.ggj.article.module.business.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author:gaoguangjin
 * @date:2018/4/4
 */
@Controller
@RequestMapping("/report")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping("article")
    public String articleReport(Model model) {
        List<ArticleReport> list = reportService.findArticleReport();
        model.addAttribute("list", list);
        return "bussiness/article/bussiness_report";
    }



}
