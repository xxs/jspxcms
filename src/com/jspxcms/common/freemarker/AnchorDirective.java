package com.jspxcms.common.freemarker;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import com.jspxcms.common.util.Strings;
import com.jspxcms.common.web.Anchor;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * Anchor FreeMarker标签
 * 
 * 生成html a标签
 * 
 * @author liufang
 * 
 */
public class AnchorDirective implements TemplateDirectiveModel {
	public static final String BEAN = "bean";
	public static final String TARGET = "target";
	public static final String LENGTH = "length";
	public static final String APPEND = "append";
	public static final String ATTRS = "attrs";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Anchor a = Freemarkers.getObject(params, BEAN, Anchor.class);

		String target = Freemarkers.getString(params, TARGET);
		if (a.getNewWindow() != null && a.getNewWindow()) {
			target = "_blank";
		}
		String append = Freemarkers.getString(params, APPEND);
		if (append == null) {
			append = "...";
		}
		Integer length = Freemarkers.getInteger(params, LENGTH);
		String attrs = Freemarkers.getString(params, ATTRS);

		StringBuilder buff = new StringBuilder();
		buff.append("<a href=\"").append(a.getUrl()).append("\"");
		String title = HtmlUtils.htmlEscape(a.getTitle());
		buff.append(" title=\"").append(title).append("\"");
		if (StringUtils.isNotBlank(target)) {
			buff.append(" target=\"").append(target).append("\"");
		}
		if (StringUtils.isNotBlank(attrs)) {
			buff.append(" ").append(attrs);
		}

		buff.append(">");
		boolean isStrong = a.getStrong() != null && a.getStrong();
		boolean isEm = a.getEm() != null && a.getEm();
		String color = a.getColor();
		if (isStrong) {
			buff.append("<strong>");
		}
		if (isEm) {
			buff.append("<em>");
		}
		if (StringUtils.isNotBlank(color)) {
			buff.append("<span style=\"color:").append(color).append(";\">");
		}
		if (length != null && length > 0) {
			title = HtmlUtils.htmlEscape(Strings.substring(a.getTitle(),
					length, append));
		}
		buff.append(title);
		if (StringUtils.isNotBlank(color)) {
			buff.append("</span>");
		}
		if (isEm) {
			buff.append("</em>");
		}
		if (isStrong) {
			buff.append("</strong>");
		}
		buff.append("</a>");
		env.getOut().write(buff.toString());
	}
}
