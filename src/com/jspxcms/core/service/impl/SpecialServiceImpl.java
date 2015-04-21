package com.jspxcms.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ArrayUtils;
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
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.Special;
import com.jspxcms.core.domain.SpecialCategory;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.listener.SiteDeleteListener;
import com.jspxcms.core.listener.SpecialCategoryDeleteListener;
import com.jspxcms.core.listener.UserDeleteListener;
import com.jspxcms.core.repository.SpecialDao;
import com.jspxcms.core.service.InfoSpecialService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.SpecialCategoryService;
import com.jspxcms.core.service.SpecialService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.DeleteException;

/**
 * SpecialServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class SpecialServiceImpl implements SpecialService, UserDeleteListener,
		SiteDeleteListener, SpecialCategoryDeleteListener {
	public Page<Special> findAll(Integer siteId, Map<String, String[]> params,
			Pageable pageable) {
		return dao.findAll(spec(siteId, params), pageable);
	}

	public RowSide<Special> findSide(Integer siteId,
			Map<String, String[]> params, Special bean, Integer position,
			Sort sort) {
		if (position == null) {
			return new RowSide<Special>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<Special> list = dao.findAll(spec(siteId, params), limit);
		return RowSide.create(list, bean);
	}

	private Specification<Special> spec(final Integer siteId,
			Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Special> fsp = SearchFilter.spec(filters,
				Special.class);
		Specification<Special> sp = new Specification<Special>() {
			public Predicate toPredicate(Root<Special> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Integer> siteIdPath = root.get("site").<Integer> get("id");
				Predicate siteIdPred = cb.equal(siteIdPath, siteId);
				return cb.and(fsp.toPredicate(root, query, cb), siteIdPred);
			}
		};
		return sp;
	}

	public List<Special> findList(Integer[] siteId, Integer[] categoryId,
			Date beginDate, Date endDate, Boolean isWithImage,
			Boolean isRecommend, Limitable limitable) {
		return dao.findList(siteId, categoryId, beginDate, endDate,
				isWithImage, isRecommend, limitable);
	}

	public Page<Special> findPage(Integer[] siteId, Integer[] categoryId,
			Date beginDate, Date endDate, Boolean isWithImage,
			Boolean isRecommend, Pageable pageable) {
		return dao.findPage(siteId, categoryId, beginDate, endDate,
				isWithImage, isRecommend, pageable);
	}

	public Special get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public Special save(Special bean, Integer categoryId, Integer creatorId,
			Integer siteId) {
		SpecialCategory category = specialCategoryService.get(categoryId);
		bean.setCategory(category);
		User creator = userService.get(creatorId);
		bean.setCreator(creator);
		Site site = siteService.get(siteId);
		bean.setSite(site);

		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Special update(Special bean, Integer categoryId) {
		if (categoryId != null) {
			SpecialCategory category = specialCategoryService.get(categoryId);
			bean.setCategory(category);
		}
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Special delete(Integer id) {
		Special entity = dao.findOne(id);
		infoSpecialService.deleteBySpecialId(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public Special[] delete(Integer[] ids) {
		Special[] beans = new Special[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = delete(ids[i]);
		}
		return beans;
	}

	@Transactional
	public Special refer(Integer beanId) {
		Special bean = get(beanId);
		bean.setRefers(bean.getRefers() + 1);
		return bean;
	}

	@Transactional
	public List<Special> refer(Integer[] beanIds) {
		if (ArrayUtils.isEmpty(beanIds)) {
			return Collections.emptyList();
		}
		Set<Integer> beanIdSet = new HashSet<Integer>();
		List<Special> beans = new ArrayList<Special>(beanIds.length);
		for (Integer beanId : beanIds) {
			if (!beanIdSet.contains(beanId)) {
				beans.add(refer(beanId));
				beanIdSet.add(beanId);
			}
		}
		return beans;
	}

	@Transactional
	public void derefer(Special bean) {
		bean.setRefers(bean.getRefers() - 1);
	}

	@Transactional
	public void derefer(Collection<Special> beans) {
		for (Special bean : beans) {
			derefer(bean);
		}
	}

	public void preSiteDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countBySiteId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("special.management");
			}
		}
	}

	public void preSpecialCategoryDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByCategoryId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("special.management");
			}
		}
	}

	public void preUserDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByCreatorId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("special.management");
			}
		}
	}

	private SiteService siteService;
	private UserService userService;
	private SpecialCategoryService specialCategoryService;
	private InfoSpecialService infoSpecialService;

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setSpecialCategoryService(
			SpecialCategoryService specialCategoryService) {
		this.specialCategoryService = specialCategoryService;
	}

	@Autowired
	public void setInfoSpecialService(InfoSpecialService infoSpecialService) {
		this.infoSpecialService = infoSpecialService;
	}

	private SpecialDao dao;

	@Autowired
	public void setDao(SpecialDao dao) {
		this.dao = dao;
	}
}
