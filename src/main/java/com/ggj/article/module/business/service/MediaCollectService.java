package com.ggj.article.module.business.service;

import com.ggj.article.module.business.bean.MediaCollect;
import com.ggj.article.module.business.dao.MediaCollectMapper;
import com.ggj.article.module.common.crud.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author:gaoguangjin
 * @date 2017/5/15 16:38
 */
@Service
@Transactional(readOnly = true)
public class MediaCollectService extends CrudService<MediaCollectMapper, MediaCollect> {

}
