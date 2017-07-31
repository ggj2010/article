package com.ggj.article.module.business.dao;

import com.ggj.article.module.business.bean.Article;
import com.ggj.article.module.common.crud.CrudDao;

import java.util.List;

public interface ArticleMapper extends CrudDao<Article> {
    void saveBatchArticle(List<Article> articleList);
}