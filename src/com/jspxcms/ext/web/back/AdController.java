package com.jspxcms.ext.web.back;

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
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.ext.domain.Ad;
import com.jspxcms.ext.domain.AdSlot;
import com.jspxcms.ext.service.AdService;
import com.jspxcms.ext.service.AdSlotService;

@Controller
@RequestMapping("/ext/ad")
public class AdController {
	private static final Logger logger = LoggerFactory
			.getLogger(AdController.class);

	@RequiresPermissions("ext:ad:list")
	@RequestMapping("list.do")
	public String list(
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		List<Ad> list = service.findList(siteId, params, pageable.getSort());
		List<AdSlot> slotList = slotService.findList(siteId);
		modelMap.addAttribute("list", list);
		modelMap.addAttribute("slotList", slotList);
		return "ext/ad/ad_list";
	}

	@RequiresPermissions("ext:ad:create")
	@RequestMapping("create.do")
	public String create(Integer id, Integer querySlotId,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		if (id != null) {
			Ad bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		List<AdSlot> slotList = slotService.findList(siteId);
		AdSlot slot;
		if (querySlotId != null) {
			slot = slotService.get(querySlotId);
		} else {
			slot = slotList.get(0);
		}
		modelMap.addAttribute("slotList", slotList);
		modelMap.addAttribute("slot", slot);
		modelMap.addAttribute(OPRT, CREATE);
		return "ext/ad/ad_form";
	}

	@RequiresPermissions("ext:ad:edit")
	@RequestMapping("edit.do")
	public String edit(
			Integer id,
			Integer querySlotId,
			Integer position,
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		Ad bean = service.get(id);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		RowSide<Ad> side = service.findSide(siteId, params, bean, position,
				pageable.getSort());
		List<AdSlot> slotList = slotService.findList(siteId);
		AdSlot slot = bean.getSlot();
		if (querySlotId != null) {
			slot = slotService.get(querySlotId);
		}
		modelMap.addAttribute("slotList", slotList);
		modelMap.addAttribute("slot", slot);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		modelMap.addAttribute(OPRT, EDIT);
		return "ext/ad/ad_form";
	}

	@RequiresPermissions("ext:ad:save")
	@RequestMapping("save.do")
	public String save(Ad bean, Integer slotId, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		service.save(bean, slotId, siteId);
		logger.info("save Ad, name={}.", bean.getName());
		ra.addAttribute("querySlotId", slotId);
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

	@RequiresPermissions("ext:ad:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") Ad bean, Integer slotId,
			Integer position, String redirect, RedirectAttributes ra) {
		service.update(bean, slotId);
		logger.info("update Ad, name={}.", bean.getName());
		ra.addAttribute("querySlotId", slotId);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("ext:ad:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, Integer querySlotId,
			RedirectAttributes ra) {
		Ad[] beans = service.delete(ids);
		for (Ad bean : beans) {
			logger.info("delete Ad, name={}.", bean.getName());
		}
		ra.addAttribute("querySlotId", querySlotId);
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@ModelAttribute("bean")
	public Ad preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}

	@Autowired
	private AdSlotService slotService;
	@Autowired
	private AdService service;
}
