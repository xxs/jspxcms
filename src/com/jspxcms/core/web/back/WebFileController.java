package com.jspxcms.core.web.back;

import static com.jspxcms.core.support.Constants.CREATE;
import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.EDIT;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPERATION_FAILURE;
import static com.jspxcms.core.support.Constants.OPERATION_SUCCESS;
import static com.jspxcms.core.support.Constants.OPRT;
import static com.jspxcms.core.support.Constants.SAVE_SUCCESS;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.util.AntZipUtils;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.WebFile;

/**
 * WebFileController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/web_file")
public class WebFileController {
	private static final Logger logger = LoggerFactory
			.getLogger(WebFileController.class);

	private static FileFilter dirFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	};

	@RequiresPermissions("core:web_file:left")
	@RequestMapping("left.do")
	public String left(Boolean isSiteFile, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		if (isSiteFile == null) {
			isSiteFile = false;
		}
		File rootFile = new File(pathResolver.getPath(""));

		File parentFile;
		if (isSiteFile) {
			parentFile = rootFile;
		} else {
			parentFile = new File(pathResolver.getPath(site
					.getFilesBasePath("")));
			modelMap.addAttribute("theme", site.getTemplateTheme());
		}

		WebFile parentWebFile = new WebFile(parentFile,
				rootFile.getCanonicalPath(), request.getContextPath());
		if (!hasPermission(parentFile, site)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		List<WebFile> list = parentWebFile.listFiles(dirFilter);
		modelMap.addAttribute("isEnableSiteFile", isEnableSiteFile());
		modelMap.addAttribute("isSiteFile", isSiteFile);
		modelMap.addAttribute("parent", parentWebFile);
		modelMap.addAttribute("list", list);
		return "core/web_file/web_file_left";
	}

	@RequiresPermissions("core:web_file:left")
	@RequestMapping("left_tree.do")
	public String leftTree(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		File rootFile = new File(pathResolver.getPath(""));
		File parentFile = rootFile;
		String parentId = Servlets.getParameter(request, "parentId");
		if (StringUtils.isNotBlank(parentId)) {
			parentFile = new File(pathResolver.getPath(parentId));
		}

		WebFile parentWebFile = new WebFile(parentFile,
				rootFile.getCanonicalPath(), request.getContextPath());
		if (!hasPermission(parentFile, site)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		List<WebFile> list = parentWebFile.listFiles(dirFilter);
		modelMap.addAttribute("list", list);
		return "core/web_file/web_file_left_tree";
	}

	@RequiresPermissions("core:web_file:list")
	@RequestMapping("list.do")
	public String list(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap)
			throws IOException {
		String parentId = Servlets.getParameter(request, "parentId");
		Site site = Context.getCurrentSite(request);
		File root = new File(pathResolver.getPath(""));
		String base = site.getFilesBasePath("");
		if (StringUtils.isBlank(parentId)) {
			parentId = base;
		}
		String realPath = pathResolver.getPath(parentId);
		File parent = new File(realPath);
		if (!hasPermission(parent, site)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		WebFile parentWebFile = new WebFile(parent, root.getAbsolutePath(),
				request.getContextPath());
		String searchName = Servlets.getParameter(request, "search_name");
		List<WebFile> list = parentWebFile.listFiles(searchName);
		String sort = request.getParameter("page_sort");
		String sortDir = request.getParameter("page_sort_dir");
		WebFile.sort(list, sort, sortDir);
		if (parentId.length() > 1) {
			WebFile ppWebFile = parentWebFile.getParentFile();
			ppWebFile.setParent(true);
			list.add(0, ppWebFile);
			modelMap.addAttribute("ppId", ppWebFile.getId());
		}
		modelMap.addAttribute("parentId", parentId);
		modelMap.addAttribute("list", list);
		return "core/web_file/web_file_list";
	}

	@RequiresPermissions("core:web_file:create")
	@RequestMapping("create.do")
	public String create(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		String parentId = Servlets.getParameter(request, "parentId");
		Site site = Context.getCurrentSite(request);
		File root = new File(pathResolver.getPath(""));
		String base = site.getFilesBasePath("");
		if (StringUtils.isBlank(parentId)) {
			parentId = base;
		}

		String cid = Servlets.getParameter(request, "cid");
		if (StringUtils.isNotBlank(cid)) {
			File file = new File(pathResolver.getPath(cid));
			WebFile bean = new WebFile(file, root.getAbsolutePath(),
					request.getContextPath());
			modelMap.addAttribute("bean", bean);
		}

		modelMap.addAttribute("parentId", parentId);
		modelMap.addAttribute(OPRT, CREATE);
		return "core/web_file/web_file_form";
	}

	@RequiresPermissions("core:web_file:edit")
	@RequestMapping("edit.do")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		String parentId = Servlets.getParameter(request, "parentId");
		String id = Servlets.getParameter(request, "id");
		File root = new File(pathResolver.getPath(""));
		File file = new File(pathResolver.getPath(id));
		if (!hasPermission(file, site)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		WebFile bean = new WebFile(file, root.getAbsolutePath(),
				request.getContextPath());
		modelMap.addAttribute("parentId", parentId);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute(OPRT, EDIT);
		return "core/web_file/web_file_form";
	}

	@RequiresPermissions("core:web_file:mkdir")
	@RequestMapping(value = "mkdir.do", method = RequestMethod.POST)
	public String mkdir(String parentId, String dir,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) throws IOException {
		Site site = Context.getCurrentSite(request);
		String base = site.getFilesBasePath("");
		if (StringUtils.isBlank(parentId)) {
			parentId = base;
		}
		File parent = new File(pathResolver.getPath(parentId));
		File newDir = new File(parent, dir);
		if (!hasPermission(newDir, site)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		boolean isSuccess = newDir.mkdirs();
		ra.addFlashAttribute("refreshLeft", true);
		ra.addAttribute("parentId", parentId);
		ra.addFlashAttribute(MESSAGE, isSuccess ? OPERATION_SUCCESS
				: OPERATION_FAILURE);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:web_file:save")
	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	public String save(String parentId, String name, String text,
			String redirect, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		File parent = new File(pathResolver.getPath(parentId));
		File file = new File(parent, name);
		if (!hasPermission(file, site)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		try {
			FileUtils.write(file, text, "UTF-8");
		} catch (IOException e) {
			logger.error("", e);
		}
		logger.info("save file, name={}.", file.getAbsolutePath());
		ra.addFlashAttribute("refreshLeft", true);
		ra.addAttribute("parentId", parentId);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else if (Constants.REDIRECT_CREATE.equals(redirect)) {
			return "redirect:create.do";
		} else {
			File root = new File(pathResolver.getPath(""));
			WebFile webFile = new WebFile(file, root.getAbsolutePath(),
					request.getContextPath());
			ra.addAttribute("id", webFile.getId());
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:web_file:update")
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public void update(String parentId, String origName, String name,
			String text, Integer position, String redirect,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		File root = new File(pathResolver.getPath(""));
		File parent = new File(pathResolver.getPath(parentId));
		if (!hasPermission(parent, site)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		File origFile = new File(parent, origName);
		File destFile = new File(parent, name);
		WebFile origWebFile = new WebFile(origFile, root.getAbsolutePath());
		if (origWebFile.isEditable()) {
			FileUtils.write(origFile, text, "UTF-8");
		}
		if (!origName.equals(name)) {
			FileUtils.moveFile(origFile, destFile);
		}
		logger.info("update file, name={}.", origFile.getAbsolutePath());
		Servlets.writeHtml(response, "true");
	}

	@RequiresPermissions("core:web_file:delete")
	@RequestMapping("delete.do")
	public String delete(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		String parentId = Servlets.getParameter(request, "parentId");
		String[] ids = Servlets.getParameterValues(request, "ids");
		if (ArrayUtils.isNotEmpty(ids)) {
			for (int i = 0, len = ids.length; i < len; i++) {
				File f = new File(pathResolver.getPath(ids[i]));
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

	@RequiresPermissions("core:web_file:rename")
	@RequestMapping("rename.do")
	public String rename(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra)
			throws IOException {
		String parentId = Servlets.getParameter(request, "parentId");
		Site site = Context.getCurrentSite(request);
		String base = site.getFilesBasePath("");
		if (StringUtils.isBlank(parentId)) {
			parentId = base;
		}

		String id = Servlets.getParameter(request, "id");
		String name = Servlets.getParameter(request, "name");
		File file = new File(pathResolver.getPath(id));
		if (!hasPermission(file, site)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		file.renameTo(new File(file.getParentFile(), name));

		ra.addAttribute("parentId", parentId);
		ra.addFlashAttribute("refreshLeft", true);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:web_file:move")
	@RequestMapping("move.do")
	public String move(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra)
			throws IOException {
		String parentId = Servlets.getParameter(request, "parentId");
		Site site = Context.getCurrentSite(request);
		String base = site.getFilesBasePath("");
		if (StringUtils.isBlank(parentId)) {
			parentId = base;
		}
		String[] ids = Servlets.getParameterValues(request, "ids");
		String dest = Servlets.getParameter(request, "dest");
		File destDir = new File(pathResolver.getPath(dest));
		String destPath = destDir.getAbsolutePath();
		String path, parentPath;
		for (String id : ids) {
			File file = new File(pathResolver.getPath(id));
			path = file.getAbsolutePath();
			parentPath = file.getParentFile().getAbsolutePath();
			if (destPath.equals(path) || destPath.equals(parentPath)
					|| destPath.startsWith(path + File.separator)) {
				continue;
			}
			if (!hasPermission(file, site)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			if (!hasPermission(destDir, site)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			FileUtils.moveToDirectory(file, destDir, true);
		}

		ra.addAttribute("refreshLeft", true);
		ra.addAttribute("parentId", parentId);
		ra.addFlashAttribute("refreshLeft", true);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:web_file:zip")
	@RequestMapping("zip.do")
	public String zip(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) throws IOException {
		Site site = Context.getCurrentSite(request);
		String parentId = Servlets.getParameter(request, "parentId");
		String[] ids = Servlets.getParameterValues(request, "ids");
		File[] files = new File[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			files[i] = new File(pathResolver.getPath(ids[i]));
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

	@RequiresPermissions("core:web_file:zip_download")
	@RequestMapping("zip_download.do")
	public void zipDownload(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		String[] ids = Servlets.getParameterValues(request, "ids");
		File[] files = new File[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			files[i] = new File(pathResolver.getPath(ids[i]));
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

	@RequiresPermissions("core:web_file:unzip")
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

	@RequiresPermissions("core:web_file:upload")
	@RequestMapping("upload.do")
	public void upload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		String parentId = Servlets.getParameter(request, "parentId");
		Site site = Context.getCurrentSite(request);
		String base = site.getFilesBasePath("");
		if (StringUtils.isBlank(parentId)) {
			parentId = base;
		}
		File dest = new File(pathResolver.getPath(parentId),
				file.getOriginalFilename());
		if (!hasPermission(dest, site)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		file.transferTo(dest);
		Servlets.writeHtml(response, "true");
	}

	@RequiresPermissions("core:web_file:zip_upload")
	@RequestMapping("zip_upload.do")
	public void zipUpload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) throws IOException {
		String parentId = Servlets.getParameter(request, "parentId");
		Site site = Context.getCurrentSite(request);
		String base = site.getFilesBasePath("");
		if (StringUtils.isBlank(parentId)) {
			parentId = base;
		}
		File parentFile = new File(pathResolver.getPath(parentId));
		if (!hasPermission(parentFile, site)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		File tempFile = File.createTempFile(
				String.valueOf(System.currentTimeMillis()), "temp");
		file.transferTo(tempFile);
		AntZipUtils.unzip(tempFile, parentFile);
		tempFile.delete();
		Servlets.writeHtml(response, "true");

		// ra.addAttribute("parentId", parentId);
		// ra.addFlashAttribute("refreshLeft", true);
		// ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		// return "redirect:list.do";
	}

	private boolean hasPermission(File file, Site site) throws IOException {
		String realPath = file.getCanonicalPath();
		String baseRealPath = pathResolver.getPath("");
		// 去除最后一个'/'
		baseRealPath = baseRealPath.substring(0, baseRealPath.length() - 1);
		String templateRealPath = pathResolver.getPath(ForeContext.FILES_PATH
				+ "/" + site.getId());
		logger.debug("realPath: {}", realPath);
		logger.debug("baseRealPath: {}", baseRealPath);
		logger.debug("templateRealPath: {}", templateRealPath);
		// 不能访问应用以外的文件
		if (!StringUtils.startsWithIgnoreCase(realPath, baseRealPath)) {
			return false;
		}
		// 在模板路径下的文件可以访问
		if (StringUtils.startsWithIgnoreCase(realPath, templateRealPath)) {
			return true;
		}
		Subject subject = SecurityUtils.getSubject();
		// 开启站点文件并且有权限的，可以访问。
		if (isEnableSiteFile()
				&& subject.isPermitted("core:web_file:site_file")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isEnableSiteFile() {
		String isEnableSiteFile = properties
				.getProperty("webFile.isEnableSiteFile");
		return "true".equals(isEnableSiteFile);
	}

	@Autowired
	@Qualifier("main")
	private Properties properties;

	@Autowired
	private PathResolver pathResolver;
}
