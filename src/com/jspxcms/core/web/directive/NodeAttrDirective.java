package com.jspxcms.core.web.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jspxcms.common.freemarker.Freemarkers;
import com.jspxcms.core.domain.NodeAttr;
import com.jspxcms.core.service.NodeAttrService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * NodeAttrDirective
 * 
 * @author xxs
 * 
 */
public class NodeAttrDirective implements TemplateDirectiveModel {

	public static final String ID = "id";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		if (loopVars.length < 1) {
			throw new TemplateModelException("Loop variable is required.");
		}
		if (body == null) {
			throw new RuntimeException("missing body");
		}
		Integer id = Freemarkers.getInteger(params, ID);
		List<NodeAttr> nodeAttrs = null;
		if (id != null) {
			nodeAttrs = query.getByNodeId(id);
		} 
		loopVars[0] = env.getObjectWrapper().wrap(nodeAttrs);
		body.render(env.getOut());
	}

	@Autowired
	private NodeAttrService query;
}
