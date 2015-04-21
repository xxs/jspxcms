package com.jspxcms.core.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.SpecialCategory;
import com.jspxcms.core.service.SpecialCategoryService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;

/**
 * SpecialsController
 * 
 * @author liufang
 * 
 */
@Controller
public class SpecialsController {
	public static final String TEMPLATE = "sys_specials.html";

	@RequestMapping(value = "/specials.jspx")
	public String index(Integer page, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TEMPLATE);
	}

	@RequestMapping(value = "/specials/{categoryId:[0-9]+}.jspx")
	public String specials(@PathVariable Integer categoryId, Integer page,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		SpecialCategory category = service.get(categoryId);
		// Site site = category.getSite();
		modelMap.addAttribute("category", category);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TEMPLATE);
	}

	@Autowired
	private SpecialCategoryService service;
}
