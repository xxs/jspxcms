package com.jspxcms.core.web.fore;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.service.NodeBufferService;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;

/**
 * NodeController
 * 
 * @author liufang
 * 
 */
@Controller
public class NodeController {
	@RequestMapping(value = { "/", "/index.jspx" })
	public String index(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		Site site = Context.getCurrentSite(request);
		return doIndex(site, resp, messages, request, modelMap);
	}

	@RequestMapping(value = "/site_{siteNumber}.jspx")
	public String indexSite(@PathVariable String siteNumber,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(siteNumber, messages, "siteNumber")) {
			return resp.badRequest();
		}
		Site site = siteService.findByNumber(siteNumber);
		if (!Validations.exist(site, messages, "Site", siteNumber)) {
			return resp.badRequest();
		}
		Context.setCurrentSite(request, site);
		Context.setCurrentSite(site);
		return doIndex(site, resp, messages, request, modelMap);
	}

	private String doIndex(Site site, Response resp, List<String> messages,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Node node = query.findRoot(site.getId());
		if (node == null) {
			if (!Validations.exist(node, messages, "Node", "root")) {
				return resp.badRequest();
			}
		}
		modelMap.addAttribute("node", node);

		ForeContext.setData(modelMap.asMap(), request);
		String template = Servlets.getParameter(request, "template");
		if (StringUtils.isNotBlank(template)) {
			return template;
		} else {
			return node.getTemplate();
		}
	}

	@RequestMapping(value = "/node/{nodeId:[0-9]+}_{page:[0-9]+}.jspx")
	public String nodeByPagePath(@PathVariable Integer nodeId,
			@PathVariable Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		Site site = Context.getCurrentSite(request);
		return doNode(nodeId, page, site, resp, messages, request, modelMap);
	}

	@RequestMapping(value = "/site_{siteNumber}/node/{nodeId:[0-9]+}_{page:[0-9]+}.jspx")
	public String nodeByPagePathSite(@PathVariable String siteNumber,
			@PathVariable Integer nodeId, @PathVariable Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(siteNumber, messages, "siteNumber")) {
			return resp.badRequest();
		}
		Site site = siteService.findByNumber(siteNumber);
		if (!Validations.exist(site, messages, "Site", siteNumber)) {
			return resp.badRequest();
		}
		Context.setCurrentSite(request, site);
		Context.setCurrentSite(site);
		return doNode(nodeId, page, site, resp, messages, request, modelMap);
	}

	@RequestMapping(value = "/node/{nodeId:[0-9]+}.jspx")
	public String node(@PathVariable Integer nodeId, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		return nodeByPagePath(nodeId, page, request, response, modelMap);
	}

	@RequestMapping(value = "/site_{siteNumber}/node/{nodeId:[0-9]+}.jspx")
	public String nodeSite(@PathVariable String siteNumber,
			@PathVariable Integer nodeId, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		return nodeByPagePathSite(siteNumber, nodeId, page, request, response,
				modelMap);
	}

	private String doNode(Integer nodeId, Integer page, Site site,
			Response resp, List<String> messages, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Node node = query.get(nodeId);
		if (node == null) {
			if (!Validations.exist(node, messages, "Node", nodeId)) {
				return resp.badRequest();
			}
		}
		if (!node.getSite().getId().equals(site.getId())) {
			site = node.getSite();
			Context.setCurrentSite(request, site);
			Context.setCurrentSite(site);
		}
		String linkUrl = node.getLinkUrl();
		if (StringUtils.isNotBlank(linkUrl)) {
			return "redirect:" + linkUrl;
		}
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("text", node.getText());

		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page, node);
		String template = Servlets.getParameter(request, "template");
		if (StringUtils.isNotBlank(template)) {
			return template;
		} else {
			return node.getTemplate();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/node_views/{nodeId:[0-9]+}.jspx")
	public String views(@PathVariable Integer nodeId) {
		return Integer.toString(bufferService.updateViews(nodeId));
	}

	@Autowired
	private SiteService siteService;
	@Autowired
	private NodeBufferService bufferService;
	@Autowired
	private NodeQueryService query;
}
