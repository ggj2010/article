package com.ggj.article.module.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ggj.article.module.business.bean.*;
import com.ggj.article.module.business.dao.*;
import com.ggj.article.module.common.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ggj.article.module.common.crud.CrudService;

import static com.ggj.article.module.common.utils.UserUtils.getPrincipal;


/**
 * @author:gaoguangjin
 * @date 2017/5/15 16:38
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleMapper, Article> {

    @Autowired
    private CustomInfoMapper customInfoMapper;
    @Autowired
    protected UserInfoMapper userInfoMapper;
    @Autowired
    protected CustomUserInfoMapper customUserInfoMapperMapper;
    @Autowired
    private MediaMapper mediaDao;
    @Autowired
    private MediaSettleMentMapper mediaSettleMentMapper;

    @Transactional(readOnly = false)
    public void delete(Article entity) {
        dao.delete(entity);
        mediaSettleMentMapper.delete(new MediaSettleMent(entity.getId()));
    }

    @Transactional(readOnly = false)
    public void save(Map<String, String> fileUrlMap, ArticleVO articleVO) {
        JSONArray articleJsonArray = articleVO.getArticleInfo();
        JSONArray mediaJsonArray = articleVO.getMediaInfo();
        List<Article> articleList = new ArrayList<Article>();
        UserInfo userInfo= getPrincipal().getUserInfo();
        String customId="";
        String customName="";
        for (Object o : articleJsonArray) {
            Article article = new Article();
            JSONObject j = (JSONObject) o;
            article.setTitle(j.getString("title"));
            if(StringUtils.isNotEmpty(j.getString("customId"))){
                customId = j.getString("customId");
                customName = j.getString("customName");
                //新的客户
                if("0".equals(customId)&&userInfo.getUserType()==0){
                    UserInfo newUserInfo = new UserInfo();
                    newUserInfo.setUserName(customName);
                    userInfoMapper.insert(newUserInfo);
                    CustomInfo customInfos=new CustomInfo();
                    customInfos.setUserId(newUserInfo.getId());
                    customInfoMapper.insert(customInfos);

                    CustomUserInfo customUserInfo = new CustomUserInfo();
                    customUserInfo.setCustomId(customInfos.getId());
                    //创建人id
                    customUserInfo.setUserId(UserUtils.getPrincipal().getId());
                    customUserInfo.setCreateDate(new Date());
                    customUserInfoMapperMapper.insert(customUserInfo);
                    customId=customInfos.getId()+"";
                }

            }
            String fileName = j.getString("fileName");
            String type = j.getString("type");
            article.setArticleType(Integer.parseInt(type));
            if (type.equals("2")) {
                article.setUrl(j.getString("url"));
            } else {
                article.setUrl(fileUrlMap.get(fileName));
            }
            article.setRemark(j.getString("remark"));
            articleList.add(article);
        }
        for (Object o : mediaJsonArray) {
            JSONObject j = (JSONObject) o;
            Integer mediaId = j.getInteger("id");
            long customPrice = j.getLong("price");
            Media media = mediaDao.get(mediaId);
            long costPrice = mediaDao.get(mediaId).getCostPrice();
            for (Article article : articleList) {
                article.setMediaId(mediaId);
                article.setCustomId(Integer.parseInt(customId));
                article.setCustomName(customName);
                article.setCustomPrice(customPrice);
                article.setCostPrice(costPrice);
                article.setMediaName(media.getName());
                if(userInfo.getUserType()==0) {
                    article.setUserId(UserUtils.getPrincipal().getId());
                }else {
                    //个人id
                    article.setUserId(userInfo.getCustomInfo().getCustomUserId());
                    article.setCustomId(userInfo.getId());
                }
                article.setStatus(0);
                article.setCreateDate(new Date());
                dao.insert(article);
            }
        }

    }
    @Transactional(readOnly = false)
    public void verifying(Article article) {
        article.setStatus(1);
        //审核中
        article.setVerifyDate(new Date());
        dao.update(article);
    }
    //已审核
    @Transactional(readOnly = false)
    public void verify(Article article) {
        article.setStatus(2);
        //编辑者id
        article.setEditorId(getPrincipal().getId());
        article.setVerifyDate(new Date());
        dao.update(article);

        //类型1、客户结算2、公司结算、3、编辑结算
        MediaSettleMent mediaSettleMent1=new MediaSettleMent();
        mediaSettleMent1.setArticleId(article.getId());
        mediaSettleMent1.setType("1");
        mediaSettleMent1.setStatus("0");
        MediaSettleMent mediaSettleMent2=new MediaSettleMent();
        mediaSettleMent2.setArticleId(article.getId());
        mediaSettleMent2.setType("2");
        mediaSettleMent2.setStatus("0");
        MediaSettleMent mediaSettleMent3=new MediaSettleMent();
        mediaSettleMent3.setArticleId(article.getId());
        mediaSettleMent3.setType("3");
        mediaSettleMent3.setStatus("0");
        mediaSettleMentMapper.insert(mediaSettleMent1);
        mediaSettleMentMapper.insert(mediaSettleMent2);
        mediaSettleMentMapper.insert(mediaSettleMent3);
    }

    //退稿
    @Transactional(readOnly = false)
    public void back(Article article) {
        article.setStatus(3);
        //审核中
        article.setVerifyDate(new Date());
        dao.update(article);
    }
}
