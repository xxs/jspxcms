package com.jspxcms.core.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jspxcms.core.support.Configurable;

public class GlobalRegister implements Configurable {
	public static final String PREFIX = "register_";
	/**
	 * 注册模式：关闭注册
	 */
	public static final int MODE_OFF = 0;
	/**
	 * 注册模式：开放注册
	 */
	public static final int MODE_ON = 1;
	/**
	 * 注册验证模式：不验证
	 */
	public static final int VERIFY_MODE_NONE = 0;
	/**
	 * 注册验证模式：邮箱验证
	 */
	public static final int VERIFY_MODE_EMAIL = 1;
	/**
	 * 注册验证模式：人工验证
	 */
	public static final int VERIFY_MODE_MANUAL = 2;

	/**
	 * 注册模式。0：关闭注册，1：开放注册。
	 */
	public static final String MODE = "register_mode";
	/**
	 * 会员组ID
	 */
	public static final String GROUP_ID = "register_groupId";
	/**
	 * 组织ID
	 */
	public static final String ORG_ID = "register_orgId";
	/**
	 * 用户名合法字符。
	 */
	public static final String VALID_CHARACTER = "register_validCharacter";
	/**
	 * 用户名最小长度。
	 */
	public static final String MIN_LENGTH = "register_minLength";
	/**
	 * 用户名最大长度。
	 */
	public static final String MAX_LENGTH = "register_maxLength";
	/**
	 * 用户名保留字。
	 */
	public static final String RESERVED_WORDS = "register_reservedWords";
	/**
	 * 用户验证模式。0：不验证，1：邮件验证，2：人工审核。
	 */
	public static final String VERIFY_MODE = "register_verifyMode";
	/**
	 * 用户协议。
	 */
	public static final String USER_AGREEMENT = "register_userAgreement";
	/**
	 * 注册验证EMAIL标题
	 */
	public static final String VERIFY_EMAIL_SUBJECT = "register_verifyEmailSubject";
	/**
	 * 注册验证EMAIL正文
	 */
	public static final String VERIFY_EMAIL_TEXT = "register_verifyEmailText";
	/**
	 * 找回密码EMAIL标题
	 */
	public static final String PASSWORD_EMAIL_SUBJECT = "register_passwordEmailSubject";
	/**
	 * 找回密码EMAIL正文
	 */
	public static final String PASSWORD_EMAIL_TEXT = "register_passwordEmailText";

	// /**
	// * 邮箱激活EMAIL标题
	// */
	// public static final String ACTIVE_EMAIL_SUBJECT =
	// "register_activeEmailSubject";
	// /**
	// * 邮箱激活EMAIL正文
	// */
	// public static final String ACTIVE_EMAIL_TEXT =
	// "register_activeEmailText";

	// /**
	// * 欢迎信息EMAIL标题
	// */
	// public static final String WELCOME_EMAIL_SUBJECT =
	// "register_welcomeEmailSubject";
	// /**
	// * 欢迎信息EMAIL正文
	// */
	// public static final String WELCOME_EMAIL_TEXT =
	// "register_welcomeEmailText";

	private Map<String, String> customs;

	public GlobalRegister() {
	}

	public GlobalRegister(Map<String, String> customs) {
		this.customs = customs;
	}

	public static String replaceVerifyEmail(String text, String username,
			String sitename, String url) {
		text = StringUtils.replace(text, "${username}", username);
		text = StringUtils.replace(text, "${sitename}", sitename);
		text = StringUtils.replace(text, "${url}", url);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		text = StringUtils.replace(text, "${date}", df.format(new Date()));
		return text;
	}

	public static String replacePasswordEmail(String text, String username,
			String sitename, String url) {
		text = StringUtils.replace(text, "${username}", username);
		text = StringUtils.replace(text, "${sitename}", sitename);
		text = StringUtils.replace(text, "${url}", url);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		text = StringUtils.replace(text, "${date}", df.format(new Date()));
		return text;
	}

