package com.ggj.article.module.business.bean;

import java.util.Date;

import com.ggj.article.module.common.persistence.BaseEntity;

import com.ggj.article.module.common.utils.DateUtils;
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
	private Integer verifyStatus;
	private String beginTime;
	private String beginTimeStr;
	private Date verifyDate;
	private String endTime;
	private String endTimeStr;
	private String timeType;

	public Date getBeginTime() {
		return DateUtils.parseDate(beginTimeStr);
	}


	public Date getEndTime() {
		return DateUtils.parseDate(endTimeStr+" 23:59:59");
	}


	private String typeParam;

//	public String getVerifyDate() {
//		return DateUtils.formatDate(verfiyDate);
//	}
}