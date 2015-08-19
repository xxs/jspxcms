package com.jspxcms.core.repository;

import java.util.List;

import com.jspxcms.core.domain.NodeSpec;

public interface NodeSpecDaoPlus{

	public List<NodeSpec> findByNodeIdAndSpecId(Integer nodeId, Integer specId);

	public List<NodeSpec> findByNodeId(Integer nodeId);

	public List<NodeSpec> findBySpecId(Integer specId);
}
