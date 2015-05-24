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
	public List<InfoAttr> save(Info info, Integer[] attrIds,
			Map<String, String> attrImages) {
		List<InfoAttr> infoAttrs = new ArrayList<InfoAttr>();
		if (ArrayUtils.isNotEmpty(attrIds)) {
			InfoAttr infoAttr;
			Attr attr;
			String image;
			for (Integer attrId : attrIds) {
				attr = attrService.get(attrId);
				image = attrImages.get(attrId.toString());
				image = StringUtils.trimToNull(image);
				infoAttr = new InfoAttr(info, attr, image);
				dao.save(infoAttr);
				infoAttrs.add(infoAttr);
			}
		}
		info.setInfoAttrss(infoAttrs);
		return infoAttrs;
	}

	@Transactional
	public List<InfoAttr> update(Info info, Integer[] attrIds,
			Map<String, String> attrImages) {
		dao.deleteByInfoId(info.getId());
		return save(info, attrIds, attrImages);
	}

	public int deleteByInfoId(Integer infoId) {
		return dao.deleteByInfoId(infoId);
	}

	public int deleteByAttrId(Integer attrId) {
		return dao.deleteByAttrId(attrId);
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
