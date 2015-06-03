package com.jspxcms.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoAttr;
import com.jspxcms.core.repository.InfoAttrDao;
import com.jspxcms.core.service.AttrService;
import com.jspxcms.core.service.InfoAttrService;
import com.jspxcms.core.service.InfoQueryService;

/**
 * InfoAttrServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class InfoAttrServiceImpl implements InfoAttrService {
	@Transactional
	public List<InfoAttr> save(Info info, Integer[] attrIds) {
		List<InfoAttr> infoAttrs = new ArrayList<InfoAttr>();
		if (ArrayUtils.isNotEmpty(attrIds)) {
			InfoAttr infoAttr;
			Attr attr;
			for (Integer attrId : attrIds) {
				attr = attrService.get(attrId);
				infoAttr = new InfoAttr(info, attr);
				dao.save(infoAttr);
				infoAttrs.add(infoAttr);
			}
		}
		info.setInfoAttrss(infoAttrs);
		return infoAttrs;
	}
	
	@Transactional
	public InfoAttr save(Info info, Attr attr) {
		InfoAttr bean = new InfoAttr();
		bean.setInfo(info);
		bean.setAttr(attr);
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public void update(Attr attr, Integer[] infoIds) {
		Integer attrId = attr.getId();
		List<InfoAttr> irs = dao.getByAttrId(attrId);
		boolean contains;
		Info info = new Info();
		for (Integer infoId : infoIds) {
			contains = false;
			for (InfoAttr ir : irs) {
				info = ir.getInfo();
				if (!info.getId().equals(infoId)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				save(info, attr);
			}	
		}
	}
	
	@Transactional
	public List<InfoAttr> update(Info info, Integer[] attrIds) {
		dao.deleteByInfoId(info.getId());
		return save(info, attrIds);
	}

	public int deleteByInfoId(Integer infoId) {
		return dao.deleteByInfoId(infoId);
	}

	public int deleteByAttrId(Integer attrId) {
		return dao.deleteByAttrId(attrId);
	}
	
	public List<InfoAttr> getByInfoId(Integer infoId) {
		return dao.getByInfoId(infoId);
	}
	
	private InfoQueryService infoQueryService;
	private AttrService attrService;

	@Autowired
	public void setInfoQueryService(InfoQueryService infoQueryService) {
		this.infoQueryService = infoQueryService;
	}
	@Autowired
	public void setAttrService(AttrService attrService) {
		this.attrService = attrService;
	}

	private InfoAttrDao dao;

	@Autowired
	public void setDao(InfoAttrDao dao) {
		this.dao = dao;
	}

}
