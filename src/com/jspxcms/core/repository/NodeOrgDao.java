package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.NodeOrg;

public interface NodeOrgDao extends Repository<NodeOrg, Integer> {
	public NodeOrg findOne(Integer id);

	public NodeOrg save(NodeOrg bean);

	public void delete(NodeOrg bean);

	// --------------------

	public List<NodeOrg> findByNodeId(Integer nodeId);

	@Modifying
	@Query("delete NodeOrg bean where bean.node.id = ?1")
	public int deleteByNodeId(Integer nodeId);

	@Modifying
	@Query("delete NodeOrg bean where bean.org.id = ?1")
	public int deleteByOrgId(Integer orgId);

}
