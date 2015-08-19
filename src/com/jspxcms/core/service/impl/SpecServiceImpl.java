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
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.Spec;
import com.jspxcms.core.listener.SiteDeleteListener;
import com.jspxcms.core.repository.NodeDao;
import com.jspxcms.core.repository.SpecDao;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.SpecService;
import com.jspxcms.core.service.SpecValueService;
import com.jspxcms.core.support.DeleteException;

/**
 * SpecificationServiceImpl
 * 
 * @author xxs
 * 
 */
@Service
@Transactional(readOnly = true)
public class SpecServiceImpl implements SpecService,
		SiteDeleteListener {
	
	public RowSide<Spec> findSide(Integer siteId, Map<String, String[]> params,
			Spec bean, Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<Spec>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<Spec> list = null;
		return RowSide.create(list, bean);
	}
	
	private Specification<Spec> spec(final Integer siteId,
			Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Spec> fsp = SearchFilter.spec(filters, Spec.class);
		Specification<Spec> sp = new Specification<Spec>() {
			public Predicate toPredicate(Root<Spec> root,
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
	public Spec save(Spec bean, Integer[] infoPermIds, Integer[] nodePermIds,String[] itemName,
			Integer siteId) {
		Site site = siteService.get(siteId);
		bean.setSite(site);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		specValueService.save(itemName,null, bean);
		return bean;
	}

	@Transactional
	public Spec update(Spec bean, Integer[] infoPermIds, Integer[] nodePermIds,Integer[] itemId,String[] itemName) {
		bean = dao.save(bean);
		specValueService.update(itemId, itemName,null, bean);
		return bean;
	}
	
	public List<Spec> findList(Integer siteId) {
		return dao.findBySiteId(siteId, new Sort("seq", "id"));
	}


	public Spec get(Integer id) {
		return dao.findOne(id);
	}


	@Transactional
	public Spec save(Spec bean, Integer siteId) {
		Site site = siteService.get(siteId);
		bean.setSite(site);
		bean.applyDefaultValue();
		dao.save(bean);
		return bean;
	}

	@Transactional
	public Spec update(Spec bean) {
		//bean.applyDefaultValue();
		dao.save(bean);
		return bean;
	}

	@Transactional
	public Spec[] batchUpdate(Integer[] id, String[] name) {
		Spec[] beans = new Spec[id.length];
		for (int i = 0, len = id.length; i < len; i++) {
			beans[i] = get(id[i]);
			beans[i].setSeq(i);
			beans[i].setName(name[i]);
			update(beans[i]);
		}
		return beans;
	}

	@Transactional
	public Spec delete(Integer id) {
		Spec entity = dao.findOne(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public Spec[] delete(Integer[] ids) {
		Spec[] beans = new Spec[ids.length];
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
	
	private SpecValueService specValueService;

	@Autowired
	public void setSpecValueService(SpecValueService specValueService) {
		this.specValueService = specValueService;
	}
	

	private SpecDao dao;
	
	@Autowired
	public void setDao(SpecDao dao) {
		this.dao = dao;
	}
	
	private NodeDao nodeDao;
	
	@Autowired
	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}
	
}
