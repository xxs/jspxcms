package com.jspxcms.core.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.listener.SiteDeleteListener;
import com.jspxcms.core.repository.AttrDao;
import com.jspxcms.core.service.AttrService;
import com.jspxcms.core.service.InfoAttrService;
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

	private AttrDao dao;

	@Autowired
	public void setDao(AttrDao dao) {
		this.dao = dao;
	}
}
