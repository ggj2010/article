package com.ggj.article.module.business.service;

import com.ggj.article.module.business.bean.Media;
import com.ggj.article.module.business.dao.MediaMapper;
import com.ggj.article.module.common.crud.CrudService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author:gaoguangjin
 * @date 2017/5/15 16:38
 */
@Service
@Transactional(readOnly = true)
public class MediaService extends CrudService<MediaMapper, Media> {

    @Transactional(readOnly = false)
    public void save(final Media entity) {
        super.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(Media entity) {
        super.delete(entity);
    }

    public PageInfo<Media> findEditorList(Media media) {
        return new PageInfo<Media>(dao.findEditorList(media));
    }
}
