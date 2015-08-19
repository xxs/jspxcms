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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.ParameterGroup;
import com.jspxcms.core.repository.NodeDao;
import com.jspxcms.core.service.ParameterGroupService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;

/**
 * ParameterGroupController
 * 
 * @author xxs
 * 
 */
@Controller
@RequestMapping("/core/parameterGroup")
public class ParameterGroupController {
	private static final Logger logger = LoggerFactory
			.getLogger(ParameterGroupController.class);

	@RequestMapping("list.do")
	public String list(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		List<ParameterGroup> list = service.findList(siteId);
		modelMap.addAttribute("list", list);
		return "core/parameter_group/parameterGroup_list";
	}

	@RequestMapping("create.do")
	public String create(Integer id, org.springframework.ui.Model modelMap) {
		if (id != null) {
			ParameterGroup bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		modelMap.addAttribute(OPRT, CREATE);
		return "core/parameter_group/parameterGroup_form";
	}
	
	@RequestMapping("save.do")
	public String save(ParameterGroup bean, Integer[] infoPermIds, Integer[] nodePermIds,String[] itemName,
			String redirect, HttpServletRequest request, RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		service.save(bean, infoPermIds, nodePermIds,itemName, siteId);
		logger.info("save Attr, name={}.", bean.getName());
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
	public String batchUpdate(Integer[] id, String[] name, String[] number,
			Integer[] imageWidth, Integer[] imageHeight, RedirectAttributes ra) {
		if (ArrayUtils.isNotEmpty(id)) {
			ParameterGroup[] beans = service.batchUpdate(id, name);
			for (ParameterGroup bean : beans) {
				logger.info("update Attr, name={}.", bean.getName());
			}
		}
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		return "redirect:list.do";
	}

	@RequestMapping("edit.do")
	public String edit(Integer id, Integer position, @PageableDefaults(sort = {
			"seq", "id" }, sortDir = Direction.ASC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		ParameterGroup bean = service.get(id);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		RowSide<ParameterGroup> side = service.findSide(siteId, params, bean, position,
				pageable.getSort());
		modelMap.addAttribute("nodePerms", bean.getNodePerms());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		modelMap.addAttribute(OPRT, EDIT);
		return "core/parameter_group/parameterGroup_form";
	}
	
	
	
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") ParameterGroup bean,
			Integer[] infoPermIds, Integer[] nodePermIds,Integer[] itemId,String[] itemName, Integer position,
			String redirect, RedirectAttributes ra) {
		// 如果拥有所有权限，则清空权限字符串。
		if (infoPermIds == null) {
			infoPermIds = new Integer[0];
		}
		if (nodePermIds == null) {
			nodePermIds = new Integer[0];
		}
		service.update(bean, infoPermIds, nodePermIds,itemId,itemName);
		logger.info("update Attr, name={}.", bean.getName());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}
	
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, RedirectAttributes ra) {
		ParameterGroup[] beans = service.delete(ids);
		for (ParameterGroup bean : beans) {
			logger.info("delete Attr, name={}.", bean.getName());
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@Autowired
	private NodeDao nodeDao;
	
	@Autowired
	private ParameterGroupService service;
}
