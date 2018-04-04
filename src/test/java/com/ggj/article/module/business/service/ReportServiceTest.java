package com.ggj.article.module.business.service;

import base.BaseTest;
import com.ggj.article.module.business.bean.ArticleReport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author:gaoguangjin
 * @date:2018/4/4
 */
public class ReportServiceTest extends BaseTest {
    @Autowired
    private ReportService reportService;
    @Test
    public void findArticleReport() throws Exception {
        List<ArticleReport> dd = reportService.findArticleReport();
        System.out.println("");
    }

}