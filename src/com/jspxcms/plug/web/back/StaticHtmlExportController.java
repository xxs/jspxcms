package com.jspxcms.plug.web.back;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.service.SiteService;
/**
 * 静态资源导出
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/plug/she/")
public class StaticHtmlExportController {
	private static final Logger logger = LoggerFactory
			.getLogger(StaticHtmlExportController.class);

	@RequestMapping("list.do")
	public String list(Model modelMap) {
		List<Site> list = service.findList();
		modelMap.addAttribute("list", list);
		return "plug/she/list";
	}
	@RequestMapping("edit.do")
	public String edit(
			Integer id,
			Integer position,
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site bean = service.get(id);
		modelMap.addAttribute("bean", bean);
		return "plug/she/form";
	}

	@Autowired
	private SiteService service;
}
