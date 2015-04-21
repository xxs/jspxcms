package com.jspxcms.ext.web.back;

import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.EDIT;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPRT;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.ext.domain.VisitLog;
import com.jspxcms.ext.service.VisitLogService;

@Controller
@RequestMapping("/ext/visit_log")
public class VisitLogController {
	private static final Logger logger = LoggerFactory
			.getLogger(VisitLogController.class);

	@RequiresPermissions("ext:visit_log:list")
	@RequestMapping("list.do")
	public String list(
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		Page<VisitLog> pagedList = service.findAll(params, pageable);
		modelMap.addAttribute("pagedList", pagedList);
		return "ext/visit_log/visit_log_list";
	}

	@RequiresPermissions("ext:visit_log:view")
	@RequestMapping("view.do")
	public String view(
			Integer id,
			Integer position,
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		VisitLog bean = service.get(id);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		RowSide<VisitLog> side = service.findSide(params, bean, position,
				pageable.getSort());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		modelMap.addAttribute(OPRT, EDIT);
		return "ext/visit_log/visit_log_form";
	}

	@RequiresPermissions("ext:visit_log:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, RedirectAttributes ra) {
		List<VisitLog> beans = service.delete(ids);
		for (VisitLog bean : beans) {
			logger.info("delete VisitLog, url={}.", bean.getUrl());
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("ext:visit_log:delete")
	@RequestMapping("batch_delete.do")
	public String batchDelete(String before, RedirectAttributes ra) {
		long count = service.deleteByDate(before);
		logger.info("delete VisitLog, date <= {}, count: {}.", before, count);
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("ext:visit_log:traffic_analysis")
	@RequestMapping("traffic_analysis.do")
	public String trafficAnalysis(String begin, String end, Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		DateTime dt = new DateTime();
		if (StringUtils.isBlank(end)) {
			end = VisitLog.format(dt.toDate());
		}
		if (StringUtils.isBlank(begin)) {
			begin = VisitLog.format(dt.plusDays(-30).toDate());
		}
		Page<Object[]> pagedList = service.trafficByDate(begin, end, siteId,
				pageable);
		modelMap.addAttribute("pagedList", pagedList);
		modelMap.addAttribute("begin", begin);
		modelMap.addAttribute("end", end);
		return "ext/visit_log/visit_traffic_analysis";
	}

	@RequiresPermissions("ext:visit_log:url_analysis")
	@RequestMapping("url_analysis.do")
	public String urlAnalysis(String begin, String end, Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		DateTime dt = new DateTime();
		if (StringUtils.isBlank(end)) {
			end = VisitLog.format(dt.toDate());
		}
		if (StringUtils.isBlank(begin)) {
			begin = VisitLog.format(dt.plusDays(-30).toDate());
		}
		Page<Object[]> pagedList = service.urlByDate(begin, end, siteId,
				pageable);
		modelMap.addAttribute("pagedList", pagedList);
		modelMap.addAttribute("begin", begin);
		modelMap.addAttribute("end", end);
		return "ext/visit_log/visit_url_analysis";
	}

	@Autowired
	private VisitLogService service;
}
