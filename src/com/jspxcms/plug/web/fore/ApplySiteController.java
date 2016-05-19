package com.jspxcms.plug.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.plug.domain.ApplySite;
import com.jspxcms.plug.service.ApplySiteService;

/**
 * ApplySiteController
 * 
 * @author liufang
 * 
 */
@Controller
public class ApplySiteController {
	public static final String TEMPLATE = "plug_apply_site.html";

	@RequestMapping(value = "/apply_site.jspx")
	public String applySiteForm(Integer page, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TEMPLATE);
	}

	@RequestMapping(value = "/apply_site.jspx", method = RequestMethod.POST)
	public String applySiteSubmit(@Valid ApplySite bean, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		Site site = Context.getCurrentSite(request);
		service.save(bean, site.getId());
		return resp.post();
	}

	@Autowired
	private ApplySiteService service;
}
