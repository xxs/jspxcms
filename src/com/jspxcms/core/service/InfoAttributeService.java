package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoAttribute;

/**
 * InfoAttributeService
 * 
 * @author liufang
 * 
 */
public interface InfoAttributeService {
	public List<InfoAttribute> save(Info info, Integer[] attrIds,
			Map<String, String> attrImages);

	public List<InfoAttribute> update(Info info, Integer[] attrIds,
			Map<String, String> attrImages);

	public int deleteByInfoId(Integer infoId);

	public int deleteByAttributeId(Integer attributeId);
}
