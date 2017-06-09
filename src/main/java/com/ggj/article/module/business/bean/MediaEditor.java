package com.ggj.article.module.business.bean;

import com.ggj.article.module.common.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaEditor extends BaseEntity {
	
	private Integer mediaId;
	
	private Integer editorId;

	public MediaEditor(Integer id) {
		this.editorId=id;
	}
	public MediaEditor() {
	}
}