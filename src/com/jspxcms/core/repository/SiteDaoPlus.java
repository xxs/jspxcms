package com.jspxcms.core.repository;

import java.util.List;

import com.jspxcms.core.domain.Site;

/**
 * SiteDaoPlus
 * 
 * @author liufang
 * 
 */
public interface SiteDaoPlus {
	public Site findDefault();
	
	public void forcedelete(Site bean);

	public List<Site> findByStatus(Integer[] status);
}
