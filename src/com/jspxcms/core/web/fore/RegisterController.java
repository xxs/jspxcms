package com.jspxcms.core.web.fore;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jspxcms.common.security.Captchas;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.domain.GlobalMail;
import com.jspxcms.core.domain.GlobalRegister;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.MemberGroupService;
import com.jspxcms.core.service.OrgService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.octo.captcha.service.CaptchaService;

/**
 * RegisterController
 * 
 * @author liufang
 * 
 */
@Controller
public class RegisterController {
	/**
	 * 注册模板
	 */
	public static final String REGISTER_TEMPLATE = "sys_member_register.html";
	/**
	 * 注册结果模板。提示会员注册成功，或提示会员接收验证邮件。
	 */
	public static final String REGISTER_MESSAGE_TEMPLATE = "sys_member_register_message.html";
	/**
	 * 验证会员模板
	 */
	public static final String VERIFY_MEMBER_TEMPLATE = "sys_member_verify_member.html";
	/**
	 * 忘记密码模板
	 */
	public static final String FORGOT_PASSWORD_TEMPLATE = "sys_member_forgot_password.html";
	/**
	 * 找回密码模板
	 */
	public static final String RETRIEVE_PASSWORD_TEMPLATE = "sys_member_retrieve_password.html";

	@RequestMapping(value = "/register.jspx")
	public String registerForm(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		Site site = Context.getCurrentSite(request);
		GlobalRegister registerConf = site.getGlobal().getRegister();
		if (registerConf.getMode() == GlobalRegister.MODE_OFF) {
			return resp.warning("register.off");
		}
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(REGISTER_TEMPLATE);
	}

	@RequestMapping(value = "/register.jspx", method = RequestMethod.POST)
	public String registerSubmit(String captcha, String username,
			String password, String email, String gender, Date birthDate,
			String bio, String comeFrom, String qq, String msn, String weixin,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		Site site = Context.getCurrentSite(request);
		GlobalRegister reg = site.getGlobal().getRegister();
		String result = validateRegisterSubmit(request, resp, reg, captcha,
				username, password, email, gender);
		if (resp.hasErrors()) {
			return result;
		}

		int verifyMode = reg.getVerifyMode();
		String ip = Servlets.getRemoteAddr(request);
		int groupId = reg.getGroupId();
		int orgId = reg.getOrgId();
		int status = verifyMode == GlobalRegister.VERIFY_MODE_NONE ? User.NORMAL
				: User.UNVERIFIED;
		User user = userService.register(ip, groupId, orgId, status, username,
				password, email, gender, birthDate, bio, comeFrom, qq, msn,
				weixin);
		if (verifyMode == GlobalRegister.VERIFY_MODE_EMAIL) {
			GlobalMail mail = site.getGlobal().getMail();
			String subject = reg.getVerifyEmailSubject();
			String text = reg.getVerifyEmailText();
			userService.sendVerifyEmail(site, user, mail, subject, text);
		}
		resp.addData("verifyMode", verifyMode);
		resp.addData("id", user.getId());
		resp.addData("username", user.getUsername());
		resp.addData("email", user.getEmail());
		return resp.post();
	}

	@RequestMapping(value = "/register_message.jspx")
	public String registerMessage(String email, Integer verifyMode,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		Site site = Context.getCurrentSite(request);
		GlobalRegister reg = site.getGlobal().getRegister();
		String username = Servlets.getParameter(request, "username");
		String result = validateRegisterMessage(request, resp, reg, username,
				email, verifyMode);
		if (resp.hasErrors()) {
			return result;
		}

		User registerUser = userService.findByUsername(username);
		modelMap.addAttribute("registerUser", registerUser);
		modelMap.addAttribute("verifyMode", verifyMode);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(REGISTER_MESSAGE_TEMPLATE);
	}

	@RequestMapping(value = "/verify_member.jspx")
	public String verifyMember(String key, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		Site site = Context.getCurrentSite(request);
		if (!Validations.notEmpty(key, messages, "key")) {
			return resp.badRequest();
		}
		User keyUser = userService.findByValidation(
				Constants.VERIFY_MEMBER_TYPE, key);
		userService.verifyMember(keyUser);
		modelMap.addAttribute("keyUser", keyUser);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(VERIFY_MEMBER_TEMPLATE);
	}

	@RequestMapping(value = "/forgot_password.jspx")
	public String forgotPasswordForm(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(FORGOT_PASSWORD_TEMPLATE);
	}

	@RequestMapping(value = "/forgot_password.jspx", method = RequestMethod.POST)
	public String forgotPasswordSubmit(String username, String email,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		String result = validateForgotPasswordSubmit(request, resp, username,
				email);
		if (resp.hasErrors()) {
			return result;
		}

		Site site = Context.getCurrentSite(request);
		User forgotUser = userService.findByUsername(username);
		GlobalRegister reg = site.getGlobal().getRegister();
		GlobalMail mail = site.getGlobal().getMail();
		String subject = reg.getPasswordEmailSubject();
		String text = reg.getPasswordEmailText();
		userService.sendPasswordEmail(site, forgotUser, mail, subject, text);
		resp.addData("username", username);
		resp.addData("email", email);
		return resp.post();
	}

