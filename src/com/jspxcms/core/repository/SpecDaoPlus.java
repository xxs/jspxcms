package com.jspxcms.core.repository;

import java.util.List;

import com.jspxcms.core.domain.Spec;

/**
 * SpecDaoPlus
 * 
 * @author xxs
 * 
 */
public interface SpecDaoPlus {
	public List<Spec> findByNumbers(String[] numbers);

	public List<Spec> findByNodeIdAndSiteId(Integer nodeId, Integer siteId);
}
