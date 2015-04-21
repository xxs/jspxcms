package com.jspxcms.core.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.QuerydslUtils;
import com.jspxcms.core.domain.Special;
import com.jspxcms.core.domaindsl.QSpecial;
import com.jspxcms.core.repository.SpecialDaoPlus;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * SpecialDaoImpl
 * 
 * @author liufang
 * 
 */
public class SpecialDaoImpl implements SpecialDaoPlus {
	public List<Special> findList(Integer[] siteId, Integer[] categoryId,
			Date beginDate, Date endDate, Boolean isWithImage,
			Boolean isRecommend, Limitable limitable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QSpecial special = QSpecial.special;
		predicate(query, special, siteId, categoryId, beginDate, endDate,
				isWithImage, isRecommend);
		return QuerydslUtils.list(query, special, limitable);
	}

	public Page<Special> findPage(Integer[] siteId, Integer[] categoryId,
			Date beginDate, Date endDate, Boolean isWithImage,
			Boolean isRecommend, Pageable pageable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QSpecial special = QSpecial.special;
		predicate(query, special, siteId, categoryId, beginDate, endDate,
				isWithImage, isRecommend);
		return QuerydslUtils.page(query, special, pageable);
	}

	private void predicate(JPAQuery query, QSpecial special, Integer[] siteId,
			Integer[] categoryId, Date beginDate, Date endDate,
			Boolean isWithImage, Boolean isRecommend) {
		query.from(special);
		BooleanBuilder exp = new BooleanBuilder();
		if (ArrayUtils.isNotEmpty(siteId)) {
			exp = exp.and(special.site.id.in(siteId));
		}
		if (ArrayUtils.isNotEmpty(categoryId)) {
			exp = exp.and(special.category.id.in(categoryId));
		}
		if (beginDate != null) {
			exp = exp.and(special.creationDate.goe(beginDate));
		}
		if (endDate != null) {
			exp = exp.and(special.creationDate.goe(endDate));
		}
		if (isWithImage != null) {
			exp = exp.and(special.withImage.eq(isWithImage));
		}
		if (isRecommend != null) {
			exp = exp.and(special.recommend.eq(isRecommend));
		}
		query.where(exp);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
