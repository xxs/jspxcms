package com.jspxcms.core.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.QueryHints;

import com.jspxcms.core.domain.Attribute;
import com.jspxcms.core.domaindsl.QAttribute;
import com.jspxcms.core.repository.AttributeDaoPlus;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * AttributeDaoImpl
 * 
 * @author liufang
 * 
 */
public class AttributeDaoImpl implements AttributeDaoPlus {
	public List<Attribute> findByNumbers(String[] numbers) {
		if (ArrayUtils.isEmpty(numbers)) {
			return Collections.emptyList();
		}
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QAttribute attribute = QAttribute.attribute;
		query.from(attribute);
		BooleanExpression exp = attribute.number.eq(numbers[0]);
		for (int i = 1, len = numbers.length; i < len; i++) {
			exp = exp.or(attribute.number.eq(numbers[i]));
		}
		query.where(exp);
		return query.list(attribute);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
