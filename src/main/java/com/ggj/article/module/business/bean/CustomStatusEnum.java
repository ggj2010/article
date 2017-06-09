package com.ggj.article.module.business.bean;

/**
 * @author:gaoguangjin
 * @date 2017/5/22 15:35
 */
public enum CustomStatusEnum {
	processing(0, "申请中"), endProcessing(1, "已开通");
	
	private Integer customStatus;
	
	private String name;
	
	private CustomStatusEnum(Integer customStatus, String name) {
		this.customStatus = customStatus;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static CustomStatusEnum valueOf(Integer customStatus) {
		switch(customStatus) {
			case 0:
				return processing;
			case 1:
				return endProcessing;
		}
		return null;
	}
	
	public Integer getCustomStatus() {
		return customStatus;
	}
	
	public void setCustomStatus(Integer customStatus) {
		this.customStatus = customStatus;
	}
}
