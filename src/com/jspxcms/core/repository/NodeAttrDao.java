package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.NodeAttr;

public interface NodeAttrDao extends Repository<NodeAttr, Integer> {
	public NodeAttr findOne(Integer id);

	public NodeAttr save(NodeAttr bean);

	public void delete(NodeAttr bean);

	@Modifying
	@Query("delete from NodeAttr bean where bean.node.id=?1")
	public int deleteByNodeId(Integer nodeId);

	@Modifying
	@Query("delete from NodeAttr bean where bean.attr.id=?1")
	public int deleteByAttrId(Integer attrId);
}
