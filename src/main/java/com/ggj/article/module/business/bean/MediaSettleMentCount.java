package com.ggj.article.module.business.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class MediaSettleMentCount implements Serializable{
    private static final long serialVersionUID = -1814528314920353016L;
    //报价 客户和员工用到
    private long sumCustomPrice;
    //成本价格
    private long sumCostPrice;
    //0未结算，1已结算
    private Integer status;
    //总数量
    private Integer total;
}
