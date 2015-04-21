package com.jspxcms.ext.web.fore;

import static com.jspxcms.ext.domain.SiteGuestbook.MODE_OFF;
import static com.jspxcms.ext.domain.SiteGuestbook.MODE_USER;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jspxcms.common.security.Captchas;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.SensitiveWordService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.ext.domain.Guestbook;
import com.jspxcms.ext.domain.SiteGuestbook;
import com.jspxcms.ext.service.GuestbookService;
import com.jspxcms.ext.service.GuestbookTypeService;
import com.octo.captcha.service.CaptchaService;

/**
 * GuestbookController
 * 
 * @author yangxing
 * 
 */
@Controller
public class GuestbookController {
	public static final String TEMPLATE = "sys_guestbook.html";

	@RequestMapping(value = "/guestbook.jspx")
	public String list(Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		Site site = Context.getCurrentSite(request);
		SiteGuestbook sg = new SiteGuestbook(site.getCustoms());
		// 未开启
		if (sg.getMode() == MODE_OFF) {
			return resp.warning("guestbook.off");
		}
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TEMPLATE);
	}

	@RequestMapping(value = "/guestbook.jspx", method = RequestMethod.POST)
	public String submit(Integer typeId, String text, String title,
			String username, Boolean gender, String phone, String mobile,
			String qq, String email, String captcha,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		Site site = Context.getCurrentSite(request);
		SiteGuestbook sg = new SiteGuestbook(site.getCustoms());
		String result = validateSubmit(request, resp, sg, captcha, typeId,
				title, text, username, gender, phone, mobile, qq, email);
		if (resp.hasErrors()) {
			return result;
		}
		title = sensitiveWordService.replace(title);
		text = sensitiveWordService.replace(text);
		User user = Context.getCurrentUser(request);
		Guestbook bean = new Guestbook();
		if (sg.isAudit(user)) {
			bean.setStatus(Guestbook.AUDITED);
			resp.setStatus(0);
		} else {
			bean.setStatus(Guestbook.UNAUDIT);
			resp.setStatus(1);
		}
		if (user == null) {
			user = userService.getAnonymous();
		}
		bean.setUsername(username);
		bean.setGender(gender);
		bean.setPhone(phone);
		bean.setMobile(mobile);
		bean.setQq(qq);
		bean.setEmail(email);
		bean.setTitle(title);
		bean.setText(text);
		String ip = Servlets.getRemoteAddr(request);
		service.save(bean, user.getId(), typeId, ip, site.getId());
		return resp.post();
	}

	private String validateSubmit(HttpServletRequest request, Response resp,
			SiteGuestbook sg, String captcha, Integer typeId, String title,
			String text, String username, Boolean gender, String phone,
			String mobile, String qq, String email) {
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(text, sg.getMaxLength(), messages, "text")) {
			resp.post(401);
		}
		if (typeId != null) {
			if (!Validations.exist(guestbookTypeService.get(typeId), messages,
					"GuestbookType", typeId)) {
				resp.post(451);
			}
		}
		User user = Context.getCurrentUser(request);
		if (sg.getMode() == MODE_OFF) {
			return resp.post(501, "guestbook.off");
		} else if (sg.getMode() == MODE_USER && user == null) {
			return resp.post(502, "guestbook.needLogin");
		}

		if (sg.isNeedCaptcha(user)) {
			if (!Captchas.isValid(captchaService, request, captcha)) {
				return resp.post(100, "error.captcha");
			}
		}
		return null;
	}

	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Autowired
	private UserService userService;
	@Autowired
	private GuestbookTypeService guestbookTypeService;
	@Autowired
	private GuestbookService service;
}
