package com.jspxcms.common.freemarker;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

/**
 * FreeMarker视图
 * 
 * @author liufang
 * 
 */
public class FreeMarkerViewResolver extends AbstractTemplateViewResolver {
	public FreeMarkerViewResolver() {
		setViewClass(requiredViewClass());
	}

	protected Class<FreeMarkerView> requiredViewClass() {
		return FreeMarkerView.class;
	}
}
