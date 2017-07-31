package com.ggj.article.module.business.service;

import com.ggj.article.module.business.bean.Media;
import com.ggj.article.module.business.dao.MediaMapper;
import com.ggj.article.module.common.crud.CrudService;
import com.ggj.article.module.common.utils.ExelUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;


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
    public List<Media> findAllEditorList(Media media) {
        return  dao.findEditorList(media);
    }
    @Transactional(readOnly = false)
    public void saveImport(InputStream inputStream) throws Exception {
        List<Media> lists = ExelUtil.importMediExel(inputStream);
        for (Media media : lists) {
            List<Media> mediaList = super.findList(media);
            if(mediaList.size()==0) {
                super.save(media);
            }
        }
    }
}
