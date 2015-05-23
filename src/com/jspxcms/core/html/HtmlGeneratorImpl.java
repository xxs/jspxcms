package com.jspxcms.core.html;

import static com.jspxcms.core.domain.Node.STATIC_INFO;
import static com.jspxcms.core.domain.Node.STATIC_MANUAL;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jspxcms.common.web.PathResolver;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.Task;
import com.jspxcms.core.service.TaskService;
import com.jspxcms.core.support.Constants;

import freemarker.template.Configuration;

public class HtmlGeneratorImpl implements HtmlGenerator {

	private final Logger logger = LoggerFactory
			.getLogger(HtmlGeneratorImpl.class);

	public void makeInfo(Info info) {
		if (info == null) {
			return;
		}
		Node node = info.getNode();
		int method = node.getStaticMethodOrDef();
		if (STATIC_MANUAL == method) {
			return;
		}
		final Integer infoId = info.getId();
		new Thread() {
			public void run() {
				try {
					htmlService.makeInfo(infoId, getConfig(), resolver,
							taskService, null, false);
				} catch (Exception e) {
					logger.error("make html error!", e);
				}
			}
		}.start();
	}

	public void makeNode(Node node) {
		if (node == null) {
			return;
		}
		int method = node.getStaticMethodOrDef();
		if (STATIC_MANUAL == method || STATIC_INFO == method) {
			return;
		}
		final Integer nodeId = node.getId();
		new Thread() {
			public void run() {
				try {
					htmlService.makeNode(nodeId, getConfig(), resolver,
							taskService, null, false);
				} catch (Exception e) {
					logger.error("make html error!", e);
				}
			}
		}.start();
	}

	public void makeAll(final Site site, final Integer userId) {
		final Integer siteId = site.getId();
		String name = site.getName();
		Task task = taskService.save("Site: " + name, null, Task.NODE_HTML,
				userId, siteId);
		final Integer taskId = task.getId();
		new Thread() {
			public void run() {
				try {
					htmlService.makeNode(siteId, null, true, getConfig(),
							resolver, taskService, taskId, false);
					if (taskService.isRunning(taskId)) {
						htmlService.makeInfo(siteId, null, true, getConfig(),
								resolver, taskService, taskId, false);
					}
					taskService.finish(taskId);
				} catch (Exception e) {
					taskService.abort(taskId);
					logger.error("make html error!", e);
				}
			}
		}.start();
	}

	public void makeAllSite(final Site site, final Integer userId) {
		final Integer siteId = site.getId();
		String name = site.getName();

		String realurl = servletContext.getRealPath("");
		File srcDir = new File(realurl + site.getFilesPath());
		File destDir = new File(realurl + "\\" + Constants.SHE_BACKUP_PATH
				+ "\\" + site.getNumber() + "\\_files");
		try {
			FileUtils.copyDirectory(srcDir, destDir);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("静态资源拷贝异常");
		}

		Task task = taskService.save("Site: " + name, null, Task.NODE_HTML,
				userId, siteId);
		final Integer taskId = task.getId();
		new Thread() {
			public void run() {
				try {
					htmlService.makeNode(siteId, null, true, getConfig(),
							resolver, taskService, taskId, true);
					if (taskService.isRunning(taskId)) {
						htmlService.makeInfo(siteId, null, true, getConfig(),
								resolver, taskService, taskId, true);
					}
					taskService.finish(taskId);
				} catch (Exception e) {
					taskService.abort(taskId);
					logger.error("make html error000000000000000000!", e);
				}
			}
		}.start();
	}

	public void makeInfo(final Integer siteId, final Node node,
			final boolean includeChildren, Integer userId) {
		String name = "ALL";
		if (node != null) {
			name = node.getDisplayName();
		}
		Task task = taskService.save("Node: " + name, null, Task.INFO_HTML,
				userId, siteId);
		final Integer taskId = task.getId();
		new Thread() {
			public void run() {
				try {
					htmlService.makeInfo(siteId, node, includeChildren,
							getConfig(), resolver, taskService, taskId, false);
					taskService.finish(taskId);
				} catch (Exception e) {
					taskService.abort(taskId);
					logger.error("make html error!", e);
				}
			}
		}.start();
	}

	public void makeNode(final Integer siteId, final Node node,
			final boolean includeChildren, Integer userId) {
		String name = "ALL";
		if (node != null) {
			name = node.getDisplayName();
		}
		Task task = taskService.save("Node: " + name, null, Task.NODE_HTML,
				userId, siteId);
		final Integer taskId = task.getId();
		new Thread() {
			public void run() {
				try {
					htmlService.makeNode(siteId, node, includeChildren,
							getConfig(), resolver, taskService, taskId, false);
					taskService.finish(taskId);
				} catch (Exception e) {
					taskService.abort(taskId);
					logger.error("make html error!", e);
				}
			}
		}.start();
	}

	public void deleteHtml(Node node) {
		if (node == null) {
			return;
		}
		String path = node.getUrlStatic(1, false, false, true);
		String filename = resolver.getPath(path);
		File file = new File(filename);
		if (file.exists()) {
			FileUtils.deleteQuietly(file);
		}
	}

	private Configuration getConfig() {
		return freeMarkerConfigurer.getConfiguration();
	}

	private HtmlService htmlService;
	private TaskService taskService;
	private FreeMarkerConfigurer freeMarkerConfigurer;
	private PathResolver resolver;
	private ServletContext servletContext;

	@Autowired
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Autowired
	public void setHtmlService(HtmlService htmlService) {
		this.htmlService = htmlService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Autowired
	public void setFreeMarkerConfigurer(
			FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	@Autowired
	public void setPathResolver(PathResolver resolver) {
		this.resolver = resolver;
	}
}
