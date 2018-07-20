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

    private String mediaChannel;

    private String mediaRegion;

    private String publishSpeed;

    private Integer  baiduSeo;

    private String exampleUrl;

    private String typeParam;

    private String fansNum;
    private String readsNum;
    /**
     * 1 未审核，2审核通过。
     */
    private int status;


    public Media(int id) {
        this.id = id;
    }

    public Media() {
    }
}