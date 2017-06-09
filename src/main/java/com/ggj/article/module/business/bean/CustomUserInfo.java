package com.ggj.article.module.business.bean;

import com.ggj.article.module.common.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserInfo extends BaseEntity {

    private Integer customId;
    //绑定人的id
    private Integer userId;
    private Integer customStatus;
    private CustomInfo customInfo;

    public CustomUserInfo(int userId) {
        this.userId = userId;
    }

    public CustomUserInfo() {
    }
}