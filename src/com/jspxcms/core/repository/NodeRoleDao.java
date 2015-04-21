package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.NodeRole;

public interface NodeRoleDao extends Repository<NodeRole, Integer> {
	public NodeRole findOne(Integer id);

	public NodeRole save(NodeRole bean);

	public void delete(NodeRole bean);

	// --------------------

	public List<NodeRole> findByNodeIdAndRoleId(Integer nodeId, Integer roleId);

	public List<NodeRole> findByNodeId(Integer nodeId);

	public List<NodeRole> findByRoleId(Integer roleId);

	@Modifying
	@Query("delete from NodeRole bean where bean.node.id=?1")
	public int deleteByNodeId(Integer nodeId);

	@Modifying
	@Query("delete from NodeRole bean where bean.role.id=?1")
	public int deleteByRoleId(Integer roleId);
}
