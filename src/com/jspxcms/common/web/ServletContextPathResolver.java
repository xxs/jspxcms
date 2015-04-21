package com.jspxcms.common.web;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

/**
 * ServletContext路径获取实现
 * 
 * @author liufang
 * 
 */
public class ServletContextPathResolver implements PathResolver,
		ServletContextAware {
	public String getPath(String uri) {
		if (uri == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(servletContext.getRealPath(""));
		if (!uri.startsWith("/")) {
			sb.append(File.separator);
		}
		sb.append(uri.replace('/', File.separatorChar));
		return sb.toString();
	}

	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
