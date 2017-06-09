package com.ggj.article.module.business.dao;

import com.ggj.article.module.business.bean.MediaEditor;
import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.common.crud.CrudDao;

public interface MediaEditorMapper extends CrudDao< MediaEditor> {
    void saveUserInfoMedia(UserInfo userInfo);
}