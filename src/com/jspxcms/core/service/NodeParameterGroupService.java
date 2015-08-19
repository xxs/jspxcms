package com.jspxcms.core.service;

import java.util.List;

import com.jspxcms.core.domain.ParameterGroup;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeParameterGroup;

public interface NodeParameterGroupService {
	
	public List<NodeParameterGroup> getByNodeId(Integer nodeId);
	
	public NodeParameterGroup save(Node node, ParameterGroup parameterGroup);

	public void update(ParameterGroup parameterGroup, Integer[] nodePermIds);

	public void update(Node node, Integer[] nodePermIds);
}
