package com.jspxcms.ext.web.back;

import static com.jspxcms.core.support.Constants.CREATE;
import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.EDIT;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPRT;
import static com.jspxcms.core.support.Constants.SAVE_SUCCESS;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.ext.domain.Vote;
import com.jspxcms.ext.service.VoteService;

@Controller
@RequestMapping("/ext/vote")
public class VoteController {
	private static final Logger logger = LoggerFactory
			.getLogger(VoteController.class);

	@RequiresPermissions("ext:vote:list")
	@RequestMapping("list.do")
	public String list(
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		Page<Vote> pagedList = service.findAll(siteId, params, pageable);
		modelMap.addAttribute("pagedList", pagedList);
		return "ext/vote/vote_list";
	}

	@RequiresPermissions("ext:vote:create")
	@RequestMapping("create.do")
	public String create(Integer id, org.springframework.ui.Model modelMap) {
		if (id != null) {
			Vote bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		modelMap.addAttribute(OPRT, CREATE);
		return "ext/vote/vote_form";
	}

	@RequiresPermissions("ext:vote:edit")
	@RequestMapping("edit.do")
	public String edit(
			@RequestParam(required = true) Integer id,
			Integer position,
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		Vote bean = service.get(id);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		RowSide<Vote> side = service.findSide(siteId, params, bean, position,
				pageable.getSort());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		modelMap.addAttribute(OPRT, EDIT);
		return "ext/vote/vote_form";
	}

	@RequiresPermissions("ext:vote:save")
	@RequestMapping("save.do")
	public String save(Vote bean, String[] itemTitle, Integer[] itemCount,
			String redirect, HttpServletRequest request, RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		service.save(bean, itemTitle, itemCount, siteId);
		logger.info("save Vote, title={}.", bean.getTitle());
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

	@RequiresPermissions("ext:vote:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") Vote bean, Integer[] itemId,
			String[] itemTitle, Integer[] itemCount, Integer position,
			String redirect, RedirectAttributes ra) {
		service.update(bean, itemId, itemTitle, itemCount);
		logger.info("update Vote, title={}.", bean.getTitle());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("ext:vote:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, RedirectAttributes ra) {
		Vote[] beans = service.delete(ids);
		for (Vote bean : beans) {
			logger.info("delete Vote, title={}.", bean.getTitle());
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	/**
	 * 检查编号是否存在
	 * 
	 * @return
	 */
	@RequestMapping("check_number.do")
	public void checkNumber(String number, String original,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(number) || StringUtils.equals(number, original)) {
			Servlets.writeHtml(response, "true");
			return;
		}
		// 检查数据库是否重名
		Integer siteId = Context.getCurrentSiteId(request);
		String result = service.numberExist(number, siteId) ? "false" : "true";
		Servlets.writeHtml(response, result);
	}

	@ModelAttribute("bean")
	public Vote preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}

	@Autowired
	private VoteService service;
}
