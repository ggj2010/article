package com.ggj.article.module.base.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for(String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	protected void addErrorMessage(RedirectAttributes redirectAttributes, String... messages) {
		addMessage(redirectAttributes, messages);
		redirectAttributes.addFlashAttribute("level", "error");
	}

	@ExceptionHandler({UnauthorizedException.class})
	public String bizException(UnauthorizedException e, HttpServletRequest request, Model model) {
		return "error/unauthorizedExceptions";
	}
}
