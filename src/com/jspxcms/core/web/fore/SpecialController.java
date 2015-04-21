package com.jspxcms.core.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.Special;
import com.jspxcms.core.service.SpecialService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;

/**
 * SpecialController
 * 
 * @author liufang
 * 
 */
@Controller
public class SpecialController {
	public static final String TEMPLATE = "sys_special.html";

	@RequestMapping(value = "/special/{id:[0-9]+}.jspx")
	public String special(@PathVariable Integer id, Integer page,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Special special = service.get(id);
		// Site site = special.getSite();
		modelMap.addAttribute("special", special);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TEMPLATE);
	}

	@Autowired
	private SpecialService service;
}
