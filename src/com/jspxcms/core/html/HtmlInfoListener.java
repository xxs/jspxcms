package com.jspxcms.core.html;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.listener.AbstractInfoListener;

/**
 * HtmlInfoListener
 * 
 * @author liufang
 * 
 */
@Component
public class HtmlInfoListener extends AbstractInfoListener {
	@Override
	public void postInfoSave(Info bean) {
		generator.makeInfo(bean);
	}

	@Override
	public void postInfoUpdate(Info bean) {
		generator.makeInfo(bean);
	}

	@Override
	public void postInfoDelete(List<Info> beans) {
		// TODO 删除静态页
		// for(Info bean :beans) {
		// generator.makeInfo(bean);
		// }
	}

	@Override
	public void postInfoPass(List<Info> beans) {
		for (Info bean : beans) {
			generator.makeInfo(bean);
		}
	}

	@Override
	public void postInfoReject(List<Info> beans) {
		for (Info bean : beans) {
			generator.makeInfo(bean);
		}
	}

	private HtmlGenerator generator;

	@Autowired
	public void setGenerator(HtmlGenerator generator) {
		this.generator = generator;
	}
}
