package com.ggj.article.module.business.bean;

import com.ggj.article.module.common.persistence.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:gaoguangjin
 * @date:2017/12/16
 */
@Data
public class Notice extends BaseEntity implements Serializable {
    private String title;
    private String content;
}
