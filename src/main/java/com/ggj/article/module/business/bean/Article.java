package com.ggj.article.module.business.bean;

import java.util.Date;
import java.util.List;

import com.ggj.article.module.common.persistence.BaseEntity;

import com.ggj.article.module.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article extends BaseEntity {
	//员工id
	private Integer userId;
	private String userName;
	//顾客id
	private Integer customId;
	//员工id
	private Integer editorId;
	private String editorName;
	private Integer mediaId;
	private List<String>  mediaIdStr;
	private String mediaName;
	private String title;
	private String customName;
	private Integer articleType;
	private long costPrice;
	private long customPrice;
	private String url;
	private String verifyUrl;
	//0待审核、1、审核中、2已审核、3已退稿、4已删除
	private Integer status;
	//已审核稿件阅读状态，方便客户检阅。1、未提取，2、未提取
	private Integer approveStatus;
	private String refundRemark;
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

}