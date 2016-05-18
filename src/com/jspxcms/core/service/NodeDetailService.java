package com.jspxcms.core.service;

import java.util.Set;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeDetail;

/**
 * NodeDetailService
 * 
 * @author liufang
 * 
 */
public interface NodeDetailService {
	public void save(NodeDetail detail, Node node);
	
	public void saveBatch(Set<NodeDetail> detailSet, Node node);

	public NodeDetail update(NodeDetail detail, Node node);
}
