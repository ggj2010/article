package com.ggj.article.module.business.bean.enums;

import lombok.Getter;

/**
 * 登录系统的四个角色
 *
 * @author:gaoguangjin
 * @date:2017/11/4
 */
public enum LoginRoleEnum {

    USER(1, "user", "员工"),
    EDITOR(2, "editor", "编辑"),
    CUSTOM(3, "custom", "顾客"),
    ADMIN(4, "admin", "管理员");
    @Getter
    private int type;
    @Getter
    private String name;
    private String desc;

    private LoginRoleEnum(int type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }
}
