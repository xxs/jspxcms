package com.jspxcms.ext.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.ext.domain.Ad;

public interface AdService {
	public List<Ad> findList(Integer siteId, Map<String, String[]> params,
			Sort sort);
	public List<Ad> findList(Integer siteId,Integer slotId, Map<String, String[]> params,
			Sort sort);

	public RowSide<Ad> findSide(Integer siteId, Map<String, String[]> params,
			Ad bean, Integer position, Sort sort);

	public Ad get(Integer id);

	public Ad save(Ad bean, Integer slotId, Integer siteId);

	public Ad update(Ad bean, Integer slotId);

	public Ad delete(Integer id);

	public Ad[] delete(Integer[] ids);
}
