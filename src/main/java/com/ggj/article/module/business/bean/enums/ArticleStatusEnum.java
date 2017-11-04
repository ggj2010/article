package com.ggj.article.module.business.bean.enums;

import lombok.Getter;

/**
 * @author:gaoguangjin
 * @date:2017/11/4
 */
public enum ArticleStatusEnum {
    NEED_APPROVE(0, "待审核"),

    APPROVE_ING(1, "审核中"),

    APPROVED(2, "已审核"),

    REJECTED(3, "已退稿"),

    DELETE(4, "已删除");
    @Getter
    private int status;
    private String desc;

    private ArticleStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}
