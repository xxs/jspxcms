package com.jspxcms.core.html;

import static com.jspxcms.core.domain.Node.STATIC_INFO_NODE;
import static com.jspxcms.core.domain.Node.STATIC_INFO_NODE_PARENT;
import static com.jspxcms.core.domain.Node.STATIC_INFO_NODE_PARENT_LIST;
import static com.jspxcms.core.domain.Node.STATIC_MANUAL;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.web.PathResolver;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.service.TaskService;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * HtmlServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional
public class HtmlServiceImpl implements HtmlService {
	public void makeInfo(Integer infoId, Configuration config,
			PathResolver resolver, TaskService taskService, Integer taskId)
			throws IOException, TemplateException {
		Info info = infoQuery.get(infoId);
		if (info == null) {
			return;
		}
		Node node = info.getNode();
		int method = node.getStaticMethodOrDef();
		if (STATIC_MANUAL != method) {
			PInfo.makeHtml(info, config, resolver, taskService, taskId);
			makeNode(node, config, resolver, taskService, taskId);
		}
	}

	public void makeNode(Integer nodeId, Configuration config,
			PathResolver resolver, TaskService taskService, Integer taskId)
			throws IOException, TemplateException {
		Node node = nodeQuery.get(nodeId);
		if (node == null) {
			return;
		}
		makeNode(node, config, resolver, taskService, taskId);
	}

	private void makeNode(Node node, Configuration config,
			PathResolver resolver, TaskService taskService, Integer taskId)
			throws IOException, TemplateException {
		int method = node.getStaticMethodOrDef();
		int max = 1;
		if (STATIC_INFO_NODE_PARENT_LIST == method) {
			max = Integer.MAX_VALUE;
		}
		if (STATIC_INFO_NODE_PARENT == method
				|| STATIC_INFO_NODE_PARENT_LIST == method) {
			while (node != null) {
				PNode.makeHtml(node, max, config, resolver, taskService, taskId);
				node = node.getParent();
			}
		} else if (STATIC_INFO_NODE == method) {
			PNode.makeHtml(node, max, config, resolver, taskService, taskId);
		} else {
			// do nothing
		}
	}

	public int makeNode(Integer siteId, Node node, boolean includeChildren,
			Configuration config, PathResolver resolver,
			TaskService taskService, Integer taskId) throws IOException,
			TemplateException {
		Integer nodeId = null;
		String treeNumber = null;
		if (node != null) {
			if (includeChildren) {
				treeNumber = node.getTreeNumber();
			} else {
				nodeId = node.getId();
			}
		}
		return dao.makeNode(siteId, nodeId, treeNumber, config, resolver,
				taskService, taskId);
	}

	public int makeInfo(Integer siteId, Node node, boolean includeChildren,
			Configuration config, PathResolver resolver,
			TaskService taskService, Integer taskId) throws IOException,
			TemplateException {
		Integer nodeId = null;
		String treeNumber = null;
		if (node != null) {
			if (includeChildren) {
				treeNumber = node.getTreeNumber();
			} else {
				nodeId = node.getId();
			}
		}
		return dao.makeInfo(siteId, nodeId, treeNumber, config, resolver,
				taskService, taskId);
	}

	private InfoQueryService infoQuery;
	private NodeQueryService nodeQuery;

	@Autowired
	public void setInfoQuery(InfoQueryService infoQuery) {
		this.infoQuery = infoQuery;
	}

	@Autowired
	public void setNodeQuery(NodeQueryService nodeQuery) {
		this.nodeQuery = nodeQuery;
	}

	private HtmlDao dao;

	@Autowired
	public void setHtmlDao(HtmlDao dao) {
		this.dao = dao;
	}

}
