package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoAttr;

/**
 * InfoAttrService
 * 
 * @author xxs
 * 
 */
public interface InfoAttrService {
	public List<InfoAttr> save(Info info, Integer[] attrIds,Map<String, String> values);

	public InfoAttr save(Info info, Attr attr);
	
	public List<InfoAttr> update(Info info, Integer[] attrIds,Map<String, String> values);
	
	public void update(Attr attr, Integer[] infoPermIds);

	public List<InfoAttr> getByInfoId(Integer infoId);
	
	public int deleteByInfoId(Integer infoId);

	public int deleteByAttrId(Integer attrId);
}
