package com.jspxcms.core.repository.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.QueryHints;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.repository.SiteDaoPlus;

/**
 * SiteDaoImpl
 * 
 * @author liufang
 * 
 */
public class SiteDaoImpl implements SiteDaoPlus {
	public Site findDefault() {
		JpqlBuilder jqpl = new JpqlBuilder("from Site bean where 1=1");
		jqpl.append(" order by bean.def desc, bean.treeNumber asc");
		TypedQuery<Site> query = jqpl.createQuery(em, Site.class);
		query.setMaxResults(1);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		List<Site> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Site> findByStatus(Integer[] status) {
		JpqlBuilder jqpl = new JpqlBuilder("from Site bean where 1=1");
		if (ArrayUtils.isNotEmpty(status)) {
			jqpl.append(" and bean.status in (:status)");
			jqpl.setParameter("status", Arrays.asList(status));
		}
		jqpl.append(" order by bean.treeNumber");
		return jqpl.list(em);
	}

	private EntityManager em;

	@javax.persistence.PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
