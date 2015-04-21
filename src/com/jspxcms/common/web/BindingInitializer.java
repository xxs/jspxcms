package com.jspxcms.common.web;

import java.util.Date;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 转换器绑定
 * 
 * @author liufang
 * 
 */
public class BindingInitializer implements WebBindingInitializer {
	public void initBinder(WebDataBinder binder, WebRequest request) {
		DateEditor dateEditor = new DateEditor();
		binder.registerCustomEditor(Date.class, dateEditor);
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}
