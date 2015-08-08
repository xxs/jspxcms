package com.jspxcms.core.repository;

import java.util.List;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.ParameterGroup;
import com.jspxcms.core.domain.Site;

/**
 * ParameterDaoPlus
 * 
 * @author xxs
 * 
 */
public interface ParameterGroupDaoPlus {
	public List<ParameterGroup> findByNodeIdAndSiteId(Integer nodeId, Integer siteId);
	public List<ParameterGroup> findByNodeAndSite(Node node,Site site);
}
