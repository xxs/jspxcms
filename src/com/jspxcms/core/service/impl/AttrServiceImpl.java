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
import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.listener.SiteDeleteListener;
import com.jspxcms.core.repository.AttrDao;
import com.jspxcms.core.service.AttrItemService;
import com.jspxcms.core.service.AttrService;
import com.jspxcms.core.service.InfoAttrService;
import com.jspxcms.core.service.NodeAttrService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.support.DeleteException;

/**
 * AttrServiceImpl
 * 
 * @author xiaoshi
 * 
 */
@Service
@Transactional(readOnly = true)
public class AttrServiceImpl implements AttrService,
		SiteDeleteListener {
	
	public RowSide<Attr> findSide(Integer siteId, Map<String, String[]> params,
			Attr bean, Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<Attr>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<Attr> list = dao.findAll(spec(siteId, params), limit);
		return RowSide.create(list, bean);
	}
	
	private Specification<Attr> spec(final Integer siteId,
			Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Attr> fsp = SearchFilter.spec(filters, Attr.class);
		Specification<Attr> sp = new Specification<Attr>() {
			public Predicate toPredicate(Root<Attr> root,
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
	public Attr save(Attr bean, Integer[] infoPermIds, Integer[] nodePermIds,String[] itemName,
			Integer siteId) {
		Site site = siteService.get(siteId);
		bean.setSite(site);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		nodeAttrService.update(bean, nodePermIds);
		infoAttrService.update(bean, infoPermIds);
		attrItemService.save(itemName,bean);
		
		return bean;
	}

	@Transactional
	public Attr update(Attr bean, Integer[] infoPermIds, Integer[] nodePermIds,Integer[] itemId,String[] itemName) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		nodeAttrService.update(bean, nodePermIds);
		infoAttrService.update(bean, infoPermIds);
		attrItemService.update(itemId,itemName,bean);
		return bean;
	}
	
	public List<Attr> findList(Integer siteId) {
		return dao.findBySiteId(siteId, new Sort("seq", "id"));
	}

	public List<Attr> findByNumbers(String[] numbers) {
		return dao.findByNumbers(numbers);
	}

	public Attr get(Integer id) {
		return dao.findOne(id);
	}

	public boolean numberExist(String number, Integer siteId) {
		return dao.countByNumber(number, siteId) > 0;
	}

	@Transactional
	public Attr save(Attr bean, Integer siteId) {
		Site site = siteService.get(siteId);
		bean.setSite(site);
		bean.applyDefaultValue();
		dao.save(bean);
		return bean;
	}

	@Transactional
	public Attr update(Attr bean) {
		bean.applyDefaultValue();
		dao.save(bean);
		return bean;
	}

	@Transactional
	public Attr[] batchUpdate(Integer[] id, String[] name,
			String[] number, Integer[] imageWidth, Integer[] imageHeight) {
		Attr[] beans = new Attr[id.length];
		for (int i = 0, len = id.length; i < len; i++) {
			beans[i] = get(id[i]);
			beans[i].setSeq(i);
			beans[i].setName(name[i]);
			beans[i].setNumber(number[i]);
			update(beans[i]);
		}
		return beans;
	}

	@Transactional
	public Attr delete(Integer id) {
		Attr entity = dao.findOne(id);
		infoAttrService.deleteByAttrId(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public Attr[] delete(Integer[] ids) {
		Attr[] beans = new Attr[ids.length];
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

	private InfoAttrService infoAttrService;

	@Autowired
	public void setInfoAttrService(
			InfoAttrService infoAttrService) {
		this.infoAttrService = infoAttrService;
	}

	private SiteService siteService;

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	
	private NodeAttrService nodeAttrService;
	private AttrItemService attrItemService;

	@Autowired
	public void setAttrItemService(AttrItemService attrItemService) {
		this.attrItemService = attrItemService;
	}
	
	@Autowired
	public void setNodeAttrService(NodeAttrService nodeAttrService) {
		this.nodeAttrService = nodeAttrService;
	}

	private AttrDao dao;

	@Autowired
	public void setDao(AttrDao dao) {
		this.dao = dao;
	}
}
