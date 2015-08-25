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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Brand;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.listener.SiteDeleteListener;
import com.jspxcms.core.repository.BrandDao;
import com.jspxcms.core.service.BrandService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.support.DeleteException;

/**
 * BrandServiceImpl
 * 
 * @author xxs
 * 
 */
@Service
@Transactional(readOnly = true)
public class BrandServiceImpl implements BrandService, SiteDeleteListener {

	public List<Brand> findList(Integer siteId, Map<String, String[]> params) {
		return dao.findAll(spec(siteId, params), new Sort("seq", "id"));
	}

	public RowSide<Brand> findSide(Integer siteId,
			Map<String, String[]> params, Brand bean, Integer position,
			Sort sort) {
		if (position == null) {
			return new RowSide<Brand>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<Brand> list = dao.findAll(spec(siteId, params), limit);
		return RowSide.create(list, bean);
	}

	private Specification<Brand> spec(final Integer siteId,
			Map<String, String[]> params) {

		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Brand> fs = SearchFilter.spec(filters, Brand.class);
		Specification<Brand> sp = new Specification<Brand>() {
			public Predicate toPredicate(Root<Brand> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fs.toPredicate(root, query, cb);
				if (siteId != null) {
					pred = cb.and(pred,
							cb.equal(root.get("site").get("id"), siteId));
				}
				return pred;
			}
		};
		return sp;
	}

	public List<Brand> findList(Integer[] siteId, String[] type,
			Integer[] typeId, Boolean isWithLogo, Boolean isRecommend,
			Integer[] status, Limitable limitable) {
		return dao.findList(siteId, type, typeId, isWithLogo, isRecommend,
				status, limitable);
	}

	public Brand get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public Brand save(Brand bean, Integer typeId, Integer siteId) {
		Site site = siteService.get(siteId);
		bean.setSite(site);
		if (StringUtils.isNotBlank(bean.getLogo())) {
			bean.setWithLogo(true);
		} else {
			bean.setWithLogo(false);
		}
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Brand update(Brand bean, Integer typeId) {
		if (StringUtils.isNotBlank(bean.getLogo())) {
			bean.setWithLogo(true);
		} else {
			bean.setWithLogo(false);
		}
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Brand[] batchUpdate(Integer[] id) {
		Brand[] beans = new Brand[id.length];
		for (int i = 0, len = id.length; i < len; i++) {
			beans[i] = get(id[i]);
			beans[i].setSeq(i);
			beans[i] = dao.save(beans[i]);
		}
		return beans;
	}

	@Transactional
	public Brand delete(Integer id) {
		Brand entity = dao.findOne(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public Brand[] delete(Integer[] ids) {
		Brand[] beans = new Brand[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = delete(ids[i]);
		}
		return beans;
	}

	public void preSiteDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countBySiteId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("brand.management");
			}
		}
	}

	private SiteService siteService;

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	private BrandDao dao;

	@Autowired
	public void setDao(BrandDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Brand> findList(Integer siteId) {
		return dao.findBySiteId(siteId, new Sort("seq", "id"));
	}

}
