package com.jspxcms.core.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.core.domain.NodeSpec;
import com.jspxcms.core.repository.NodeSpecDaoPlus;

/**
 * InfoSpecDaoImpl
 * 
 * @author xxs
 * 
 */
public class NodeSpecDaoImpl implements NodeSpecDaoPlus {
	
	private EntityManager em;

	@javax.persistence.PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	public List<NodeSpec> findByNodeId(Integer nodeId) {
		JpqlBuilder jqpl = new JpqlBuilder("from NodeSpec bean where 1=1");
		if (nodeId!=null) {
			jqpl.append(" and bean.node.id = (:nodeId)");
			jqpl.setParameter("nodeId", nodeId);
		}
		return jqpl.list(em);
	}
	@SuppressWarnings("unchecked")
	public List<NodeSpec> findBySpecId(Integer specId) {
		JpqlBuilder jqpl = new JpqlBuilder("from NodeSpec bean where 1=1");
		if (specId!=null) {
			jqpl.append(" and bean.spec.id = (:specId)");
			jqpl.setParameter("specId", specId);
		}
		return jqpl.list(em);
	}

	public List<NodeSpec> findByNodeIdAndSpecId(Integer nodeId, Integer specId) {
		return null;
	}


}
