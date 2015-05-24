package com.jspxcms.core.repository;

import java.util.List;

import com.jspxcms.core.domain.Attr;

/**
 * AttrDaoPlus
 * 
 * @author liufang
 * 
 */
public interface AttrDaoPlus {
	public List<Attr> findByNumbers(String[] numbers);
}
