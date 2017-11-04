package com.ggj.article.module.common.exception;

/**
 * @author:gaoguangjin
 * @date:2017/11/4
 */
public class BizException extends RuntimeException{
    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }
}
