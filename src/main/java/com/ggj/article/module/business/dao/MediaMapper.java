package com.ggj.article.module.business.dao;

import com.ggj.article.module.business.bean.Media;
import com.ggj.article.module.common.crud.CrudDao;

import java.util.List;

public interface MediaMapper extends CrudDao<Media> {
    List<Media> findEditorList(Media media);
    void updateStatus(int id);
}