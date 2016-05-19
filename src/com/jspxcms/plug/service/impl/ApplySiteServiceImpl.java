package com.jspxcms.plug.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.plug.domain.ApplySite;
import com.jspxcms.plug.repository.ApplySiteDao;
import com.jspxcms.plug.service.ApplySiteService;

@Service
@Transactional(readOnly = true)
public class ApplySiteServiceImpl implements ApplySiteService {
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
