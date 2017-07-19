package com.ggj.article.module.business.bean;

import com.ggj.article.module.common.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Media extends BaseEntity {

    private String name;
    private Integer userId;

    private Long goldPrice;

    private Long silverPrice;

    private Long bronzePrice;

    private Long costPrice;

    private String collectionType;

    private String mediaType;

    private String mediaRegion;

    private String publishSpeed;

    private Integer  baiduSeo;

    private String exampleUrl;

    private String typeParam;

    public Media(int id) {
        this.id = id;
    }

    public Media() {
    }
}