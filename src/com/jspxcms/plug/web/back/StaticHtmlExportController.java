package com.jspxcms.plug.web.back;

import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPERATION_SUCCESS;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.util.AntZipUtils;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.html.HtmlGenerator;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.WebFile;
import com.jspxcms.core.web.back.WebFileController;
/**
 * 静态资源导出
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/plug/she/")
public class StaticHtmlExportController {

	private static final Logger logger = LoggerFactory
			.getLogger(WebFileController.class);
	
	@RequestMapping("list.do")
	public String list(HttpServletRequest request,Model modelMap) {
		
		List<Site> siteList = service.findList();
		modelMap.addAttribute("siteList", siteList);
		
		//显示之前备份列表
				String realPath = pathResolver.getPath(Constants.SHE_BACKUP_PATH);
				File parent = new File(realPath);
				WebFile parentWebFile = new WebFile(parent, parent.getAbsolutePath(),
						request.getContextPath());
				List<WebFile> list = parentWebFile.listFiles();
				modelMap.addAttribute("list", list);
		return "plug/she/list";
	}
	@RequestMapping("edit.do")
	public String edit(
			Integer id,
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
		return "plug/she/form";
	}

	@RequestMapping("make_all_site.do")
	public String makeAllSite(Integer id,HttpServletRequest request, RedirectAttributes ra) {
		Site site = service.get(id);
		Integer userId = Context.getCurrentUserId(request);
		htmlGenerator.makeAllSite(site, userId);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}
	
	@RequestMapping("delete.do")
	public String delete(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		String parentId = Servlets.getParameter(request, "parentId");
		String[] ids = Servlets.getParameterValues(request, "ids");
		if (ArrayUtils.isNotEmpty(ids)) {
			for (int i = 0, len = ids.length; i < len; i++) {
				File f = new File(pathResolver.getPath(Constants.SHE_BACKUP_PATH+"//"+ids[i]));
				if (!hasPermission(f, site)) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return null;
				}
				boolean isSuccess = FileUtils.deleteQuietly(f);
				if (isSuccess) {
					logger.info("delete file success, name={}.",
							f.getAbsolutePath());
				} else {
					logger.info("delete file failure, name={}.",
							f.getAbsolutePath());
				}
			}
		}
		ra.addAttribute("parentId", parentId);
		ra.addFlashAttribute("refreshLeft", true);
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}
	
	private boolean hasPermission(File file, Site site) throws IOException {
		String realPath = file.getCanonicalPath();
		String baseRealPath = pathResolver.getPath("");
		// 去除最后一个'/'
		baseRealPath = baseRealPath.substring(0, baseRealPath.length() - 1);
		String templateRealPath = pathResolver.getPath(ForeContext.FILES_PATH
				+ "/" + site.getId());
		// 不能访问应用以外的文件
		if (!StringUtils.startsWithIgnoreCase(realPath, baseRealPath)) {
			return false;
		}
		// 在模板路径下的文件可以访问
		if (StringUtils.startsWithIgnoreCase(realPath, templateRealPath)) {
			return true;
		}
		return true;
	}
	@RequestMapping("zip.do")
	public String zip(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) throws IOException {
		Site site = Context.getCurrentSite(request);
		String parentId = Servlets.getParameter(request, "parentId");
		String[] ids = Servlets.getParameterValues(request, "ids");
		File[] files = new File[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			files[i] = new File(pathResolver.getPath(Constants.SHE_BACKUP_PATH+"//"+ids[i]));
			if (!hasPermission(files[i], site)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
		}
		AntZipUtils.zip(files);
		ra.addAttribute("parentId", parentId);
		ra.addFlashAttribute("refreshLeft", true);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}

	@RequestMapping("zip_download.do")
	public void zipDownload(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		String[] ids = Servlets.getParameterValues(request, "ids");
		File[] files = new File[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			files[i] = new File(pathResolver.getPath(Constants.SHE_BACKUP_PATH+"//"+ids[i]));
			if (!hasPermission(files[i], site)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
		}
		response.setContentType("application/x-download;charset=UTF-8");
		response.addHeader("Content-disposition", "filename=zip-download.zip");
		try {
			AntZipUtils.zip(files, response.getOutputStream());
		} catch (IOException e) {
			logger.error("zip error!", e);
		}
	}

	@RequestMapping("unzip.do")
	public String unzip(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra)
			throws IOException {
		String parentId = Servlets.getParameter(request, "parentId");
		Site site = Context.getCurrentSite(request);
		String base = site.getFilesBasePath("");
		if (StringUtils.isBlank(parentId)) {
			parentId = base;
		}

		String[] ids = Servlets.getParameterValues(request, "ids");
		for (String id : ids) {
			File file = new File(pathResolver.getPath(id));
			if (!hasPermission(file, site)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			if (AntZipUtils.isZipFile(file)) {
				AntZipUtils.unzip(file, file.getParentFile());
			}
		}

		ra.addAttribute("parentId", parentId);
		ra.addFlashAttribute("refreshLeft", true);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}
	
	@Autowired
	private SiteService service;
	@Autowired
	private HtmlGenerator htmlGenerator;
	@Autowired
	private PathResolver pathResolver;
}
