package com.jspxcms.core.repository;

import java.util.List;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.Brand;

/**
 * BrandDaoPlus
 * 
 * @author xxs
 * 
 */
public interface BrandDaoPlus {
	public List<Brand> findList(Integer[] siteId, String[] type, Integer[] typeId,
			Boolean isWithLogo, Boolean isRecommend, Integer[] status, Limitable limitable);
}
