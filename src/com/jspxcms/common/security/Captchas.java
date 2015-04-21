package com.jspxcms.common.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * 验证码工具类
 * 
 * @author liufang
 * 
 */
public abstract class Captchas {
	protected static final Logger logger = LoggerFactory
			.getLogger(Captchas.class);

	public static boolean isValid(CaptchaService service,
			HttpServletRequest request, String captcha) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		try {
			return service.validateResponseForID(session.getId(), captcha);
		} catch (CaptchaServiceException e) {
			logger.warn("captcha exception", e);
			return false;
		}
	}
}
