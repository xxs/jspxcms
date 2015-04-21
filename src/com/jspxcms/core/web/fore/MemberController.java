package com.jspxcms.core.web.fore;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jspxcms.common.security.CredentialsDigest;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserDetail;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;

/**
 * MemberController
 * 
 * @author liufang
 * 
 */
@Controller
public class MemberController {
	public static final String SPACE_TEMPLATE = "sys_member_space.html";
	public static final String MY_TEMPLATE = "sys_member_my.html";
	public static final String PROFILE_TEMPLATE = "sys_member_profile.html";
	public static final String PASSWORD_TEMPLATE = "sys_member_password.html";
	public static final String EMAIL_TEMPLATE = "sys_member_email.html";

	/**
	 * 会员首页
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/space/{id}.jspx")
	public String space(@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		User targetUser = userService.get(id);
		if (!Validations.exist(targetUser, messages, "User", id)) {
			return resp.notFound();
		}

		Site site = Context.getCurrentSite(request);
		modelMap.addAttribute("targetUser", targetUser);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(SPACE_TEMPLATE);
	}

	@RequestMapping(value = "/my.jspx")
	public String my(HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(MY_TEMPLATE);
	}

	@RequestMapping(value = "/my/profile.jspx")
	public String profileForm(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(PROFILE_TEMPLATE);
	}

	@RequestMapping(value = "/my/profile.jspx", method = RequestMethod.POST)
	public String profileSubmit(String gender, Date birthDate, String bio,
			String comeFrom, String qq, String msn, String weixin,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		User user = Context.getCurrentUser(request);
		user.setGender(gender);
		user.setBirthDate(birthDate);
		UserDetail detail = user.getDetail();
		detail.setBio(bio);
		detail.setComeFrom(comeFrom);
		detail.setQq(qq);
		detail.setMsn(msn);
		detail.setQq(qq);
		userService.update(user, detail);
		return resp.post();
	}

	@RequestMapping(value = "/my/password.jspx")
	public String passwordForm(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(PASSWORD_TEMPLATE);
	}

	@RequestMapping(value = "/my/password.jspx", method = RequestMethod.POST)
	public String passwordSubmit(String password, String rawPassword,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		User user = Context.getCurrentUser(request);
		if (!credentialsDigest.matches(user.getPassword(), password,
				user.getSaltBytes())) {
			return resp.post(501, "member.passwordError");
		}
		userService.updatePassword(user.getId(), rawPassword);
		return resp.post();
	}

	@RequestMapping(value = "/my/email.jspx")
	public String emailForm(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(EMAIL_TEMPLATE);
	}

	/**
	 * 修改邮箱
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/my/email.jspx", method = RequestMethod.POST)
	public String emailSubmit(String password, String email,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		// TODO 修改邮箱后需重新激活才能生效
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(email, messages, "email")) {
			return resp.post(401);
		}
		if (!Validations.email(email, messages, "email")) {
			return resp.post(402);
		}
		User user = Context.getCurrentUser(request);
		if (!credentialsDigest.matches(user.getPassword(), password,
				user.getSaltBytes())) {
			return resp.post(501, "member.passwordError");
		}
		userService.updateEmail(user.getId(), email);
		return resp.post();
	}

	@Autowired
	private CredentialsDigest credentialsDigest;
	@Autowired
	private UserService userService;
}
