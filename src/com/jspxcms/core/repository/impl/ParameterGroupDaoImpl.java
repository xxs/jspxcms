package com.jspxcms.core.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.ejb.QueryHints;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.ParameterGroup;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domaindsl.QParameterGroup;
import com.jspxcms.core.repository.ParameterGroupDaoPlus;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * AttrDaoImpl
 * 
 * @author xiaoshi
 * 
 */
public class ParameterGroupDaoImpl implements ParameterGroupDaoPlus {
	public List<ParameterGroup> findByNodeIdAndSiteId(Integer nodeId,
			Integer siteId) {
		return null;
	}

	public List<ParameterGroup> findByNodeAndSite(Node node, Site site) {
		if (node != null && site != null) {
			return Collections.emptyList();
		}
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QParameterGroup parameterGroup = QParameterGroup.parameterGroup;
		query.from(parameterGroup);
		BooleanExpression nid = parameterGroup.node.eq(node);
		BooleanExpression sid = parameterGroup.site.eq(site);
		query.where(nid, sid);
		return query.list(parameterGroup);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

}
