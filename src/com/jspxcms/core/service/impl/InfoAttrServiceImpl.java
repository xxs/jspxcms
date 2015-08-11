package com.jspxcms.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoAttr;
import com.jspxcms.core.repository.InfoAttrDao;
import com.jspxcms.core.service.AttrService;
import com.jspxcms.core.service.InfoAttrService;

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
	public List<InfoAttr> save(Info info, Integer[] attrIds, Map<String, String> attrValues) {
		List<InfoAttr> infoAttrs = new ArrayList<InfoAttr>();
		if (ArrayUtils.isNotEmpty(attrIds)) {
			InfoAttr infoAttr;
			Attr attr;
			String value;
			for (Integer attrId : attrIds) {
				attr = attrService.get(attrId);
				value = attrValues.get(attrIds.toString());
				value = StringUtils.trimToNull(value);
				infoAttr = new InfoAttr(info, attr, value);
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
		System.out.println("暂未实现此功能");
	}

	@Transactional
	public List<InfoAttr> update(Info info, Integer[] attrIds, Map<String, String> attrValues) {
		dao.deleteByInfoId(info.getId());
		return save(info, attrIds, attrValues);
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

	private AttrService attrService;

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
