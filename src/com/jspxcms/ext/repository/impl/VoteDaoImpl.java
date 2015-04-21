package com.jspxcms.ext.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.ejb.QueryHints;

import com.jspxcms.ext.domain.Vote;
import com.jspxcms.ext.domaindsl.QVote;
import com.jspxcms.ext.repository.VoteDaoPlus;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

public class VoteDaoImpl implements VoteDaoPlus {
	public Vote findByNumber(String number, Integer[] status, Integer siteId) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QVote bean = QVote.vote;
		query.from(bean);
		BooleanExpression exp = bean.status.eq(Vote.NOMAL_STATUS);
		if (siteId != null) {
			exp = exp.and(bean.site.id.eq(siteId));
		}
		if (ArrayUtils.isNotEmpty(status)) {
			exp = exp.and(bean.status.in(status));
		}
		if (StringUtils.isNotBlank(number)) {
			exp = exp.and(bean.number.eq(number));
		}
		query.where(exp);
		query.orderBy(bean.seq.asc(), bean.id.desc());
		query.limit(1);
		List<Vote> list = query.list(bean);
		return list.isEmpty() ? null : list.get(0);
	}

	public Vote findLatest(Integer[] status, Integer siteId) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QVote bean = QVote.vote;
		query.from(bean);
		BooleanBuilder exp = new BooleanBuilder();
		if (siteId != null) {
			exp = exp.and(bean.site.id.eq(siteId));
		}
		if (ArrayUtils.isNotEmpty(status)) {
			exp = exp.and(bean.status.in(status));
		}
		query.where(exp);
		query.orderBy(bean.seq.asc(), bean.id.desc());
		query.limit(1);
		List<Vote> list = query.list(bean);
		return list.isEmpty() ? null : list.get(0);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
