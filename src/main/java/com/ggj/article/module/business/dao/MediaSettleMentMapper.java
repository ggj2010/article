package com.ggj.article.module.business.dao;

import java.util.List;

import com.ggj.article.module.business.bean.MediaSettleMent;
import com.ggj.article.module.business.bean.MediaSettleMentCount;
import com.ggj.article.module.common.crud.CrudDao;

public interface MediaSettleMentMapper extends CrudDao<MediaSettleMent> {
	
	void saveBatchSettle(List<MediaSettleMent> list);
	List<MediaSettleMentCount> settleStatistics(MediaSettleMent mediaSettleMent);
}