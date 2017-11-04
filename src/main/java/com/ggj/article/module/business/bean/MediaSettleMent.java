package com.ggj.article.module.business.bean;

import com.ggj.article.module.common.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MediaSettleMent extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -3317883196931592556L;
	private Integer articleId;
//	1、客户结算2、员工结算、3、编辑结算
	private String type;
	private String bussinnessType;
	//结算价格
	private long price;
	//原价
	private long originalPrice;
	private long customPrice;
	private String status;
	private Article article;

	public MediaSettleMent(Integer articleId) {
		this.articleId=articleId;
	}
	public MediaSettleMent() {
	}
}