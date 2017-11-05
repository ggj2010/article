package com.ggj.article.module.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:gaoguangjin
 * @date:2017/11/5
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {

    @RequestMapping("")
    public String index() {
        return "notice";
    }
}
