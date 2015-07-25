package com.jspxcms.core.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.QueryHints;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.QuerydslUtils;
import com.jspxcms.core.domain.Brand;
import com.jspxcms.core.domaindsl.QBrand;
import com.jspxcms.core.repository.BrandDaoPlus;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * BrandDaoImpl
 * 
 * @author yangxing
 * 
 */
public class BrandDaoImpl implements BrandDaoPlus {
	public List<Brand> findList(Integer[] siteId, String[] type,
			Integer[] typeId, Boolean isWithLogo, Boolean isRecommend,
			Integer[] status, Limitable limitable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QBrand brand = QBrand.brand;
		predicate(query, brand, siteId, type, typeId, isWithLogo,
				isRecommend, status);
		return QuerydslUtils.list(query, brand, limitable);
	}

	private void predicate(JPAQuery query, QBrand brand,
			Integer[] siteId, String[] type, Integer[] typeId,
			Boolean isWithLogo, Boolean isRecommend, Integer[] status) {
		query.from(brand);
		BooleanBuilder exp = new BooleanBuilder();
		if (ArrayUtils.isNotEmpty(siteId)) {
			exp = exp.and(brand.site.id.in(siteId));
		}
		if (isWithLogo != null) {
			exp = exp.and(brand.withLogo.eq(isWithLogo));
		}
		if (isRecommend != null) {
			exp = exp.and(brand.recommend.eq(isRecommend));
		}
		if (ArrayUtils.isNotEmpty(status)) {
			exp = exp.and(brand.status.in(status));
		}
		query.where(exp);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
