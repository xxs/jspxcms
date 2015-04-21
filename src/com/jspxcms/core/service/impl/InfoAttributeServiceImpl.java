package com.jspxcms.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Attribute;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoAttribute;
import com.jspxcms.core.repository.InfoAttributeDao;
import com.jspxcms.core.service.AttributeService;
import com.jspxcms.core.service.InfoAttributeService;

/**
 * InfoAttributeServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class InfoAttributeServiceImpl implements InfoAttributeService {
	@Transactional
	public List<InfoAttribute> save(Info info, Integer[] attrIds,
			Map<String, String> attrImages) {
		List<InfoAttribute> infoAttrs = new ArrayList<InfoAttribute>();
		if (ArrayUtils.isNotEmpty(attrIds)) {
			InfoAttribute infoAttr;
			Attribute attr;
			String image;
			for (Integer attrId : attrIds) {
				attr = attributeService.get(attrId);
				image = attrImages.get(attrId.toString());
				image = StringUtils.trimToNull(image);
				infoAttr = new InfoAttribute(info, attr, image);
				dao.save(infoAttr);
				infoAttrs.add(infoAttr);
			}
		}
		info.setInfoAttrs(infoAttrs);
		return infoAttrs;
	}

	@Transactional
	public List<InfoAttribute> update(Info info, Integer[] attrIds,
			Map<String, String> attrImages) {
		dao.deleteByInfoId(info.getId());
		return save(info, attrIds, attrImages);
	}

	public int deleteByInfoId(Integer infoId) {
		return dao.deleteByInfoId(infoId);
	}

	public int deleteByAttributeId(Integer attributeId) {
		return dao.deleteByAttributeId(attributeId);
	}

	private AttributeService attributeService;

	@Autowired
	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	private InfoAttributeDao dao;

	@Autowired
	public void setDao(InfoAttributeDao dao) {
		this.dao = dao;
	}
}
