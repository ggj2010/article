package com.ggj.article.module.sys.dao;

import java.util.List;

import com.ggj.article.module.common.crud.CrudDao;
import com.ggj.article.module.sys.entity.DictionaryTable;

/**
 * @ClassName:DictionaryTableDao.java
 * @Description:    字典表数据层
 * @author gaoguangjin
 * @Date 2015-9-23 下午1:23:44
 */
public interface DictionaryTableDao extends CrudDao<DictionaryTable> {
	
	public List<String> getType(DictionaryTable dt);

	List<String> getValue(DictionaryTable dictionaryTable);
}
