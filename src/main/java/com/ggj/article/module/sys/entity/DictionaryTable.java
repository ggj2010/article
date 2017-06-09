package com.ggj.article.module.sys.entity;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.ggj.article.module.common.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName:DictionaryTable.java
 * @Description: 系统字典表维护  
 * @author gaoguangjin
 * @Date 2015-9-23 下午2:03:55
 */
@Getter
@Setter
public class DictionaryTable extends BaseEntity {
	/*
	 * @NotEmpty 用在集合类上面
	 * 
	 * @NotBlank 用在String上面
	 * 
	 * @NotNull 用在基本类型上
	 */
	
	private static final long serialVersionUID = 6135938623993844435L;
	// 字典名称
	@NotEmpty(message = "字典名称不能为空")
	private String name;
	// 字典值
	@NotEmpty(message = "字典值不能为空")
	private String value;
	// 类型
	@NotEmpty(message = "字典类型不能为空")
	private String type;
	// 描述
	@Length(max = 255)
	private String description;
	// 下拉框显示时候排序
	@Max(value = 2147483646, message = "排序值不能超过int类型")
	private int sort;
	
	public DictionaryTable() {
	}
	
	public DictionaryTable(String type) {
		this.type = type;
	}
}
