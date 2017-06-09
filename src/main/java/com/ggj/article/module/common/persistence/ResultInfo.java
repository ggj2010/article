package com.ggj.article.module.common.persistence;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * author:gaoguangjin
 * Description:
 * Email:335424093@qq.com
 * Date 2016/1/5 18:56
 */
@Getter
@Setter
public class ResultInfo {

    protected boolean success = false;
    protected String message = null;
    protected Map<String,Object> data = new HashMap<String,Object>();

    public ResultInfo(boolean success) {
        this.success = success;
    }


    public ResultInfo(String key, Object object) {
        this.success = true;
        this.data.put(key, object);
    }

    public ResultInfo(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public ResultInfo(boolean success, String message, Map<String,Object> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
