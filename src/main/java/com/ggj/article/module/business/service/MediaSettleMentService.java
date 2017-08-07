package com.ggj.article.module.business.service;

import com.ggj.article.module.business.bean.MediaSettleMent;
import com.ggj.article.module.business.dao.MediaSettleMentMapper;
import com.ggj.article.module.common.utils.ExelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggj.article.module.business.bean.Media;
import com.ggj.article.module.business.dao.MediaMapper;
import com.ggj.article.module.common.crud.CrudService;

import java.util.List;


/**
 * @author:gaoguangjin
 * @date 2017/5/15 16:38
 */
@Service
@Transactional(readOnly = true)
public class MediaSettleMentService extends CrudService<MediaSettleMentMapper, MediaSettleMent> {
    @Override
    @Transactional(readOnly = false)
    public void save(MediaSettleMent mediaSettleMent) {
        mediaSettleMent.setStatus("1");
        dao.update(mediaSettleMent);
    }

}
