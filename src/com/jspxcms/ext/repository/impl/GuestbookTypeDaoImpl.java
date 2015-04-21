package com.jspxcms.ext.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.QueryHints;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.QuerydslUtils;
import com.jspxcms.ext.domain.GuestbookType;
import com.jspxcms.ext.domaindsl.QGuestbookType;
import com.jspxcms.ext.repository.GuestbookTypeDaoPlus;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * GuestbookTypeDaoImpl
 * 
 * @author yangxing
 * 
 */
public class GuestbookTypeDaoImpl implements GuestbookTypeDaoPlus {
	public List<GuestbookType> getList(Integer[] siteId, Limitable limitable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QGuestbookType guestbookType = QGuestbookType.guestbookType;
		query.from(guestbookType);
		BooleanBuilder exp = new BooleanBuilder();
		if (ArrayUtils.isNotEmpty(siteId)) {
			exp = exp.and(guestbookType.site.id.in(siteId));
		}
		query.where(exp);
		return QuerydslUtils.list(query, guestbookType, limitable);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
