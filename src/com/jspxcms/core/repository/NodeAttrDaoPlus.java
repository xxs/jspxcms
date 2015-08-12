package com.jspxcms.core.repository;

import java.util.List;

import com.jspxcms.core.domain.NodeAttr;

public interface NodeAttrDaoPlus{

	public List<NodeAttr> findByNodeIdAndAttrId(Integer nodeId, Integer attrId);

	public List<NodeAttr> findByNodeId(Integer nodeId);

	public List<NodeAttr> findByAttrId(Integer attrId);
}
