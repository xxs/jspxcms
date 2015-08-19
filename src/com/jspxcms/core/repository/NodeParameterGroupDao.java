package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.NodeParameterGroup;

public interface NodeParameterGroupDao extends Repository<NodeParameterGroup, Integer> {
	public NodeParameterGroup findOne(Integer id);

	public NodeParameterGroup save(NodeParameterGroup bean);

	public void delete(NodeParameterGroup bean);

	@Modifying
	@Query("delete from NodeParameterGroup bean where bean.node.id=?1")
	public int deleteByNodeId(Integer nodeId);

	@Modifying
	@Query("delete from NodeParameterGroup bean where bean.parameterGroup.id=?1")
	public int deleteByParameterGroupId(Integer parameterGroupId);
}
