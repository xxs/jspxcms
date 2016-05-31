package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Configurable;

/**
 * SiteService
 * 
 * @author liufang
 * 
 */
public interface SiteService {
	public List<Site> findList(Map<String, String[]> params, Sort sort);

	public RowSide<Site> findSide(Map<String, String[]> params, Site bean,
			Integer position, Sort sort);

	public List<Site> findList();

	public List<Site> findByUserId(Integer userId);

	public Site findByNumber(String number);

	public Site findByDomain(String domain);

	public Site findDefault();

	public boolean numberExist(String number);

	public Site get(Integer id);

	public Site save(Site bean, Integer parentId, Integer orgId,
			Integer userId, Site srcSite);

	public Site update(Site bean);

	public Site update(Site bean, Integer parentId, Integer orgId);

	public void updateConf(Site site, Configurable conf);

	public void updateCustoms(Site site, String prefix, Map<String, String> map);

	public Site delete(Integer id);

	public Site[] delete(Integer[] ids);
	
	public Site[] forcedelete(Integer[] ids);
}
