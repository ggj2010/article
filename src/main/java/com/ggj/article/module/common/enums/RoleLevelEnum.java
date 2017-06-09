package com.ggj.article.module.common.enums;

/**
 * 角色等级
 * @author:gaoguangjin
 * @date 2017/4/25 22:40
 */
public enum RoleLevelEnum {
	gold(1, "金牌"), silver(2, "银牌"), bronze(3, "金牌");
	
	private int level;
	private String name;
	
	private RoleLevelEnum(int level, String name) {
		this.level = level;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}
