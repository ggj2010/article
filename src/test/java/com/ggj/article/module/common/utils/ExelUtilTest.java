package com.ggj.article.module.common.utils;

import base.BaseTest;
import com.ggj.article.module.business.bean.Article;
import com.ggj.article.module.business.bean.MediaSettleMent;
import com.ggj.article.module.business.dao.ArticleMapper;
import com.ggj.article.module.business.dao.MediaSettleMentMapper;
import com.ggj.article.module.business.dao.UserInfoMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author:gaoguangjin
 * @date 2017/7/31 12:08
 */
public class ExelUtilTest  extends BaseTest{

    @Autowired
    protected ArticleMapper articleMapper;
    @Autowired
    protected MediaSettleMentMapper mediaSettleMentMapper;
    //@Test
    public void importArticle() throws Exception {
//        List<Article> list = ExelUtil.importArticle(new FileInputStream(new File("c:杨敏47.xls")), 47);
////        List<Article> list = ExelUtil.importArticle(new FileInputStream(new File("c:苏时48.xls")), 48);
//        articleMapper.saveBatchArticle(list);
//        List<MediaSettleMent> mediaSettleMentList=new ArrayList<MediaSettleMent>();
//        for (Article article : list) {
//            MediaSettleMent mediaSettleMent=new MediaSettleMent();
//            mediaSettleMent.setArticleId(article.getId());
//            mediaSettleMent.setType("1");
//            mediaSettleMent.setStatus("1");
//            mediaSettleMentList.add(mediaSettleMent);
//        }
//        mediaSettleMentMapper.saveBatchSettle(mediaSettleMentList);
    }

}