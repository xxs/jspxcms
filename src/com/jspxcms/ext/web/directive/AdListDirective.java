package com.jspxcms.ext.web.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.jspxcms.common.freemarker.Freemarkers;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.ext.domain.Ad;
import com.jspxcms.ext.service.AdService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class AdListDirective implements TemplateDirectiveModel {
	
	/**
	 * 所属广告位ID。整型。
	 */
	public static final String SLOT_ID = "slotId";
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
		TemplateDirectiveBody body) throws TemplateException, IOException {
		if (loopVars.length < 1) {
			throw new TemplateModelException("Loop variable is required.");
		}
		if (body == null) {
			throw new RuntimeException("missing body");
		}
		Integer siteId = ForeContext.getSiteId(env);
		Integer[] slotId = Freemarkers.getIntegers(params, SLOT_ID);

		Sort defSort = new Sort(Direction.ASC, "seq", "id");
		List<Ad> list = service.findList(siteId,slotId[0],null,defSort);
		loopVars[0] = env.getObjectWrapper().wrap(list);
		body.render(env.getOut());
	}
	@Autowired
	private AdService service;
}
