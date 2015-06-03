package com.jspxcms.core.service;

import java.util.List;

import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeAttr;

public interface NodeAttrService {
	
	public List<NodeAttr> getByNodeId(Integer nodeId);
	
	public NodeAttr save(Node node, Attr attr);

	public void update(Attr attr, Integer[] nodePermIds);

	public void update(Node node, Integer[] nodePermIds);
}
