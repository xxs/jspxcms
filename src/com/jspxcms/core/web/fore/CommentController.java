package com.jspxcms.core.web.fore;

import static com.jspxcms.core.domain.SiteComment.STATUS_DENIED;
import static com.jspxcms.core.domain.SiteComment.STATUS_LOGIN;
import static com.jspxcms.core.domain.SiteComment.STATUS_OFF;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.common.security.Captchas;
import com.jspxcms.common.web.Anchor;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.domain.Comment;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.SiteComment;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.CommentService;
import com.jspxcms.core.service.SensitiveWordService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.Commentable;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.core.support.Siteable;
import com.octo.captcha.service.CaptchaService;

/**
 * CommentController
 * 
 * @author liufang
 * 
 */
@Controller
public class CommentController {
	public static final String TPL_PREFIX = "sys_comment";
	public static final String TPL_SUFFIX = ".html";
	public static final String TEMPLATE = TPL_PREFIX + TPL_SUFFIX;

	@RequestMapping(value = "/comment.jspx")
	public String veiw(String ftype, Integer fid, Integer page,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		if (StringUtils.isBlank(ftype)) {
			ftype = "Info";
		}
		if (fid == null) {
			// TODO
		}
		Object bean = service.getEntity(ftype, fid);
		if (bean == null) {
			// TODO
		}
		Anchor anchor = (Anchor) bean;
		// Site site = ((Siteable) bean).getSite();
		modelMap.addAttribute("anchor", anchor);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TEMPLATE);
	}

	@RequestMapping(value = "/comment_submit.jspx")
	public String submit(String fname, String ftype, Integer fid, String text,
			String captcha, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		User user = Context.getCurrentUser(request);
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (StringUtils.isBlank(fname)) {
			fname = "com.jspxcms.core.domain.InfoComment";
		}
		if (StringUtils.isBlank(ftype)) {
			ftype = Info.COMMENT_TYPE;
		}
		if (!Validations.notNull(fid, messages, "id")) {
			return resp.post(401);
		}
		Object bean = service.getEntity(ftype, fid);
		if (!Validations.exist(bean, messages, fname, fid)) {
			return resp.post(451);
		}
		if (bean instanceof Commentable) {
			Collection<MemberGroup> groups = Context.getCurrentGroups(request);
			int commentStatus = ((Commentable) bean).getCommentStatus(user,
					groups);
			if (commentStatus == STATUS_OFF) {
				return resp.post(503, "comment.off");
			} else if (commentStatus == STATUS_LOGIN) {
				return resp.post(504, "comment.needLogin");
			} else if (commentStatus == STATUS_DENIED) {
				return resp.post(501, "comment.noPermission");
			}
		} else {
			// 不可评论对象
			return resp.post(502, "comment.notAllow");
		}
		Site site = ((Siteable) bean).getSite();
		SiteComment conf = new SiteComment(site.getCustoms());
		if (!Validations.notEmpty(text, conf.getMaxLength(), messages, "text")) {
			resp.post(401);
		}
		if (conf.isNeedCaptcha(user)) {
			if (!Captchas.isValid(captchaService, request, captcha)) {
				return resp.post(100, "error.captcha");
			}
		}

		text = sensitiveWordService.replace(text);
		Comment comment = (Comment) Class.forName(fname).newInstance();
		comment.setFid(fid);
		comment.setText(text);
		comment.setIp(Servlets.getRemoteAddr(request));
		if (conf.isAudit(user)) {
			comment.setStatus(Comment.AUDITED);
			resp.setStatus(0);
		} else {
			comment.setStatus(Comment.SAVED);
			resp.setStatus(1);
		}
		if (user == null) {
			user = userService.getAnonymous();
		}
		service.save(comment, user.getId(), site.getId());
		return resp.post();
	}

	@RequestMapping(value = "/comment_list.jspx")
	public String list(String ftype, Integer fid, Integer page,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		if (StringUtils.isBlank(ftype)) {
			ftype = "Info";
		}
		if (fid == null) {
			// TODO
		}
		Object bean = service.getEntity(ftype, fid);
		if (bean == null) {
			// TODO
		}
		Anchor anchor = (Anchor) bean;
		// Site site = ((Siteable) bean).getSite();
		String tpl = Servlets.getParameter(request, "tpl");
		if (StringUtils.isBlank(tpl)) {
			tpl = "_list";
		}
		modelMap.addAttribute("anchor", anchor);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TPL_PREFIX + tpl + TPL_SUFFIX);
	}

	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService service;

}
