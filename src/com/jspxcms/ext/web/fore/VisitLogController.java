package com.jspxcms.ext.web.fore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.support.Context;
import com.jspxcms.ext.service.VisitLogService;

/**
 * VisitLogController 访问日志控制器
 * 
 * @author liufang
 * 
 */
@Controller
public class VisitLogController {

	@RequestMapping(value = "/visit_log.jspx")
	public void visitLog(Integer siteId, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site;
		if (siteId != null) {
			site = siteService.get(siteId);
			Context.setCurrentSite(request, site);
			Context.setCurrentSite(site);
		} else {
			site = Context.getCurrentSite(request);
		}
		String url = Servlets.getParameter(request, "url");
		String referrer = Servlets.getParameter(request, "referrer");
		// 长度不超过150个字符
		url = StringUtils.substring(url, 0, 255);
		referrer = StringUtils.substring(referrer, 0, 255);
		// String userAgent = request.getHeader("user-agent");
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		service.save(url, referrer, ip, cookie, site);
	}

	@Autowired
	private SiteService siteService;
	@Autowired
	private VisitLogService service;
}
