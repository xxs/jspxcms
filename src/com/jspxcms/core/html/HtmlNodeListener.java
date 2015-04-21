package com.jspxcms.core.html;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.listener.AbstractNodeListener;

/**
 * HtmlNodeListener
 * 
 * @author liufang
 * 
 */
@Component
public class HtmlNodeListener extends AbstractNodeListener {
	@Override
	public void postNodeSave(Node[] beans) {
		for (Node bean : beans) {
			generator.makeNode(bean);
		}
	}

	@Override
	public void postNodeUpdate(Node[] beans) {
		for (Node bean : beans) {
			generator.makeNode(bean);
		}
	}

	private HtmlGenerator generator;

	@Autowired
	public void setGenerator(HtmlGenerator generator) {
		this.generator = generator;
	}
}
