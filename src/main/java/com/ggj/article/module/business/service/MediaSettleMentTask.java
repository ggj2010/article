package com.ggj.article.module.business.service;


import com.ggj.article.module.business.bean.MediaSettleMent;
import com.ggj.article.module.common.utils.PageUtils;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;

/**
 * 异步取数据
 */
public class MediaSettleMentTask implements Callable<PageInfo<MediaSettleMent>> {
    private MediaSettleMentService mediaSettleMentService;
    private MediaSettleMent mediaSettleMent;
    private HttpServletRequest request;
    private HttpServletResponse rep;
    private PageUtils pageUtils;

    public MediaSettleMentTask(MediaSettleMentService mediaSettleMentService, MediaSettleMent mediaSettleMent, HttpServletRequest request, HttpServletResponse rep, PageUtils pageUtils) {
        this.mediaSettleMent = mediaSettleMent;
        this.mediaSettleMentService = mediaSettleMentService;
        this.pageUtils = pageUtils;
        this.request = request;
        this.rep = rep;
    }

    @Override
    public PageInfo<MediaSettleMent> call() throws Exception {
        pageUtils.setPage(request, rep);
        return mediaSettleMentService.findPage(mediaSettleMent);
    }
}
