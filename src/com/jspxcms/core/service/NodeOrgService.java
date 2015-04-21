package com.jspxcms.core.service;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeOrg;
import com.jspxcms.core.domain.Org;

public interface NodeOrgService {
	public NodeOrg save(Node node, Org org, Boolean viewPerm);

	public void update(Node node, Integer[] viewOrgIds);
}
