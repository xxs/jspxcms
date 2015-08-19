package com.jspxcms.core.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.QueryHints;

import com.jspxcms.core.domain.Spec;
import com.jspxcms.core.domaindsl.QSpec;
import com.jspxcms.core.repository.SpecDaoPlus;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * SpecDaoImpl
 * 
 * @author xiaoshi
 * 
 */
public class SpecDaoImpl implements SpecDaoPlus {
	public List<Spec> findByNumbers(String[] numbers) {
		if (ArrayUtils.isEmpty(numbers)) {
			return Collections.emptyList();
		}
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QSpec spec = QSpec.spec;
		query.from(spec);
		BooleanExpression exp = spec.number.eq(numbers[0]);
		for (int i = 1, len = numbers.length; i < len; i++) {
			exp = exp.or(spec.number.eq(numbers[i]));
		}
		query.where(exp);
		return query.list(spec);
	}
	public List<Spec> findByNodeIdAndSiteId(Integer nodeId, Integer siteId) {
		return null;
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	
}
