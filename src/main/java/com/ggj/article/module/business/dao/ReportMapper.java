package com.ggj.article.module.business.dao;

import com.ggj.article.module.business.bean.ArticleReport;

import java.util.List;

/**
 * @author:gaoguangjin
 * @date:2018/4/4
 */
public interface ReportMapper {
    List<ArticleReport> findArticleReport();
}
