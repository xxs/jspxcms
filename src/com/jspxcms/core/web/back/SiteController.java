package com.jspxcms.core.web.back;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Org;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.service.OrgService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.ThemeService;
import com.jspxcms.core.support.Context;

@Controller
@RequestMapping({ "/core/site" })
public class SiteController {
	private static final Logger logger = LoggerFactory
			.getLogger(SiteController.class);

	@Autowired
	private OrgService orgService;

	@Autowired
	private SiteService service;
	
	@Autowired
	private ThemeService themeService;

	@Autowired
	private PathResolver pathResolver;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequiresPermissions({ "core:site:list" })
	@RequestMapping({ "list.do" })
	public String list(
			@PageableDefaults(sort = { "treeNumber" }) Pageable pageable,
			HttpServletRequest request, Model modelMap) {
		Map params = Servlets.getParameterValuesMap(request, "search_");

		List list = this.service.findList(params, pageable.getSort());
		modelMap.addAttribute("list", list);
		return "core/site/site_list";
	}

	@RequiresPermissions({ "core:site:create" })
	@RequestMapping({ "create.do" })
	public String create(Integer id, Model modelMap) {
		if (id != null) {
			Site bean = this.service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		Org org = this.orgService.findRoot();
		modelMap.addAttribute("org", org);
//		List themeList = new ArrayList();
//		themeList.add("bluewise");
//		modelMap.addAttribute("themeList", themeList);
		
		modelMap.addAttribute("themes", themeService.getAll());
		
		modelMap.addAttribute("oprt", "create");
		return "core/site/site_form";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequiresPermissions({ "core:site:edit" })
	@RequestMapping({ "edit.do" })
	public String edit(
			Integer id,
			Integer position,
			@PageableDefaults(sort = { "id" }, sortDir = Sort.Direction.DESC) Pageable pageable,
			HttpServletRequest request, Model modelMap) {
		Site bean = this.service.get(id);
		Map params = Servlets.getParameterValuesMap(request, "search_");

		RowSide side = this.service.findSide(params, bean, position,
				pageable.getSort());

		String filesBasePath = bean.getFilesBasePath("");
		File filesBaseFile = new File(this.pathResolver.getPath(filesBasePath));
		File[] themeFiles = filesBaseFile.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		List themeList = new ArrayList();
		if (themeFiles != null) {
			for (File themeFile : themeFiles) {
				themeList.add(themeFile.getName());
			}
		}
//		if (themeList.isEmpty()) {
//			themeList.add("bluewise");
//		}
//		modelMap.addAttribute("themeList", themeList);
		modelMap.addAttribute("themes", themeService.getAll());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("parent", bean.getParent());
		modelMap.addAttribute("org", bean.getOrg());
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		modelMap.addAttribute("oprt", "edit");
		return "core/site/site_form";
	}

	@RequiresRoles({ "super" })
	@RequiresPermissions({ "core:site:save" })
	@RequestMapping({ "save.do" })
	public String save(Site bean, Integer parentId, Integer orgId,
			String redirect, HttpServletRequest request, RedirectAttributes ra) {
		Site site = Context.getCurrentSite(request);
		Integer userId = Context.getCurrentUserId(request);
		this.service.save(bean, parentId, orgId, userId, site);
		logger.info("save Site, name={}.", bean.getName());
		ra.addFlashAttribute("message", "saveSuccess");
		if ("list".equals(redirect))
			return "redirect:list.do";
		if ("create".equals(redirect)) {
			return "redirect:create.do";
		}
		ra.addAttribute("id", bean.getId());
		return "redirect:edit.do";
	}
	/**
	 * 循环生成站点，做站点的测试
	 * @param bean
	 * @param parentId
	 * @param orgId
	 * @param redirect
	 * @param request
	 * @param ra
	 * @return
	 */
	@RequiresRoles({ "super" })
	@RequiresPermissions({ "core:site:save" })
	@RequestMapping({ "savetest.do" })
	public String savetest(Site bean, Integer parentId, Integer orgId,
			String redirect, HttpServletRequest request, RedirectAttributes ra) {
		//获取当前的站点数据
		Site site = Context.getCurrentSite(request);
		Integer userId = Context.getCurrentUserId(request);
		//开始循环生成站点数据（用于测试站群）
		for (int i = 0; i < 200; i++) {
			Site forSite = new Site();
			System.out.println("站点域名："+i+".dedweek.com");
			System.out.println("站点名称："+i+"号测试站点");
			System.out.println("站点名称："+i+"号测试站点全名");
			System.out.println("站点编码："+i+"000001");
			forSite.setFullName(i+"号测试站点全名");
			forSite.setName(i+"号测试站点");
			forSite.setDomain(i+".dedweek.com");
			forSite.setIdentifyDomain(true);
			forSite.setNumber(i+"000001");
			forSite.setUrlRewrite(false);
			forSite.setTemplateTheme("default");
			forSite.setNoPicture("/img/no_picture.jpg");
			forSite.setAllStatic(false);
			forSite.setDef(false);
			forSite.setStatus(0);
			this.service.save(forSite, null, 1, userId, site);
		}
		logger.info("save Site, name={}.", bean.getName());
		ra.addFlashAttribute("message", "saveSuccess");
		ra.addAttribute("id", bean.getId());
		return "redirect:savetest.do";
	}

	@RequiresRoles({ "super" })
	@RequiresPermissions({ "core:site:update" })
	@RequestMapping({ "update.do" })
	public String update(@ModelAttribute("bean") Site bean, Integer parentId,
			Integer orgId, Integer position, String redirect,
			RedirectAttributes ra) {
		this.service.update(bean, parentId, orgId);
		logger.info("update Site, name={}.", bean.getName());
		ra.addFlashAttribute("message", "saveSuccess");
		if ("list".equals(redirect)) {
			return "redirect:list.do";
		}
		ra.addAttribute("id", bean.getId());
		ra.addAttribute("position", position);
		return "redirect:edit.do";
	}

	@RequiresRoles({ "super" })
	@RequiresPermissions({ "core:site:delete" })
	@RequestMapping({ "delete.do" })
	public String delete(Integer[] ids, RedirectAttributes ra) {
		Site[] beans = this.service.delete(ids);
		for (Site bean : beans) {
			logger.info("delete Site, name={}.", bean.getName());
		}
		ra.addFlashAttribute("message", "deleteSuccess");
		return "redirect:list.do";
	}
	@RequiresRoles({ "super" })
	@RequiresPermissions({ "core:site:delete" })
	@RequestMapping({ "forcedelete.do" })
	public String forcedelete(Integer[] ids, RedirectAttributes ra) {
		Site[] beans = this.service.forcedelete(ids);
		for (Site bean : beans) {
			logger.info("delete Site, name={}.", bean.getName());
		}
		ra.addFlashAttribute("message", "deleteSuccess");
		return "redirect:list.do";
	}

	@RequestMapping({ "check_number.do" })
	public void checkUsername(String number, String original,
			HttpServletResponse response) {
		if (StringUtils.isBlank(number)) {
			Servlets.writeHtml(response, "false");
			return;
		}
		if (StringUtils.equals(number, original)) {
			Servlets.writeHtml(response, "true");
			return;
		}

		boolean exist = this.service.numberExist(number);
		Servlets.writeHtml(response, exist ? "false" : "true");
	}

	@ModelAttribute("bean")
	public Site preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? this.service.get(oid) : null;
	}
}