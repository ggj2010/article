package com.ggj.article.module.business.bean;

import com.ggj.article.module.common.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaSettleMent extends BaseEntity {
	
	private Integer articleId;
//	1、客户结算2、员工结算、3、编辑结算
	private String type;
	private String bussinnessType;
	private long price;
	private String status;
	private Article article;

	public MediaSettleMent(Integer articleId) {
		this.articleId=articleId;
	}
	public MediaSettleMent() {
	}
}