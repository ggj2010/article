package com.ggj.article.module.business.bean.enums;

import lombok.Getter;

/**
 * 结算类型
 *
 * @author:gaoguangjin
 * @date:2017/11/4
 */
public enum SettlementTypeEnum {

    CUSTOM(1, "员工给客户结算"),

    USER(2, "公司给员工结算"),

    EDITOR(3, "公司给编辑结算");
    @Getter
    private int type;
    private String desc;

    private SettlementTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