	public Integer getMode() {
		String mode = getCustoms().get(MODE);
		if (StringUtils.isNotBlank(mode)) {
			return Integer.parseInt(mode);
		} else {
			return MODE_ON;
		}
	}

	public void setMode(Integer mode) {
		if (mode != null) {
			getCustoms().put(MODE, mode.toString());
		} else {
			getCustoms().remove(MODE);
		}
	}

	public Integer getGroupId() {
		String groupId = getCustoms().get(GROUP_ID);
		if (StringUtils.isNotBlank(groupId)) {
			return Integer.parseInt(groupId);
		} else {
			return 1;
		}
	}

	public void setGroupId(Integer groupId) {
		if (groupId != null) {
			getCustoms().put(GROUP_ID, groupId.toString());
		} else {
			getCustoms().remove(GROUP_ID);
		}
	}

	public Integer getOrgId() {
		String orgId = getCustoms().get(ORG_ID);
		if (StringUtils.isNotBlank(orgId)) {
			return Integer.parseInt(orgId);
		} else {
			return 1;
		}
	}

	public void setOrgId(Integer orgId) {
		if (orgId != null) {
			getCustoms().put(ORG_ID, orgId.toString());
		} else {
			getCustoms().remove(ORG_ID);
		}
	}

	public Integer getMinLength() {
		String minLength = getCustoms().get(MIN_LENGTH);
		if (StringUtils.isNotBlank(minLength)) {
			return Integer.parseInt(minLength);
		} else {
			return 3;
		}
	}

	public void setMinLength(Integer minLength) {
		if (minLength != null) {
			getCustoms().put(MIN_LENGTH, minLength.toString());
		} else {
			getCustoms().remove(MIN_LENGTH);
		}
	}

	public Integer getMaxLength() {
		String maxLength = getCustoms().get(MAX_LENGTH);
		if (StringUtils.isNotBlank(maxLength)) {
			return Integer.parseInt(maxLength);
		} else {
			return 20;
		}
	}

	public void setMaxLength(Integer maxLength) {
		if (maxLength != null) {
			getCustoms().put(MAX_LENGTH, maxLength.toString());
		} else {
			getCustoms().remove(MAX_LENGTH);
		}
	}

	public String getValidCharacter() {
		String validCharacter = getCustoms().get(VALID_CHARACTER);
		if (StringUtils.isNotBlank(validCharacter)) {
			return validCharacter;
		} else {
			return "^[0-9a-zA-Z\\u4e00-\\u9fa5\\.\\-@_]+$";
		}
	}

	public void setValidCharacter(String validCharacter) {
		if (StringUtils.isNotBlank(validCharacter)) {
			getCustoms().put(VALID_CHARACTER, validCharacter);
		} else {
			getCustoms().remove(VALID_CHARACTER);
		}
	}

	public String getReservedWords() {
		return getCustoms().get(RESERVED_WORDS);
	}

	public void setReservedWords(String reservedWords) {
		if (StringUtils.isNotBlank(reservedWords)) {
			getCustoms().put(RESERVED_WORDS, reservedWords);
		} else {
			getCustoms().remove(RESERVED_WORDS);
		}
	}

	public Integer getVerifyMode() {
		String verifyMode = getCustoms().get(VERIFY_MODE);
		if (StringUtils.isNotBlank(verifyMode)) {
			return Integer.parseInt(verifyMode);
		} else {
			return VERIFY_MODE_NONE;
		}
	}

	public void setVerifyMode(Integer verifyMode) {
		if (verifyMode != null) {
			getCustoms().put(VERIFY_MODE, verifyMode.toString());
		} else {
			getCustoms().remove(VERIFY_MODE);
		}
	}

	public String getUserAgreement() {
		return getCustoms().get(USER_AGREEMENT);
	}

