package com.jspxcms.core.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.jspxcms.core.domain.Model;
import com.jspxcms.core.domaindsl.QModel;
import com.jspxcms.core.repository.ModelDaoPlus;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * ModelDaoImpl
 * 
 * @author liufang
 * 
 */
public class ModelDaoImpl implements ModelDaoPlus {
	public List<Model> findList(Integer siteId, String type) {
		JPAQuery query = new JPAQuery(this.em);
		QModel model = QModel.model;
		query.from(model);
		BooleanBuilder exp = new BooleanBuilder();
		exp = exp.and(model.site.id.eq(siteId));
		if (type != null) {
			exp = exp.and(model.type.eq(type));
		}
		query.where(exp);
		query.orderBy(model.seq.asc());
		return query.list(model);
	}

	public Model findDefault(Integer siteId, String type) {
		JPAQuery query = new JPAQuery(this.em);
		QModel model = QModel.model;
		query.from(model);
		BooleanBuilder exp = new BooleanBuilder();
		if (siteId != null) {
			exp = exp.and(model.site.id.eq(siteId));
		}
		exp = exp.and(model.type.eq(type));
		query.where(exp);
		query.orderBy(model.seq.asc());
		query.limit(1);
		List<Model> list = query.list(model);
		return !list.isEmpty() ? list.get(0) : null;
	}

	private EntityManager em;

	@javax.persistence.PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
