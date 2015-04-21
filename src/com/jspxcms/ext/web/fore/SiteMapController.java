package com.jspxcms.ext.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;

/**
 * SiteMapController
 * 
 * @author yangxing
 * 
 */
@Controller
public class SiteMapController {
	public static final String TEMPLATE = "sys_sitemap.html";

	@RequestMapping(value = "/sitemap.jspx")
	public String list(HttpServletRequest request,HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(TEMPLATE);
	}
}
