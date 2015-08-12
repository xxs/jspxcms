package com.jspxcms.core.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.core.domain.NodeAttr;
import com.jspxcms.core.repository.NodeAttrDaoPlus;

/**
 * InfoAttrDaoImpl
 * 
 * @author xxs
 * 
 */
public class NodeAttrDaoImpl implements NodeAttrDaoPlus {
	
	private EntityManager em;

	@javax.persistence.PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	public List<NodeAttr> findByNodeId(Integer nodeId) {
		JpqlBuilder jqpl = new JpqlBuilder("from NodeAttr bean where 1=1");
		if (nodeId!=null) {
			jqpl.append(" and bean.node.id = (:nodeId)");
			jqpl.setParameter("nodeId", nodeId);
		}
		return jqpl.list(em);
	}
	@SuppressWarnings("unchecked")
	public List<NodeAttr> findByAttrId(Integer attrId) {
		JpqlBuilder jqpl = new JpqlBuilder("from NodeAttr bean where 1=1");
		if (attrId!=null) {
			jqpl.append(" and bean.attr.id = (:attrId)");
			jqpl.setParameter("attrId", attrId);
		}
		return jqpl.list(em);
	}

	public List<NodeAttr> findByNodeIdAndAttrId(Integer nodeId, Integer attrId) {
		return null;
	}


}
