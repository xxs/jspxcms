package com.jspxcms.ext.web.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jspxcms.common.freemarker.Freemarkers;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.ext.domain.AdSlot;
import com.jspxcms.ext.service.AdSlotService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class AdSlotListDirective implements TemplateDirectiveModel {
	public static final String NUMBER = "number";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
		TemplateDirectiveBody body) throws TemplateException, IOException {
		if (loopVars.length < 1) {
			throw new TemplateModelException("Loop variable is required.");
		}
		if (body == null) {
			throw new RuntimeException("missing body");
		}
		String number = Freemarkers.getString(params, NUMBER);
		Integer siteId =  ForeContext.getSiteId(env);
		List<AdSlot> list = service.findList(number, siteId);
		loopVars[0] = env.getObjectWrapper().wrap(list);
		body.render(env.getOut());
	}
	@Autowired
	private AdSlotService service;
}
