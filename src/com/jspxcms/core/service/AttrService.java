package com.jspxcms.core.service;

import java.util.List;

import com.jspxcms.core.domain.Attr;

/**
 * AttrService
 * 
 * @author liufang
 * 
 */
public interface AttrService {
	public List<Attr> findList(Integer siteId);

	public List<Attr> findByNumbers(String[] numbers);

	public Attr get(Integer id);

	public boolean numberExist(String number, Integer siteId);

	public Attr save(Attr bean, Integer siteId);

	public Attr update(Attr bean);

	public Attr[] batchUpdate(Integer[] id, String[] name,
			String[] number, Integer[] imageWidth, Integer[] imageHeight);

	public Attr delete(Integer id);

	public Attr[] delete(Integer[] ids);
}
