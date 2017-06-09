package com.ggj.article.module.business.bean;

import java.util.Date;

import com.ggj.article.module.common.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article extends BaseEntity {
	
	private Integer userId;
	private Integer customId;
	private Integer editorId;
	private Integer mediaId;
	private String mediaName;
	private String title;
	private String customName;
	private Integer articleType;
	private long costPrice;
	private long customPrice;
	private String url;
	private String verifyUrl;
	//待审核、已审核、已退稿、已删除
	private Integer status;
	protected Date beginTime;
	protected Date verifyDate;
	protected Date endTime;
	protected String typeParam;

//	public String getVerifyDate() {
//		return DateUtils.formatDate(verfiyDate);
//	}
}