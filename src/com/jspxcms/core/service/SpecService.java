package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Spec;

public interface SpecService {
	
	public List<Spec> findList(Integer siteId);

	public Spec get(Integer id);

	public Spec save(Spec bean, Integer siteId);

	public Spec update(Spec bean);
	
	public Spec save(Spec bean, Integer[] infoPermIds, Integer[] nodePermIds,String[] itemName,
			Integer siteId);

	public Spec update(Spec bean, Integer[] infoPermIds, Integer[] nodePermIds,Integer[] itemId,String[] itemName);

	public RowSide<Spec> findSide(Integer siteId, Map<String, String[]> params,
			Spec bean, Integer position, Sort sort);
	
	public Spec[] batchUpdate(Integer[] id, String[] name);

	public Spec delete(Integer id);

	public Spec[] delete(Integer[] ids);
	
}
