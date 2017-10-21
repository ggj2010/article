package com.ggj.article.module.business.service;

import com.ggj.article.module.business.bean.Article;
import com.ggj.article.module.business.bean.MediaSettleMent;
import com.ggj.article.module.business.bean.MediaSettleMentCount;
import com.ggj.article.module.business.dao.MediaSettleMentMapper;
import com.ggj.article.module.common.utils.ExelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggj.article.module.business.bean.Media;
import com.ggj.article.module.business.dao.MediaMapper;
import com.ggj.article.module.common.crud.CrudService;
import org.springframework.ui.Model;

import java.util.List;


/**
 * @author:gaoguangjin
 * @date 2017/5/15 16:38
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class MediaSettleMentService extends CrudService<MediaSettleMentMapper, MediaSettleMent> {

    @Override
    @Transactional(readOnly = false)
    public void save(MediaSettleMent mediaSettleMent) {
        mediaSettleMent.setStatus("1");
        dao.update(mediaSettleMent);
    }

    public void settleStatistics(Model model, MediaSettleMent mediaSettleMent) {
        int totalSize = 0;
        long totalPrice = 0;
        int settleSize = 0;
        long settlePrice = 0;
        boolean isCostPrice = false;

        if (mediaSettleMent.getArticle() != null) {
            Article article = mediaSettleMent.getArticle();
            //编辑结算
            if (article.getEditorId() != null && article.getEditorId() > 0) {
                isCostPrice = true;
            }
        } else {
            //系统编辑结算
            if (mediaSettleMent.getBussinnessType().equals("2") && mediaSettleMent.getType().equals("3")) {
                isCostPrice = true;
            }
        }
        long beginTime=System.currentTimeMillis();
        List<MediaSettleMentCount> list = dao.settleStatistics(mediaSettleMent);
        log.info("耗时：{}", (System.currentTimeMillis()-beginTime)+"ms");
        if (CollectionUtils.isNotEmpty(list)) {
            for (MediaSettleMentCount mediaSettleMentCount : list) {
                //未结算
                if (mediaSettleMentCount.getStatus() == 1) {
                    if (isCostPrice) {
                        settlePrice = mediaSettleMentCount.getSumCostPrice();
                    } else {
                        settlePrice = mediaSettleMentCount.getSumCustomPrice();
                    }
                    settleSize = mediaSettleMentCount.getTotal();
                }
                if (isCostPrice) {
                    //成本价
                    totalPrice += mediaSettleMentCount.getSumCostPrice();
                } else {
                    //报价
                    totalPrice += mediaSettleMentCount.getSumCustomPrice();
                }
                totalSize += mediaSettleMentCount.getTotal();
            }
        }
        model.addAttribute("totalSize", totalSize);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("settleSize", settleSize);
        model.addAttribute("settlePrice", settlePrice);
    }
}
