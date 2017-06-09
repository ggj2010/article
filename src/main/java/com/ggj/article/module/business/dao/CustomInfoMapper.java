package com.ggj.article.module.business.dao;

import com.ggj.article.module.business.bean.CustomInfo;
import com.ggj.article.module.common.crud.CrudDao;

public interface CustomInfoMapper extends CrudDao<CustomInfo> {
    void verify(CustomInfo customInfo);
    CustomInfo getRole(int id);
}