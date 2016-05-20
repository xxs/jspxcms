package com.jspxcms.plug.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.plug.domain.ApplySite;
import com.jspxcms.plug.domain.Resume;
import com.jspxcms.plug.repository.ApplySiteDao;
import com.jspxcms.plug.service.ApplySiteService;

@Service
@Transactional(readOnly = true)
public class ApplySiteServiceImpl implements ApplySiteService {
	
	public Page<ApplySite> findAll(Map<String, String[]> params,
			Pageable pageable) {
		return dao.findAll(spec(params), pageable);
	}
	
	private Specification<ApplySite> spec(Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<ApplySite> fsp = SearchFilter.spec(filters,
				ApplySite.class);
		Specification<ApplySite> sp = new Specification<ApplySite>() {
			public Predicate toPredicate(Root<ApplySite> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				return pred;
			}
		};
		return sp;
	}

	public List<ApplySite> findAll(Map<String, String[]> params,Limitable limitable) {
		return dao.findAll(spec(params),limitable);
	}
	
	public ApplySite get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public ApplySite save(ApplySite bean, Integer siteId) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public ApplySite update(ApplySite bean) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public ApplySite delete(Integer id) {
		ApplySite entity = dao.findOne(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public ApplySite[] delete(Integer[] ids) {
		ApplySite[] beans = new ApplySite[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = delete(ids[i]);
		}
		return beans;
	}

	private ApplySiteDao dao;

	@Autowired
	public void setDao(ApplySiteDao dao) {
		this.dao = dao;
	}
}
