package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Attr;

/**
 * AttrService
 * 
 * @author xxs
 * 
 */
public interface AttrService {
	public List<Attr> findList(Integer siteId);

	public List<Attr> findByNumbers(String[] numbers);

	public Attr get(Integer id);

	public boolean numberExist(String number, Integer siteId);

	public Attr save(Attr bean, Integer siteId);

	public Attr update(Attr bean);
	
	public Attr save(Attr bean, Integer[] infoPermIds, Integer[] nodePermIds,String[] itemName,
			Integer siteId);

	public Attr update(Attr bean, Integer[] infoPermIds, Integer[] nodePermIds,Integer[] itemId,String[] itemName);

	public RowSide<Attr> findSide(Integer siteId, Map<String, String[]> params,
			Attr bean, Integer position, Sort sort);
	
	public Attr[] batchUpdate(Integer[] id, String[] name,
			String[] number, Integer[] imageWidth, Integer[] imageHeight);

	public Attr delete(Integer id);

	public Attr[] delete(Integer[] ids);
}
