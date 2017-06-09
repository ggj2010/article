package com.ggj.article.module.sys.entity;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotEmpty;

import com.ggj.article.module.common.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Menu extends BaseEntity {
	private static final long serialVersionUID = -2104898640176086364L;
	// 父类id
	@Max(value = 2147483646,message = "父类按钮名称不能为空")
	private Integer parentId;
	private String parentName;
	private String name;
	private String href;
	// 排序
	// 下拉框显示时候排序
	@Max(value = 2147483646, message = "排序值不能超过int类型")
	private Integer sort;
	// 权限标识
	private String permission;
	@NotEmpty(message = "是否显示不能为空")
	private String isShow;
	// 跳转方式
	private String target;
	// 去除的id
	private Integer extId;
	
	// 功能父节点id默认为1
	public static String getRootId() {
		return "1";
	}
	
	public Menu() {
		this.sort = 30;
		this.isShow = "0";
	}
	public Menu(Integer id) {
		this.id = id;
	}

	public String toString() {
		return "id=" + super.getId() + "name=" + name;
	}
	
}
