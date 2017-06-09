package com.ggj.article.module.business.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.Setter;

/**
 * @author:gaoguangjin
 * @date 2017/5/31 15:14
 */
@Setter
public class ArticleVO {

    private String articleInfo;
    private String mediaInfo;

    public JSONArray getArticleInfo() {

        return JSONObject.parseArray(articleInfo.replace("&quot;", "\""));
    }

    public JSONArray getMediaInfo() {
        return JSONObject.parseArray(mediaInfo.replace("&quot;", "\""));
    }
}
