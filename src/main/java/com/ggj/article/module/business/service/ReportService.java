package com.ggj.article.module.business.service;

import com.ggj.article.module.business.bean.ArticleReport;
import com.ggj.article.module.business.dao.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:gaoguangjin
 * @date:2018/4/4
 */
@Service
@Transactional(readOnly = true)
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;
    public List<ArticleReport> findArticleReport() {
        return reportMapper.findArticleReport();
    }
}
