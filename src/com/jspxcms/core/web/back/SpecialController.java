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

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.jspxcms.core.domain.Special;
import com.jspxcms.core.domain.SpecialCategory;
import com.jspxcms.core.service.SpecialCategoryService;
import com.jspxcms.core.service.SpecialService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;

/**
 * SpecialController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/special")
public class SpecialController {
	private static final Logger logger = LoggerFactory
			.getLogger(SpecialController.class);

	@RequiresPermissions("core:special:list")
	@RequestMapping("list.do")
	public String list(
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		Page<Special> pagedList = service.findAll(siteId, params, pageable);
		modelMap.addAttribute("pagedList", pagedList);

		List<SpecialCategory> categoryList = specialCategoryService
				.findList(siteId);
		modelMap.addAttribute("categoryList", categoryList);
		return "core/special/special_list";
	}

	@RequiresPermissions("core:special:create")
	@RequestMapping("create.do")
	public String create(Integer id, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		if (id != null) {
			Special bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}

		Integer siteId = Context.getCurrentSiteId(request);
		List<SpecialCategory> categoryList = specialCategoryService
				.findList(siteId);
		modelMap.addAttribute("categoryList", categoryList);

		modelMap.addAttribute(OPRT, CREATE);
		return "core/special/special_form";
	}

	@RequiresPermissions("core:special:edit")
	@RequestMapping("edit.do")
	public String edit(
			Integer id,
			Integer position,
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		Special bean = service.get(id);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		RowSide<Special> side = service.findSide(siteId, params, bean,
				position, pageable.getSort());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);

		List<SpecialCategory> categoryList = specialCategoryService
				.findList(siteId);
		modelMap.addAttribute("categoryList", categoryList);

		modelMap.addAttribute(OPRT, EDIT);
		return "core/special/special_form";
	}

	@RequiresPermissions("core:special:save")
	@RequestMapping("save.do")
	public String save(Special bean, Integer categoryId, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		Integer userId = Context.getCurrentUserId(request);
		service.save(bean, categoryId, userId, siteId);
		logger.info("save Special, title={}.", bean.getTitle());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else if (Constants.REDIRECT_CREATE.equals(redirect)) {
			return "redirect:create.do";
		} else {
			ra.addAttribute("id", bean.getId());
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:special:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") Special bean,
			Integer categoryId, Integer position, String redirect,
			RedirectAttributes ra) {
		service.update(bean, categoryId);
		logger.info("update Special, title={}.", bean.getTitle());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:special:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, RedirectAttributes ra) {
		Special[] beans = service.delete(ids);
		for (Special bean : beans) {
			logger.info("delete Special, title={}.", bean.getTitle());
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@ModelAttribute("bean")
	public Special preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}

	@Autowired
	private SpecialCategoryService specialCategoryService;
	@Autowired
	private SpecialService service;
}
