package com.jspxcms.core.web.back.f7;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.common.web.PathResolver;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.WebFile;

/**
 * WebFileF7Controller
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/web_file")
public class WebFileF7Controller {

	@RequestMapping("choose_file_tree.do")
	public String f7FileTree(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		// 根目录为应用的上下文路径，这是固定的。
		File rootFile = new File(pathResolver.getPath(""));
		// 默认父文件夹为file基础路径
		String base = site.getFilesBasePath(site.getTemplateTheme());
		File baseFile = new File(pathResolver.getPath(base));
		// 当前选中路径
		String id = Servlets.getParameter(request, "id");
		WebFile bean = null;
		if (StringUtils.isNotBlank(id)) {
			File idFile = new File(pathResolver.getPath(id));
			bean = new WebFile(idFile, rootFile.getCanonicalPath(),
					request.getContextPath());
		}
		WebFile baseWebFile = new WebFile(baseFile,
				rootFile.getCanonicalPath(), request.getContextPath());
		List<WebFile> baseChildList = baseWebFile.listFiles();
		WebFile.sort(baseChildList, null, null);
		Queue<WebFile> queue = new LinkedList<WebFile>(baseChildList);
		List<WebFile> list = new ArrayList<WebFile>();
		WebFile webFile;
		List<WebFile> child;
		while (!queue.isEmpty()) {
			webFile = queue.poll();
			list.add(webFile);
			if (webFile.isDirectory()) {
				child = webFile.listFiles();
				WebFile.sort(child, null, null);
				for (WebFile c : child) {
					queue.add(c);
				}
			}
		}
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("list", list);
		modelMap.addAttribute("base", base);
		Servlets.setNoCacheHeader(response);
		return "core/web_file/choose_file_tree";
	}

	@RequestMapping("choose_dir.do")
	public String f7Dir(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		String parentId = Servlets.getParameter(request, "parentId");
		Site site = Context.getCurrentSite(request);
		// 根目录为应用的上下文路径，这是固定的。
		File root = new File(pathResolver.getPath(""));
		// 默认父文件夹为file基础路径
		String base = site.getFilesBasePath("");
		if (StringUtils.isBlank(parentId)) {
			parentId = base;
		}

		// 需排除的文件夹
		String[] ids = Servlets.getParameterValues(request, "ids");
		final String[] realIds = new String[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			realIds[i] = pathResolver.getPath(ids[i]);
		}
		String realPath = pathResolver.getPath(parentId);
		File parent = new File(realPath);
		WebFile parentWebFile = new WebFile(parent, root.getAbsolutePath(),
				request.getContextPath());
		List<WebFile> list = parentWebFile.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				// 只显示文件夹，不显示文件
				if (pathname.isDirectory()) {
					String path = pathname.getAbsolutePath();
					for (String id : realIds) {
						if (path.equals(id)
								|| path.startsWith(id + File.separator)) {
							return false;
						}
					}
					return true;
				}
				return false;
			}
		});
		// 设置当前目录
		parentWebFile.setCurrent(true);
		list.add(0, parentWebFile);
		// 设置上级目录
		if (parentId.length() > base.length()) {
			WebFile ppWebFile = parentWebFile.getParentFile();
			ppWebFile.setParent(true);
			list.add(0, ppWebFile);
			modelMap.addAttribute("ppId", ppWebFile.getId());
		}
		modelMap.addAttribute("ids", ids);
		modelMap.addAttribute("parentId", parentId);
		modelMap.addAttribute("list", list);
		Servlets.setNoCacheHeader(response);
		return "core/web_file/choose_dir";
	}

	@RequestMapping("choose_dir_list.do")
	public String f7DirList(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		f7Dir(request, response, modelMap);
		return "core/web_file/choose_dir_list";
	}

	@Autowired
	private PathResolver pathResolver;
}
