package com.ggj.article.module.business.service;

import com.ggj.article.module.business.bean.MediaSettleMent;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;

import java.util.concurrent.Callable;

public class MediaSettleMentStatisticsTask implements Callable<PageInfo<MediaSettleMent>> {

    private MediaSettleMentService mediaSettleMentService;
    private MediaSettleMent mediaSettleMent;
    private Model model;

    public MediaSettleMentStatisticsTask(MediaSettleMentService mediaSettleMentService, MediaSettleMent mediaSettleMent, Model model) {
        this.mediaSettleMent = mediaSettleMent;
        this.mediaSettleMentService = mediaSettleMentService;
        this.model = model;
    }

    @Override
    public PageInfo<MediaSettleMent> call() throws Exception {
        mediaSettleMentService.settleStatistics(model, mediaSettleMent);
        return null;
    }
}
