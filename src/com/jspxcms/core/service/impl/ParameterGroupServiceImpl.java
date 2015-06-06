package com.jspxcms.core.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.ParameterGroup;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.listener.SiteDeleteListener;
import com.jspxcms.core.repository.NodeDao;
import com.jspxcms.core.repository.ParameterDao;
import com.jspxcms.core.repository.ParameterGroupDao;
import com.jspxcms.core.service.ParameterGroupService;
import com.jspxcms.core.service.ParameterService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.support.DeleteException;

/**
 * ParameterGroupServiceImpl
 * 
 * @author xxs
 * 
 */
@Service
@Transactional(readOnly = true)
public class ParameterGroupServiceImpl implements ParameterGroupService,
		SiteDeleteListener {
	
	public RowSide<ParameterGroup> findSide(Integer siteId, Map<String, String[]> params,
			ParameterGroup bean, Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<ParameterGroup>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<ParameterGroup> list = null;
		return RowSide.create(list, bean);
	}
	
	private Specification<ParameterGroup> spec(final Integer siteId,
			Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<ParameterGroup> fsp = SearchFilter.spec(filters, ParameterGroup.class);
		Specification<ParameterGroup> sp = new Specification<ParameterGroup>() {
			public Predicate toPredicate(Root<ParameterGroup> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				if (siteId != null) {
					pred = cb.and(pred, cb.equal(root.get("site")
							.<Integer> get("id"), siteId));
				}
				return pred;
			}
		};
		return sp;
	}
	
	@Transactional
	public ParameterGroup save(ParameterGroup bean, Integer[] infoPermIds, Integer[] nodePermIds,String[] itemName,
			Integer siteId) {
		Site site = siteService.get(siteId);
		bean.setNode(nodeDao.findOne(nodePermIds[0]));
		bean.setSite(site);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		parameterService.save(itemName, bean);
		return bean;
	}

	@Transactional
	public ParameterGroup update(ParameterGroup bean, Integer[] infoPermIds, Integer[] nodePermIds,Integer[] itemId,String[] itemName) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		parameterService.update(itemId, itemName, bean);
		return bean;
	}
	
	public List<ParameterGroup> findList(Integer siteId) {
		return dao.findBySiteId(siteId, new Sort("seq", "id"));
	}


	public ParameterGroup get(Integer id) {
		return dao.findOne(id);
	}


	@Transactional
	public ParameterGroup save(ParameterGroup bean, Integer siteId) {
		Site site = siteService.get(siteId);
		bean.setSite(site);
		bean.applyDefaultValue();
		dao.save(bean);
		return bean;
	}

	@Transactional
	public ParameterGroup update(ParameterGroup bean) {
		bean.applyDefaultValue();
		dao.save(bean);
		return bean;
	}

	@Transactional
	public ParameterGroup[] batchUpdate(Integer[] id, String[] name) {
		ParameterGroup[] beans = new ParameterGroup[id.length];
		for (int i = 0, len = id.length; i < len; i++) {
			beans[i] = get(id[i]);
			beans[i].setSeq(i);
			beans[i].setName(name[i]);
			update(beans[i]);
		}
		return beans;
	}

	@Transactional
	public ParameterGroup delete(Integer id) {
		ParameterGroup entity = dao.findOne(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public ParameterGroup[] delete(Integer[] ids) {
		ParameterGroup[] beans = new ParameterGroup[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = delete(ids[i]);
		}
		return beans;
	}

	public void preSiteDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countBySiteId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("attr.management");
			}
		}

	}

	private SiteService siteService;
	
	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	
	private ParameterService parameterService;

	@Autowired
	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}
	

	private ParameterGroupDao dao;
	
	@Autowired
	public void setDao(ParameterGroupDao dao) {
		this.dao = dao;
	}
	
	private NodeDao nodeDao;
	
	@Autowired
	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}
	
	private ParameterDao parameterDao;

	@Autowired
	public void setParameterDao(ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}
}
