package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoAttr;

/**
 * InfoAttrService
 * 
 * @author liufang
 * 
 */
public interface InfoAttrService {
	public List<InfoAttr> save(Info info, Integer[] attrIds,
			Map<String, String> attrImages);

	public List<InfoAttr> update(Info info, Integer[] attrIds,
			Map<String, String> attrImages);

	public int deleteByInfoId(Integer infoId);

	public int deleteByAttrId(Integer attrId);
}
