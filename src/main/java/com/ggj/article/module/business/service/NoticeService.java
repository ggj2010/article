package com.ggj.article.module.business.service;

import com.ggj.article.module.business.bean.Notice;
import com.ggj.article.module.business.dao.NoticeMapper;
import com.ggj.article.module.common.crud.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:gaoguangjin
 * @date:2017/12/16
 */
@Service
@Transactional(readOnly = true)
public class NoticeService extends CrudService<NoticeMapper, Notice> {
}
