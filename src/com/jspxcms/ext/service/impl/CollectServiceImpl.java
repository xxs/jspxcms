package com.jspxcms.ext.service.impl;

import java.util.ArrayList;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.listener.NodeDeleteListener;
import com.jspxcms.core.listener.SiteDeleteListener;
import com.jspxcms.core.listener.UserDeleteListener;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.DeleteException;
import com.jspxcms.ext.domain.Collect;
import com.jspxcms.ext.repository.CollectDao;
import com.jspxcms.ext.service.CollectService;

@Service
@Transactional(readOnly = true)
public class CollectServiceImpl implements CollectService, NodeDeleteListener,
		UserDeleteListener, SiteDeleteListener {
	public Page<Collect> findAll(Integer siteId, Map<String, String[]> params,
			Pageable pageable) {
		return dao.findAll(spec(siteId, params), pageable);
	}

	public RowSide<Collect> findSide(Integer siteId,
			Map<String, String[]> params, Collect bean, Integer position,
			Sort sort) {
		if (position == null) {
			return new RowSide<Collect>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<Collect> list = dao.findAll(spec(siteId, params), limit);
		return RowSide.create(list, bean);
	}

	private Specification<Collect> spec(final Integer siteId,
			Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Collect> fsp = SearchFilter.spec(filters,
				Collect.class);
		Specification<Collect> sp = new Specification<Collect>() {
			public Predicate toPredicate(Root<Collect> root,
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

	public Collect get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public Collect save(Collect bean, Integer nodeId, Integer userId,
			Integer siteId) {
		Site site = siteService.get(siteId);
		bean.setSite(site);
		Node node = nodeQuery.get(nodeId);
		bean.setNode(node);
		User user = userService.get(userId);
		bean.setUser(user);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Collect update(Collect bean, Integer nodeId) {
		Node node = nodeQuery.get(nodeId);
		bean.setNode(node);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Collect delete(Integer id) {
		Collect bean = dao.findOne(id);
		dao.delete(bean);
		return bean;
	}

	@Transactional
	public List<Collect> delete(Integer[] ids) {
		List<Collect> beans = new ArrayList<Collect>(ids.length);
		for (Integer id : ids) {
			beans.add(delete(id));
		}
		return beans;
	}

	@Transactional
	public void running(Integer id) {
		Collect bean = get(id);
		bean.setStatus(Collect.RUNNING);
	}

	@Transactional
	public void ready(Integer id) {
		Collect bean = get(id);
		bean.setStatus(Collect.READY);
	}

	public boolean isRunning(Integer id) {
		Collect bean = get(id);
		if (bean == null) {
			return false;
		}
		return bean.isRunning();
	}

	public void preNodeDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByNodeId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("collect.management");
			}
		}
	}

	public void preUserDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByUserId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("collect.management");
			}
		}
	}

	public void preSiteDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countBySiteId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("collect.management");
			}
		}
	}

	private NodeQueryService nodeQuery;
	private UserService userService;
	private SiteService siteService;

	@Autowired
	public void setNodeQuery(NodeQueryService nodeQuery) {
		this.nodeQuery = nodeQuery;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	private CollectDao dao;

	@Autowired
	public void setDao(CollectDao dao) {
		this.dao = dao;
	}
}
