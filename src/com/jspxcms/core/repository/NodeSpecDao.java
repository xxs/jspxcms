package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.NodeSpec;

public interface NodeSpecDao extends Repository<NodeSpec, Integer> {
	public NodeSpec findOne(Integer id);

	public NodeSpec save(NodeSpec bean);

	public void delete(NodeSpec bean);

	@Modifying
	@Query("delete from NodeSpec bean where bean.node.id=?1")
	public int deleteByNodeId(Integer nodeId);

	@Modifying
	@Query("delete from NodeSpec bean where bean.spec.id=?1")
	public int deleteBySpecId(Integer specId);
}
