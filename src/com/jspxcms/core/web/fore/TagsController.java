package com.jspxcms.core.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.Tag;
import com.jspxcms.core.service.TagService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;

/**
 * TagsController
 * 
 * @author liufang
 * 
 */
@Controller
public class TagsController {
	public static final String TAGS_TEMPLATE = "sys_tags.html";
	public static final String TAG_TEMPLATE = "sys_tag.html";

	@RequestMapping(value = "/tags.jspx")
	public String tags(Integer page, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TAGS_TEMPLATE);
	}

	@RequestMapping(value = "/tag/{id}.jspx")
	public String tag(@PathVariable Integer id, Integer page,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Tag tag = service.get(id);
		return tag(tag, page, request, modelMap);
	}

	@RequestMapping(value = "/tagname/{name}.jspx")
	public String tag(@PathVariable String name, Integer page,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Tag tag = service.findByName(site.getId(), name);
		return tag(tag, page, request, modelMap);
	}

	private String tag(Tag tag, Integer page, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		modelMap.addAttribute("tag", tag);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TAG_TEMPLATE);
	}

	@Autowired
	private TagService service;
}
