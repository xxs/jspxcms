package com.jspxcms.core.web.back;

import static com.jspxcms.core.support.Constants.CREATE;
import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.EDIT;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPRT;
import static com.jspxcms.core.support.Constants.SAVE_SUCCESS;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Org;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.service.OrgService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;

/**
 * OrgController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/org")
public class OrgController {
	private static final Logger logger = LoggerFactory
			.getLogger(OrgController.class);

	// @RequiresPermissions("core:org:left")
	// @RequestMapping("left.do")
	// public String left(HttpServletRequest request,
	// org.springframework.ui.Model modelMap) {
	// List<Org> list = service.findList(null, null, null, null);
	// modelMap.addAttribute("list", list);
	// return "core/org/org_left";
	// }

	@RequiresPermissions("core:org:list")
	@RequestMapping("list.do")
	public String list(
			Integer queryParentId,
			@RequestParam(defaultValue = "true") boolean showDescendants,
			@PageableDefaults(sort = "treeNumber", sortDir = Direction.ASC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		String topTreeNumber = site.getOrg().getTreeNumber();
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		List<Org> list = service.findList(topTreeNumber, queryParentId,
				showDescendants, params, pageable.getSort());
		List<Org> orgList = service.findList(topTreeNumber);
		modelMap.addAttribute("list", list);
		modelMap.addAttribute("orgList", orgList);
		modelMap.addAttribute("queryParentId", queryParentId);
		modelMap.addAttribute("showDescendants", showDescendants);
		return "core/org/org_list";
	}

	@RequiresPermissions("core:org:create")
	@RequestMapping("create.do")
	public String create(Integer id, Integer parentId, Integer queryParentId,
			Boolean showDescendants, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Org topOrg = site.getOrg();
		String orgTreeNumber = topOrg.getTreeNumber();
		Org bean = null, parent = topOrg;
		if (id != null) {
			bean = service.get(id);
		}
		if (bean != null) {
			parent = bean.getParent();
		} else if (parentId != null) {
			parent = service.get(parentId);
		}
		modelMap.addAttribute(OPRT, CREATE);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("parent", parent);
		modelMap.addAttribute("queryParentId", queryParentId);
		modelMap.addAttribute("showDescendants", showDescendants);
		modelMap.addAttribute("orgTreeNumber", orgTreeNumber);
		modelMap.addAttribute("parent", parent);
		return "core/org/org_form";
	}

	@RequiresPermissions("core:org:edit")
	@RequestMapping("edit.do")
	public String edit(
			Integer id,
			Integer queryParentId,
			@RequestParam(defaultValue = "true") boolean showDescendants,
			Integer position,
			@PageableDefaults(sort = "treeNumber", sortDir = Direction.ASC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		String orgTreeNumber = site.getOrg().getTreeNumber();
		Org bean = service.get(id);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		RowSide<Org> side = service.findSide(orgTreeNumber, queryParentId,
				showDescendants, params, bean, position, pageable.getSort());
		modelMap.addAttribute(OPRT, EDIT);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("parent", bean.getParent());
		modelMap.addAttribute("queryParentId", queryParentId);
		modelMap.addAttribute("showDescendants", showDescendants);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		modelMap.addAttribute("orgTreeNumber", orgTreeNumber);
		return "core/org/org_form";
	}

	@RequiresPermissions("core:org:save")
	@RequestMapping("save.do")
	public String save(Org bean, Integer parentId, Integer queryParentId,
			Boolean showDescendants, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		service.save(bean, parentId);
		logger.info("save Org, name={}.", bean.getName());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		ra.addAttribute("parentId", parentId);
		ra.addAttribute("queryParentId", queryParentId);
		ra.addAttribute("showDescendants", showDescendants);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else if (Constants.REDIRECT_CREATE.equals(redirect)) {
			return "redirect:create.do";
		} else {
			ra.addAttribute("id", bean.getId());
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:org:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") Org bean, Integer parentId,
			Integer queryParentId, Boolean showDescendants, Integer position,
			String redirect, RedirectAttributes ra) {
		service.update(bean, parentId);
		logger.info("update Org, name={}.", bean.getName());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		ra.addAttribute("queryParentId", queryParentId);
		ra.addAttribute("showDescendants", showDescendants);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:org:batch_update")
	@RequestMapping("batch_update.do")
	public String batchUpdate(Integer[] id, String[] name, String[] number,
			String[] phone, String[] address, Integer queryParentId,
			Boolean showDescendants, Pageable pageable,
			HttpServletRequest request, RedirectAttributes ra) {
		if (ArrayUtils.isNotEmpty(id)) {
			// 有排序的情况下不更新树结构，以免引误操作。
			boolean isUpdateTree = pageable.getSort() == null;
			Org[] beans = service.batchUpdate(id, name, number, phone, address,
					isUpdateTree);
			for (Org bean : beans) {
				logger.info("update Org, name={}.", bean.getName());
			}
		}
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		ra.addAttribute("queryParentId", queryParentId);
		ra.addAttribute("showDescendants", showDescendants);
		ra.addFlashAttribute("refreshLeft", true);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:org:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, Integer queryParentId,
			Boolean showDescendants, RedirectAttributes ra) {
		Org[] beans = service.delete(ids);
		for (Org bean : beans) {
			logger.info("delete Org, name={}.", bean.getName());
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		ra.addAttribute("queryParentId", queryParentId);
		ra.addAttribute("showDescendants", showDescendants);
		return "redirect:list.do";
	}

	@ModelAttribute("bean")
	public Org preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}

	@Autowired
	private OrgService service;
}
