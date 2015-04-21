package com.jspxcms.core.service;

import java.util.List;

import com.jspxcms.core.domain.Attribute;

/**
 * AttributeService
 * 
 * @author liufang
 * 
 */
public interface AttributeService {
	public List<Attribute> findList(Integer siteId);

	public List<Attribute> findByNumbers(String[] numbers);

	public Attribute get(Integer id);

	public boolean numberExist(String number, Integer siteId);

	public Attribute save(Attribute bean, Integer siteId);

	public Attribute update(Attribute bean);

	public Attribute[] batchUpdate(Integer[] id, String[] name,
			String[] number, Integer[] imageWidth, Integer[] imageHeight);

	public Attribute delete(Integer id);

	public Attribute[] delete(Integer[] ids);
}
