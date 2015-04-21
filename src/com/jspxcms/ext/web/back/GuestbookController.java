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
import com.jspxcms.core.domain.User;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.ext.domain.Guestbook;
import com.jspxcms.ext.domain.GuestbookType;
import com.jspxcms.ext.service.GuestbookService;
import com.jspxcms.ext.service.GuestbookTypeService;

/**
 * GuestbookController
 * 
 * @author yangxing
 * 
 */
@Controller
@RequestMapping("/ext/guestbook")
public class GuestbookController {
	private static final Logger logger = LoggerFactory
			.getLogger(GuestbookController.class);

	@RequiresPermissions("ext:guestbook:list")
	@RequestMapping("list.do")
	public String list(
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		Page<Guestbook> pagedList = service.findAll(params, pageable, siteId);
		modelMap.addAttribute("pagedList", pagedList);
		List<GuestbookType> typeList = guestbookTypeService.findList(siteId);
		modelMap.addAttribute("typeList", typeList);
		return "ext/guestbook/guestbook_list";
	}

	@RequiresPermissions("ext:guestbook:create")
	@RequestMapping("create.do")
	public String create(Integer id, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		if (id != null) {
			Guestbook bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		Integer siteId = Context.getCurrentSiteId(request);
		List<GuestbookType> typeList = guestbookTypeService.findList(siteId);
		modelMap.addAttribute("typeList", typeList);
		modelMap.addAttribute(OPRT, CREATE);
		return "ext/guestbook/guestbook_form";
	}

	@RequiresPermissions("ext:guestbook:edit")
	@RequestMapping("edit.do")
	public String edit(
			Integer id,
			Integer position,
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Guestbook bean = service.get(id);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		Integer siteId = Context.getCurrentSiteId(request);
		RowSide<Guestbook> side = service.findSide(params, siteId, bean,
				position, pageable.getSort());
		List<GuestbookType> typeList = guestbookTypeService.findList(siteId);
		modelMap.addAttribute("typeList", typeList);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		modelMap.addAttribute(OPRT, EDIT);
		return "ext/guestbook/guestbook_form";
	}

	@RequiresPermissions("ext:guestbook:save")
	@RequestMapping("save.do")
	public String save(Guestbook bean, Integer typeId, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		User user = Context.getCurrentUser(request);
		String ip = Servlets.getRemoteAddr(request);
		service.save(bean, user.getId(), typeId, ip, siteId);
		logger.info("save Guestbook, text={}.", bean.getText());
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

	@RequiresPermissions("ext:guestbook:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") Guestbook bean,
			Integer typeId, Integer position, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		String ip = Servlets.getRemoteAddr(request);
		User user = Context.getCurrentUser(request);
		service.update(bean, user.getId(), typeId, ip);
		logger.info("update Guestbook, text={}.", bean.getText());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("ext:guestbook:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, RedirectAttributes ra) {
		Guestbook[] beans = service.delete(ids);
		for (Guestbook bean : beans) {
			logger.info("delete Guestbook, text={}.", bean.getText());
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@ModelAttribute("bean")
	public Guestbook preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}

	@Autowired
	private GuestbookTypeService guestbookTypeService;
	@Autowired
	private GuestbookService service;
}
