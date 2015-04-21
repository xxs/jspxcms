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
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.ext.domain.Friendlink;
import com.jspxcms.ext.domain.FriendlinkType;
import com.jspxcms.ext.service.FriendlinkService;
import com.jspxcms.ext.service.FriendlinkTypeService;

/**
 * FriendlinkController
 * 
 * @author yangxing
 * 
 */

@Controller
@RequestMapping("/ext/friendlink")
public class FriendlinkController {
	private static final Logger logger = LoggerFactory
			.getLogger(FriendlinkController.class);

	@RequiresPermissions("ext:friendlink:list")
	@RequestMapping("list.do")
	public String list(HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		Integer siteId = Context.getCurrentSiteId(request);
		List<Friendlink> list = service.findList(siteId,params);
		modelMap.addAttribute("list", list);
		List<FriendlinkType> typeList = friendlinkTypeService.findList(siteId);
		modelMap.addAttribute("typeList", typeList);
		return "ext/friendlink/friendlink_list";
	}

	@RequiresPermissions("ext:friendlink:create")
	@RequestMapping("create.do")
	public String create(Integer id, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		if (id != null) {
			Friendlink bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}

		Integer siteId = Context.getCurrentSiteId(request);
		List<FriendlinkType> typeList = friendlinkTypeService.findList(siteId);
		modelMap.addAttribute("typeList", typeList);

		modelMap.addAttribute(OPRT, CREATE);
		return "ext/friendlink/friendlink_form";
	}

	@RequiresPermissions("ext:friendlink:edit")
	@RequestMapping("edit.do")
	public String edit(
			Integer id,
			Integer position,
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		Friendlink bean = service.get(id);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		RowSide<Friendlink> side = service.findSide(siteId, params, bean, position,
				pageable.getSort());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		List<FriendlinkType> typeList = friendlinkTypeService.findList(siteId);
		modelMap.addAttribute("typeList", typeList);
		modelMap.addAttribute(OPRT, EDIT);
		return "ext/friendlink/friendlink_form";
	}

	@RequiresPermissions("ext:friendlink:save")
	@RequestMapping("save.do")
	public String save(Friendlink bean, Integer typeId, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		service.save(bean, typeId, siteId);
		logger.info("save Friendlink, name={}.", bean.getName());
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

	@RequestMapping("batch_update.do")
	public String batchUpdate(Integer[] id, RedirectAttributes ra) {
		if (ArrayUtils.isNotEmpty(id)) {
			Friendlink[] beans = service.batchUpdate(id);
			for (Friendlink bean : beans) {
				logger.info("update Friendlink, name={}.", bean.getName());
			}
		}
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("ext:friendlink:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") Friendlink bean,
			Integer typeId, Integer position, String redirect,
			RedirectAttributes ra) {
		service.update(bean, typeId);
		logger.info("update Friendlink, name={}.", bean.getName());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("ext:friendlink:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, RedirectAttributes ra) {
		Friendlink[] beans = service.delete(ids);
		for (Friendlink bean : beans) {
			logger.info("delete Friendlink, name={}.", bean.getName());
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@ModelAttribute("bean")
	public Friendlink preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}

	@Autowired
	private FriendlinkTypeService friendlinkTypeService;
	@Autowired
	private FriendlinkService service;
}
