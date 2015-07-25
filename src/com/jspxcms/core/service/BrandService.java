package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Brand;

/**
 * BrandService
 * 
 * @author xxs
 * 
 */

public interface BrandService {
	public List<Brand> findList(Integer siteId, Map<String, String[]> params);

	public RowSide<Brand> findSide(Integer siteId, Map<String, String[]> params, Brand bean, Integer position, Sort sort);

	public List<Brand> findList(Integer[] siteId, String[] type, Integer[] typeId,
			Boolean isWithLogo, Boolean isRecommend, Integer[] status, Limitable limitable);
	
	public Brand[] batchUpdate(Integer[] id);
	
	public Brand get(Integer id);

	public Brand save(Brand bean,Integer typeId,Integer siteId);

	public Brand update(Brand bean,Integer typeId);

	public Brand delete(Integer id);

	public Brand[] delete(Integer[] ids);
}
