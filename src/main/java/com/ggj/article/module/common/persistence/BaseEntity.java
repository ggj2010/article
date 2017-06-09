package com.ggj.article.module.common.persistence;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.ggj.article.module.common.utils.DateUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName:BaseEntity.java
 * @Description:所有entity父类    
 * @author gaoguangjin
 * @Date 2015-9-24 上午10:43:44
 */
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -3585903986953379399L;
	
	protected Integer id;
	
	// 创建日期
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	protected Date createDate;
	
	// 更新者
	protected String createUser;
	
	protected String remark;
	
	protected String flag = "0";
	
	/**
	 * @Description: crud保存
	 * @return:void
	 */
	public void preInsert() {
		this.createDate = new Date();
		// 登陆之后才有创建者
		// UserInfo currentUserInfo = UserUtils.getCurrentUserInfo();
		// if (currentUserInfo != null)
		// this.createUser = currentUserInfo;
	}
	
	/**
	 * @Description: crud更新
	 * @return:void
	 */
	public void preUpdate() {
		// this.updateDate = new Date();
		// 登陆之后才有更新者
		// UserInfo currentUserInfo = UserUtils.getCurrentUserInfo();
		// if (currentUserInfo != null)
		// this.updateUser = currentUserInfo;
	}
}
