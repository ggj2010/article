package com.ggj.article.module.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

/**
 * @ClassName:PageUtils.java
 * @Description:   分页插件参数封装
 * @author gaoguangjin
 * @Date 2015-9-24 下午6:04:16
 */
@Service
public class PageUtils {
	private int defaultPageSize=50;
	
	/**
	 * @Description: 
	 * @param requst 传递 page 参数，来记住页码
	 * @param response   用于设置 Cookie，记住页码   
	 * @return 
	 * @return:void
	 */
	public void setPage(HttpServletRequest request, HttpServletResponse response) {
		int pageNum = 0, pageSize = 0;
		// 当前页数
		String pageNumString = request.getParameter("pageNum");
		// 每页大小
		String pageSizeString = request.getParameter("pageSize");
		
		if (StringUtils.isNumeric(pageNumString) && StringUtils.isNumeric(pageSizeString)) {
			pageNum = Integer.parseInt(pageNumString);
			pageSize = Integer.parseInt(pageSizeString);
			
			CookieUtils.setCookie(response, "pageSize", pageSizeString);
			PageHelper.startPage(pageNum, pageSize);
		} else {
			// 默认第一页
			pageNum = 1;
			pageSizeString = CookieUtils.getCookie(request, "pageSize");
			// 缓存没有，那就设置默认的。
			if (!StringUtils.isNumeric(pageSizeString)) {
				CookieUtils.setCookie(response, "pageSize", defaultPageSize + "");
				pageSize = defaultPageSize;
			} else {
				pageSize = Integer.parseInt(pageSizeString);
			}
		}
		PageHelper.startPage(pageNum, pageSize);
	}
}