	public void setUserAgreement(String userAgreement) {
		if (StringUtils.isNotBlank(userAgreement)) {
			getCustoms().put(USER_AGREEMENT, userAgreement);
		} else {
			getCustoms().remove(USER_AGREEMENT);
		}
	}

	public String getVerifyEmailSubject() {
		String subject = getCustoms().get(VERIFY_EMAIL_SUBJECT);
		if (StringUtils.isNotBlank(subject)) {
			return subject;
		} else {
			return "帐号激活邮件";
		}
	}

	public void setVerifyEmailSubject(String verifyEmailSubject) {
		if (StringUtils.isNotBlank(verifyEmailSubject)) {
			getCustoms().put(VERIFY_EMAIL_SUBJECT, verifyEmailSubject);
		} else {
			getCustoms().remove(VERIFY_EMAIL_SUBJECT);
		}
	}

	public String getVerifyEmailText() {
		String text = getCustoms().get(VERIFY_EMAIL_TEXT);
		if (StringUtils.isNotBlank(text)) {
			return text;
		} else {
			return "亲爱的${username}:\n  请复制以下链接到浏览器中打开，以便激活您的帐号。\n  ${url}\n\n${sitename}";
		}
	}

	public void setVerifyEmailText(String verifyEmailText) {
		if (StringUtils.isNotBlank(verifyEmailText)) {
			getCustoms().put(VERIFY_EMAIL_TEXT, verifyEmailText);
		} else {
			getCustoms().remove(VERIFY_EMAIL_TEXT);
		}
	}

	public String getPasswordEmailSubject() {
		String subject = getCustoms().get(PASSWORD_EMAIL_SUBJECT);
		if (StringUtils.isNotBlank(subject)) {
			return subject;
		} else {
			return "找回密码通知";
		}
	}

	public void setPasswordEmailSubject(String passwordEmailSubject) {
		if (StringUtils.isNotBlank(passwordEmailSubject)) {
			getCustoms().put(PASSWORD_EMAIL_SUBJECT, passwordEmailSubject);
		} else {
			getCustoms().remove(PASSWORD_EMAIL_SUBJECT);
		}
	}

	public String getPasswordEmailText() {
		String text = getCustoms().get(PASSWORD_EMAIL_TEXT);
		if (StringUtils.isNotBlank(text)) {
			return text;
		} else {
			return "亲爱的${username}:\n  请复制以下链接到浏览器中打开，进入密码修改页面。\n  ${url}\n\n${sitename}";
		}
	}

	public void setPasswordEmailText(String passwordEmailText) {
		if (StringUtils.isNotBlank(passwordEmailText)) {
			getCustoms().put(PASSWORD_EMAIL_TEXT, passwordEmailText);
		} else {
			getCustoms().remove(PASSWORD_EMAIL_TEXT);
		}
	}

	// public String getWelcomeEmailTitle() {
	// return getCustoms().get(WELCOME_EMAIL_TITLE);
	// }
	//
	// public void setWelcomeEmailTitle(String welcomeEmailTitle) {
	// if (StringUtils.isNotBlank(welcomeEmailTitle)) {
	// getCustoms().put(WELCOME_EMAIL_TITLE, welcomeEmailTitle);
	// } else {
	// getCustoms().remove(WELCOME_EMAIL_TITLE);
	// }
	// }
	//
	// public String getWelcomeEmailText() {
	// return getCustoms().get(WELCOME_EMAIL_TEXT);
	// }
	//
	// public void setWelcomeEmailText(String welcomeEmailText) {
	// if (StringUtils.isNotBlank(welcomeEmailText)) {
	// getCustoms().put(WELCOME_EMAIL_TEXT, welcomeEmailText);
	// } else {
	// getCustoms().remove(WELCOME_EMAIL_TEXT);
	// }
	// }

	public Map<String, String> getCustoms() {
		if (customs == null) {
			customs = new HashMap<String, String>();
		}
		return customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	public String getPrefix() {
		return PREFIX;
	}
}
