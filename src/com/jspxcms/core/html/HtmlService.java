package com.jspxcms.core.html;

import java.io.IOException;

import com.jspxcms.common.web.PathResolver;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.service.TaskService;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * HtmlService
 * 
 * @author liufang
 * 
 */
public interface HtmlService {
	public void makeInfo(Integer infoId, Configuration config,
			PathResolver resolver, TaskService taskService, Integer taskId,Boolean isAllSite)
			throws IOException, TemplateException;

	public void makeNode(Integer nodeId, Configuration config,
			PathResolver resolver, TaskService taskService, Integer taskId,Boolean isAllSite)
			throws IOException, TemplateException;

	public int makeNode(Integer siteId, Node node, boolean includeChildren,
			Configuration config, PathResolver resolver,
			TaskService taskService, Integer taskId,Boolean isAllSite) throws IOException,
			TemplateException;

	public int makeInfo(Integer siteId, Node node, boolean includeChildren,
			Configuration config, PathResolver resolver,
			TaskService taskService, Integer taskId,Boolean isAllSite) throws IOException,
			TemplateException;
}
