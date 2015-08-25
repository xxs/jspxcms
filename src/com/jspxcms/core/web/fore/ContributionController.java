package com.jspxcms.core.web.fore;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jspxcms.common.web.Validations;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoDetail;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.InfoService;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;

/**
 * ContributeController
 * 
 * @author liufang
 * 
 */
@Controller
public class ContributionController {
	public static final String LIST_TEMPLATE = "sys_member_contribution_list.html";
	public static final String FORM_TEMPLATE = "sys_member_contribution_form.html";

	@RequestMapping(value = "/my/contribution.jspx")
	public String list(Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(LIST_TEMPLATE);
	}

	@RequestMapping(value = "/my/contribution/create.jspx")
	public String createForm(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		List<Node> nodeList = nodeQuery
				.findList(site.getId(), null, true, null);
		modelMap.addAttribute("nodeList", nodeList);
		modelMap.addAttribute(Constants.OPRT, Constants.CREATE);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(FORM_TEMPLATE);
	}

	@RequestMapping(value = "/my/contribution/update/{id}.jspx")
	public String updateForm(@PathVariable("id") Integer id,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		Site site = Context.getCurrentSite(request);
		User user = Context.getCurrentUser(request);
		if (!Validations.notNull(id, messages, "id")) {
			return resp.badRequest();
		}
		Info bean = infoQuery.get(id);
		if (!Validations.exist(bean, messages, "Info", id)) {
			return resp.notFound();
		}
		if (!bean.getCreator().getId().equals(user.getId())) {
			return resp.warning("不能修改不属于的数据");
		}
		List<Node> nodeList = nodeQuery
				.findList(site.getId(), null, true, null);
		modelMap.addAttribute("nodeList", nodeList);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute(Constants.OPRT, Constants.EDIT);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(FORM_TEMPLATE);
	}

	@RequestMapping(value = "/my/contribution/create.jspx", method = RequestMethod.POST)
	public String create(Integer nodeId, String title, String text,
			String file, String fileName, Long fileLength,
			@RequestParam(defaultValue = "false") boolean draft,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		Site site = Context.getCurrentSite(request);
		User user = Context.getCurrentUser(request);
		Collection<MemberGroup> groups = Context.getCurrentGroups(request);
		if (!Validations.notNull(nodeId, messages, "nodeId")) {
			return resp.post(401);
		}
		Node node = nodeQuery.get(nodeId);
		if (!node.isContriPerm(user, groups)) {
			return resp.post(501, "该栏目不允许投稿");
		}
		Integer siteId = site.getId();
		Integer userId = Context.getCurrentUserId(request);
		Info bean;
		InfoDetail detail;
		bean = new Info();
		detail = new InfoDetail();
		detail.setTitle(title);
		detail.setFile(file);
		detail.setFileName(fileName);
		detail.setFileLength(fileLength);
		Map<String, String> clobs = new HashMap<String, String>();
		clobs.put("text", text);
		String status = draft ? Info.DRAFT : Info.CONTRIBUTION;
		infoService.save(bean, detail, null, null, null, null, null, clobs,
				null, null, null, null, null, nodeId, userId, status, siteId,null);
		return resp.post();
	}

	@RequestMapping(value = "/my/contribution/update.jspx", method = RequestMethod.POST)
	public String update(Integer id, Integer nodeId, String title, String text,
			String file, String fileName, Long fileLength,
			@RequestParam(defaultValue = "false") boolean pass,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		User user = Context.getCurrentUser(request);
		Collection<MemberGroup> groups = Context.getCurrentGroups(request);
		Info bean = infoQuery.get(id);
		if (bean == null) {
			return resp.post(451);
		}
		if (nodeId != null && !bean.getNode().getId().equals(nodeId)) {
			Node node = nodeQuery.get(nodeId);
			if (node.isContriPerm(user, groups)) {
				return resp.post(501, "该栏目不允许投稿");
			}
		}
		if (!bean.getCreator().getId().equals(user.getId())) {
			return resp.post(501, "不能修改不属于的数据");
		}
		InfoDetail detail = bean.getDetail();
		if (!bean.getStatus().equals(Info.DRAFT)
				&& !bean.getStatus().equals(Info.CONTRIBUTION)
				&& !bean.getStatus().equals(Info.REJECTION)) {
			return resp.post(501, "稿件审核中或已审核通过，不能修改");
		}
		detail = bean.getDetail();
		detail.setTitle(title);
		detail.setFile(file);
		detail.setFileName(fileName);
		detail.setFileLength(fileLength);
		Map<String, String> clobs = new HashMap<String, String>();
		clobs.put("text", text);
		infoService.update(bean, detail, null, null, null, null, null, clobs,
				null, null, null, null, null, nodeId, user, pass,null);
		return resp.post();
	}

	@RequestMapping(value = "/my/contribution/delete.jspx")
	public String delete(Integer[] ids, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		if (ArrayUtils.isEmpty(ids)) {
			return resp.badRequest();
		}
		User user = Context.getCurrentUser(request);
		for (Integer id : ids) {
			Info bean = infoQuery.get(id);
			if (!bean.getCreator().getId().equals(user.getId())) {
				return resp.post(501, "不能删除他人的数据");
			}
			if (!bean.getStatus().equals(Info.DRAFT)
					&& !bean.getStatus().equals(Info.CONTRIBUTION)
					&& !bean.getStatus().equals(Info.REJECTION)) {
				return resp.post(501, "稿件审核中或已审核通过，不能删除");
			}
		}
		infoService.delete(ids);
		return resp.post();
	}

	@Autowired
	private NodeQueryService nodeQuery;
	@Autowired
	private InfoQueryService infoQuery;
	@Autowired
	private InfoService infoService;
}
