package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.NodeBrand;

public interface NodeBrandDao extends Repository<NodeBrand, Integer> {
	public NodeBrand findOne(Integer id);

	public NodeBrand save(NodeBrand bean);

	public void delete(NodeBrand bean);

	@Modifying
	@Query("delete from NodeBrand bean where bean.node.id=?1")
	public int deleteByNodeId(Integer nodeId);

	@Modifying
	@Query("delete from NodeBrand bean where bean.brand.id=?1")
	public int deleteByBrandId(Integer specId);
}
