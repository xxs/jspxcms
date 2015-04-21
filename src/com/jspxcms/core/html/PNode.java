package com.jspxcms.core.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jspxcms.common.freemarker.Freemarkers;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.service.TaskService;
import com.jspxcms.core.support.ForeContext;

import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * PNode
 * 
 * @author liufang
 * 
 */
public abstract class PNode {
	public static void makeHtml(Node node, int max, Configuration config,
			PathResolver resolver, TaskService taskService, Integer taskId)
			throws IOException, TemplateException {
		if (node == null || !node.getGenerate()) {
			return;
		}
		Site site = node.getSite();
		Template template = config.getTemplate(node.getTemplate());
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put(FreemarkerServlet.KEY_APPLICATION, Collections.EMPTY_MAP);
		rootMap.put(FreemarkerServlet.KEY_SESSION, Collections.EMPTY_MAP);
		rootMap.put(FreemarkerServlet.KEY_REQUEST, Collections.EMPTY_MAP);
		rootMap.put(Freemarkers.KEY_PARAMETERS, Collections.EMPTY_MAP);
		rootMap.put(Freemarkers.KEY_PARAMETER_VALUES, Collections.EMPTY_MAP);
		rootMap.put("node", node);
		Integer total = 1;
		int staticPage = node.getStaticPageOrDef();
		for (int page = 1; page <= max && page <= total && page <= staticPage
				&& taskService.isRunning(taskId); page++) {
			String path = node.getUrlStatic(page, false, true);
			String filename = resolver.getPath(path);
			File file = new File(filename);
			file.getParentFile().mkdirs();
			// TODO like info:InfoText,title,text.
			rootMap.put("text", node.getText());
			String url = node.getUrlStatic(page);
			ForeContext.setData(rootMap, site, null, null, null, null, null,
					url);
			ForeContext.setPage(rootMap, page, node);
			FileOutputStream fos = null;
			Writer out = null;
			try {
				fos = new FileOutputStream(file);
				out = new OutputStreamWriter(fos, "UTF-8");
				ForeContext.resetTotalPages();
				template.process(rootMap, out);
				taskService.add(taskId, 1);
				total = ForeContext.getTotalPages();
				if (total == null || total < 1) {
					total = 1;
				}
			} finally {
				if (out != null) {
					out.flush();
					out.close();
				}
				if (fos != null) {
					fos.close();
				}
			}
		}
	}
}
