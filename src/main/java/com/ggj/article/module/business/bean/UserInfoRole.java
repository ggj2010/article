package com.ggj.article.module.business.bean;

import com.ggj.article.module.common.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRole extends BaseEntity {

    private Integer userInfoId;

    private Integer roleId;

    public UserInfoRole(Integer userInfoId) {
        this.userInfoId = userInfoId;
    }

    public UserInfoRole() {
    }
}