package com.ggj.article.module.common.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

/**
 * @ClassName:IdGen.java
 * @Description:  封装各种生成唯一性ID算法的工具类.  
 * @author gaoguangjin
 * @Date 2015-9-2 下午6:31:37
 */
public class IdGen implements SessionIdGenerator {
	
	private SecureRandom random = new SecureRandom();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String getNextId() {
		return IdGen.uuid();
	}
	
	public  Serializable generateId(Session session) {
		return IdGen.uuid();
	}
	
	/**
	 * @Description:  密码盐用用户名+随机数
	 * @param str
	 * @return:String
	 */
	public static String getSalt(String str) {
		return new SecureRandomNumberGenerator().nextBytes().toHex() + str;
	}
	
	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public long randomLong() {
		return Math.abs(random.nextLong());
	}
}
