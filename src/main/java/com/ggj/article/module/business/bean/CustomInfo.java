package com.ggj.article.module.business.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomInfo extends UserInfo {
    private Integer userId;

    private String qqNumber;

    private String level;

    //客户关联的用户id
    private Integer customUserId;

    // 0 申请中，1已开通
    private Long customStatus;
    //参数用到
    private String formUrl;


    public CustomInfo(Integer customUserId) {
        this.customUserId = customUserId;
    }

    public CustomInfo() {
    }
}