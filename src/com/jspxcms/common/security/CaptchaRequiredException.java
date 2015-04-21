package com.jspxcms.common.security;

import org.apache.shiro.authc.AccountException;

/**
 * 账户异常，需要验证码
 * 
 * @author liufang
 * 
 */
public class CaptchaRequiredException extends AccountException {
	private static final long serialVersionUID = 1L;
}
