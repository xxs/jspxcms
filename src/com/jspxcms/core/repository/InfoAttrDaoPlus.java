package com.jspxcms.core.repository;

import java.util.List;

import com.jspxcms.core.domain.InfoAttr;

/**
 * InfoAttrDaoPlus
 * 
 * @author xxs
 * 
 */
public interface InfoAttrDaoPlus {
	public List<InfoAttr> getByInfoId(Integer infoId);
	
	public List<InfoAttr> getByAttrId(Integer attrId);
}
