package com.jspxcms.plug.web.back;

import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPERATION_SUCCESS;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.web.PathResolver;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.html.HtmlGenerator;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.WebFile;
/**
 * 静态资源导出
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/plug/she/")
public class StaticHtmlExportController {

	public static final String SHE_BACKUP_PATH = "/WEB-INF/she_backup";
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
		String filesBasePath = bean.getFilesBasePath("");
		File filesBaseFile = new File(pathResolver.getPath(filesBasePath));
		File[] themeFiles = filesBaseFile.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		List<String> themeList = new ArrayList<String>();
		if (ArrayUtils.isNotEmpty(themeFiles)) {
			for (File themeFile : themeFiles) {
				themeList.add(themeFile.getName());
			}
		}
		modelMap.addAttribute("themeList", themeList);
		modelMap.addAttribute("bean", bean);
		//显示之前备份列表
		String realPath = pathResolver.getPath(SHE_BACKUP_PATH);
		File parent = new File(realPath);
		WebFile parentWebFile = new WebFile(parent, parent.getAbsolutePath(),
				request.getContextPath());
		List<WebFile> list = parentWebFile.listFiles();
		modelMap.addAttribute("list", list);
		
		return "plug/she/form";
	}

	@RequestMapping("make_all_site.do")
	public String makeAllSite(HttpServletRequest request, RedirectAttributes ra) {
		Site site = Context.getCurrentSite(request);
		Integer userId = Context.getCurrentUserId(request);
		htmlGenerator.makeAllSite(site, userId);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:edit.do";
	}
	
	@Autowired
	private SiteService service;
	@Autowired
	private HtmlGenerator htmlGenerator;
	@Autowired
	private PathResolver pathResolver;
}