	@RequestMapping(value = "/retrieve_password.jspx")
	public String retrievePasswordForm(String key, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(key, messages, "key")) {
			return resp.badRequest();
		}

		Site site = Context.getCurrentSite(request);
		User retrieveUser = userService.findByValidation(
				Constants.RETRIEVE_PASSWORD_TYPE, key);
		modelMap.addAttribute("retrieveUser", retrieveUser);
		modelMap.addAttribute("key", key);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(RETRIEVE_PASSWORD_TEMPLATE);
	}

	@RequestMapping(value = "/retrieve_password.jspx", method = RequestMethod.POST)
	public String retrievePasswordSubmit(String key, String password,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(key, messages, "key")) {
			return resp.post(401);
		}
		if (!Validations.notNull(password, messages, "password")) {
			return resp.post(402);
		}

		User retrieveUser = userService.findByValidation(
				Constants.RETRIEVE_PASSWORD_TYPE, key);
		if (retrieveUser == null) {
			return resp.post(501, "retrievePassword.invalidKey");
		}
		userService.passwordChange(retrieveUser, password);
		return resp.post();
	}

	@ResponseBody
	@RequestMapping(value = "/check_username.jspx")
	public String checkUsername(String username, String original,
			HttpServletRequest request, HttpServletResponse response) {
		Servlets.setNoCacheHeader(response);
		if (StringUtils.isBlank(username)) {
			return "true";
		}
		if (StringUtils.equals(username, original)) {
			return "true";
		}
		// 检查数据库是否重名
		boolean exist = userService.usernameExist(username);
		if (!exist) {
			return "true";
		} else {
			return "false";
		}
	}

	private String validateRegisterSubmit(HttpServletRequest request,
			Response resp, GlobalRegister reg, String captcha, String username,
			String password, String email, String gender) {
		List<String> messages = resp.getMessages();
		if (!Captchas.isValid(captchaService, request, captcha)) {
			return resp.post(100, "error.captcha");
		}
		if (reg.getMode() == GlobalRegister.MODE_OFF) {
			return resp.post(501, "register.off");
		}
		Integer groupId = reg.getGroupId();
		if (groupService.get(groupId) == null) {
			return resp.post(502, "register.groupNotSet");
		}
		Integer orgId = reg.getOrgId();
		if (orgService.get(orgId) == null) {
			return resp.post(503, "register.orgNotSet");
		}

		if (!Validations.notEmpty(username, messages, "username")) {
			return resp.post(401);
		}
		if (!Validations.length(username, reg.getMinLength(),
				reg.getMaxLength(), messages, "username")) {
			return resp.post(402);
		}
		if (!Validations.pattern(username, reg.getValidCharacter(), messages,
				"username")) {
			return resp.post(403);
		}
		if (!Validations.notEmpty(password, messages, "password")) {
			return resp.post(404);
		}
		if (reg.getVerifyMode() == GlobalRegister.VERIFY_MODE_EMAIL
				&& !Validations.notEmpty(email, messages, "email")) {
			return resp.post(405);
		}
		if (!Validations.email(email, messages, "email")) {
			return resp.post(406);
		}
		if (!Validations.pattern(gender, "[F,M]", messages, "gender")) {
			return resp.post(407);
		}
		return null;
	}

	private String validateRegisterMessage(HttpServletRequest request,
			Response resp, GlobalRegister reg, String username, String email,
			Integer verifyMode) {
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(username, messages, "username")) {
			return resp.badRequest();
		}
		if (!Validations.notEmpty(email, messages, "email")) {
			return resp.badRequest();
		}
		if (!Validations.notNull(verifyMode, messages, "verifyMode")) {
			return resp.badRequest();
		}
		User registerUser = userService.findByUsername(username);
		if (!Validations.exist(registerUser)) {
			return resp.notFound();
		}
		if (!registerUser.getEmail().equals(email)) {
			return resp.notFound("email not found: " + email);
		}
		if (reg.getMode() == GlobalRegister.MODE_OFF) {
			return resp.warning("register.off");
		}
		return null;
	}

	private String validateForgotPasswordSubmit(HttpServletRequest request,
			Response resp, String username, String email) {
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(username, messages, "username")) {
			return resp.post(401);
		}
		if (!Validations.notEmpty(email, messages, "email")) {
			return resp.post(402);
		}

		User forgotUser = userService.findByUsername(username);
		if (!Validations.exist(forgotUser)) {
			return resp.post(501, "forgotPassword.usernameNotExist",
					new String[] { username });
		}
		if (!forgotUser.getEmail().equals(email)) {
			return resp.post(502, "forgotPassword.emailNotMatch");
		}
		return null;
	}

	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private MemberGroupService groupService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private UserService userService;
}
