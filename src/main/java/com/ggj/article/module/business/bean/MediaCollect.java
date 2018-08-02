package com.ggj.article.module.business.bean;

import com.ggj.article.module.common.persistence.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaCollect extends BaseEntity {

	private Integer mediaId;

	private Integer userId;

	public MediaCollect(Integer id) {
		this.userId=id;
	}
	public MediaCollect() {
	}
}