package com.jspxcms.core.web.back;

import static com.jspxcms.core.support.Constants.CREATE;
import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.EDIT;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPERATION_SUCCESS;
import static com.jspxcms.core.support.Constants.OPRT;
import static com.jspxcms.core.support.Constants.SAVE_SUCCESS;

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
import com.jspxcms.core.domain.Comment;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.service.CommentService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;

/**
 * CommentController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/comment")
public class CommentController {
	private static final Logger logger = LoggerFactory
			.getLogger(CommentController.class);

	@RequiresPermissions("core:comment:list")
	@RequestMapping("list.do")
	public String list(
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		Page<Comment> pagedList = service.findAll(site.getId(), params,
				pageable);
		modelMap.addAttribute("pagedList", pagedList);
		return "core/comment/comment_list";
	}

	@RequiresPermissions("core:comment:create")
	@RequestMapping("create.do")
	public String create(Integer id, org.springframework.ui.Model modelMap) {
		if (id != null) {
			Comment bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		modelMap.addAttribute(OPRT, CREATE);
		return "core/comment/comment_form";
	}

	@RequiresPermissions("core:comment:edit")
	@RequestMapping("edit.do")
	public String edit(
			Integer id,
			Integer position,
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Comment bean = service.get(id);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		RowSide<Comment> side = service.findSide(site.getId(), params, bean,
				position, pageable.getSort());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		modelMap.addAttribute(OPRT, EDIT);
		return "core/comment/comment_form";
	}

	@RequiresPermissions("core:comment:save")
	@RequestMapping("save.do")
	public String save(Comment bean, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		Integer userId = Context.getCurrentUserId(request);
		service.save(bean, userId, siteId);
		logger.info("save Comment, id={}.", bean.getId());
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

	@RequiresPermissions("core:comment:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") Comment bean,
			Integer position, String redirect, RedirectAttributes ra) {
		service.update(bean);
		logger.info("update Comment, id={}.", bean.getId());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:comment:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, RedirectAttributes ra) {
		Comment[] beans = service.delete(ids);
		for (Comment bean : beans) {
			logger.info("delete Comment, id={}.", bean.getId());
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:comment:audit")
	@RequestMapping("audit.do")
	public String audit(Integer[] ids, RedirectAttributes ra) {
		Comment[] beans = service.audit(ids);
		for (Comment bean : beans) {
			logger.info("audit Comment, id={}.", bean.getId());
		}
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:comment:anti_audit")
	@RequestMapping("anti_audit.do")
	public String antiAudit(Integer[] ids, RedirectAttributes ra) {
		Comment[] beans = service.unaudit(ids);
		for (Comment bean : beans) {
			logger.info("unaudit Comment, id={}.", bean.getId());
		}
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}

	@ModelAttribute("bean")
	public Comment preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}

	@Autowired
	private CommentService service;
}
