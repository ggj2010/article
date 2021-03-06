package com.ggj.article.module.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ggj.article.module.business.bean.*;
import com.ggj.article.module.business.bean.enums.ArticleStatusEnum;
import com.ggj.article.module.business.bean.enums.SettlementTypeEnum;
import com.ggj.article.module.business.dao.*;
import com.ggj.article.module.common.exception.BizException;
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
        UserInfo userInfo = getPrincipal().getUserInfo();
        String customId = "";
        String customName = "";
        String userName = "";
        Integer userId = 0;
        for (Object o : articleJsonArray) {
            Article article = new Article();
            JSONObject j = (JSONObject) o;
            article.setTitle(j.getString("title"));
            if (StringUtils.isNotEmpty(j.getString("customId"))) {
                customId = j.getString("customId");
                customName = j.getString("customName");
                //新的客户
                if ("0".equals(customId) && userInfo.getUserType() == 0) {
                    UserInfo newUserInfo = new UserInfo();
                    newUserInfo.setUserName(customName);
                    userInfoMapper.insert(newUserInfo);
                    CustomInfo customInfos = new CustomInfo();
                    customInfos.setUserId(newUserInfo.getId());
                    //非会员
                    customInfos.setCustomStatus(0L);
                    customInfoMapper.insert(customInfos);
                    customId = newUserInfo.getId() + "";
                    CustomUserInfo customUserInfo = new CustomUserInfo();
                    customUserInfo.setCustomId(customInfos.getId());
                    //创建人id
                    customUserInfo.setUserId(UserUtils.getPrincipal().getId());
                    customUserInfo.setCreateDate(new Date());
                    customUserInfoMapperMapper.insert(customUserInfo);

                }

            }
            String fileName = j.getString("fileName");
            String type = j.getString("type");
            article.setArticleType(Integer.parseInt(type));
            article.setApproveStatus(1);
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
                if (userInfo.getUserType() == 0) {
                    userId = UserUtils.getPrincipal().getId();
                    userName = getUserName(userName, userId);
                    article.setUserId(userId);
                    article.setUserName(userName);
                } else {
                    //个人id
                    userId = userInfo.getCustomInfo().getCustomUserId();
                    userName = getUserName(userName, userId);
                    article.setUserName(userName);
                    article.setUserId(userId);
                    article.setCustomId(userInfo.getId());
                }
                article.setStatus(0);
                article.setCreateDate(new Date());
                if(article.getTitle().contains("\"")){
                    article.setTitle(article.getTitle().replaceAll("\"",""));
                }
                dao.insert(article);
            }
        }

    }

    private String getUserName(String userName, Integer userId) {
        if (StringUtils.isEmpty(userName)) {
            return userInfoMapper.findUserInfoList(userId).get(0).getUserName();
        }
        return userName;
    }

    @Transactional(readOnly = false)
    public void verifying(Article article) {
        if (ArticleStatusEnum.APPROVE_ING.getStatus() != article.getStatus()) {
            article.setStatus(1);
            //审核中
            article.setVerifyDate(new Date());
            dao.update(article);
        }
    }

    //已审核
    @Transactional(readOnly = false)
    public void verify(Article article) {
        if (article.getStatus() <= ArticleStatusEnum.APPROVE_ING.getStatus()) {
            article.setStatus(2);
            //编辑者id
            article.setEditorId(getPrincipal().getId());
            article.setEditorName(getPrincipal().getName());
            article.setVerifyDate(new Date());
            dao.update(article);

            //类型1、客户结算2、公司结算、3、编辑结算
            MediaSettleMent mediaSettleMent1 = new MediaSettleMent();
            mediaSettleMent1.setArticleId(article.getId());
            mediaSettleMent1.setType(SettlementTypeEnum.CUSTOM.getType()+"");
            mediaSettleMent1.setStatus("0");
            mediaSettleMent1.setOriginalPrice(article.getCustomPrice());
            MediaSettleMent mediaSettleMent2 = new MediaSettleMent();
            mediaSettleMent2.setArticleId(article.getId());
            mediaSettleMent2.setType(SettlementTypeEnum.USER.getType()+"");
            mediaSettleMent2.setStatus("0");
            mediaSettleMent2.setOriginalPrice(article.getCustomPrice());
            MediaSettleMent mediaSettleMent3 = new MediaSettleMent();
            mediaSettleMent3.setArticleId(article.getId());
            mediaSettleMent3.setType(SettlementTypeEnum.EDITOR.getType()+"");
            mediaSettleMent3.setStatus("0");
            mediaSettleMent3.setOriginalPrice(article.getCostPrice());
            mediaSettleMentMapper.insert(mediaSettleMent1);
            mediaSettleMentMapper.insert(mediaSettleMent2);
            mediaSettleMentMapper.insert(mediaSettleMent3);
        } else {
            throw new BizException("审核失败，请勿重复审核！");
        }
    }

    //退稿
    @Transactional(readOnly = false)
    public void back(Article article) {
        article.setStatus(3);
        //审核中
        article.setVerifyDate(new Date());
        dao.update(article);
    }

    @Transactional(readOnly = false)
    public void update(Article article) {
        super.dao.update(article);
    }

    public List<UserInfo> getUserInfo() {
        return userInfoMapper.findUserInfoList(null);
    }

    //提取
    @Transactional(readOnly = false)
    public void review(Article article) {
        article.setApproveStatus(2);
        dao.update(article);
    }
}
