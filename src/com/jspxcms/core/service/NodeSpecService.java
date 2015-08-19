package com.jspxcms.core.service;

import java.util.List;

import com.jspxcms.core.domain.Spec;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeSpec;

public interface NodeSpecService {
	
	public List<NodeSpec> getByNodeId(Integer nodeId);
	
	public NodeSpec save(Node node, Spec spec);

	public void update(Spec spec, Integer[] nodePermIds);

	public void update(Node node, Integer[] nodePermIds);
}
