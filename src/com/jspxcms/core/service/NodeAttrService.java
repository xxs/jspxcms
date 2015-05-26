package com.jspxcms.core.service;

import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeAttr;

public interface NodeAttrService {
	public NodeAttr save(Node node, Attr attr, Boolean infoPerm,
			Boolean nodePerm);

	public void update(Attr attr, Integer[] infoPermIds, Integer[] nodePermIds);

	public void update(Node node, Integer[] infoPermIds, Integer[] nodePermIds);
}
