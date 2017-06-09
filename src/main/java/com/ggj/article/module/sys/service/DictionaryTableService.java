package com.ggj.article.module.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggj.article.module.common.crud.CrudService;
import com.ggj.article.module.sys.dao.DictionaryTableDao;
import com.ggj.article.module.sys.entity.DictionaryTable;

/**
 * @author gaoguangjin
 * @ClassName:DictionaryTableService.java
 * @Description: 字典表服务层
 * @Date 2015-9-23 下午1:24:00
 */
@Service
@Transactional(readOnly = true)
public class DictionaryTableService extends CrudService<DictionaryTableDao, DictionaryTable> {


    @Override
    @Transactional(readOnly = false)
    public void save(final DictionaryTable entity) {
        super.save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(DictionaryTable entity) {
        super.delete(entity);
    }

    /**
     * @param dictionaryTable
     * @Description: like查询
     * @return:List<String>
     */
    public List<String> getLikeType(DictionaryTable dictionaryTable) {
        return dao.getType(dictionaryTable);
    }

    public Object getType(DictionaryTable dictionaryTable) {
        return dao.getType(dictionaryTable);
    }

    public List<String> getValueList(DictionaryTable dictionaryTable) {
        return dao.getValue(dictionaryTable);
    }
}
