package com.jspxcms.core.html;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Site;

public interface HtmlGenerator {

	public void makeInfo(Info info);

	public void makeNode(Node node);

	public void makeAll(final Site site, final Integer userId);

	public void makeInfo(Integer siteId, Node node, boolean includeChildren,
			Integer userId);

	public void makeNode(Integer siteId, Node node, boolean includeChildren,
			Integer userId);

	public void deleteHtml(Node node);
}
