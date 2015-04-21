package com.jspxcms.core.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.QuerydslUtils;
import com.jspxcms.core.domain.Tag;
import com.jspxcms.core.domaindsl.QTag;
import com.jspxcms.core.repository.TagDaoPlus;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * TagDaoImpl
 * 
 * @author liufang
 * 
 */
public class TagDaoImpl implements TagDaoPlus {
	public List<Tag> findList(Integer[] siteId, Integer refers,
			Limitable limitable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QTag tag = QTag.tag;
		predicate(query, tag, siteId, refers);
		return QuerydslUtils.list(query, tag, limitable);
	}

	public Page<Tag> findPage(Integer[] siteId, Integer refers,
			Pageable pageable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QTag tag = QTag.tag;
		predicate(query, tag, siteId, refers);
		return QuerydslUtils.page(query, tag, pageable);
	}

	private void predicate(JPAQuery query, QTag tag, Integer[] siteId,
			Integer refers) {
		query.from(tag);
		BooleanBuilder exp = new BooleanBuilder();
		if (ArrayUtils.isNotEmpty(siteId)) {
			exp = exp.and(tag.site.id.in(siteId));
		}
		if (refers != null) {
			exp = exp.and(tag.refers.goe(refers));
		}
		query.where(exp);
	}

	public List<Tag> findByName(String[] names) {
		if (ArrayUtils.isEmpty(names)) {
			return Collections.emptyList();
		}
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QTag tag = QTag.tag;
		query.from(tag);
		BooleanExpression exp = tag.name.eq(names[0]);
		for (int i = 1, len = names.length; i < len; i++) {
			exp = exp.or(tag.name.eq(names[i]));
		}
		query.where(exp);
		return query.list(tag);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

}
