package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.ParameterGroup;
import com.jspxcms.core.domain.Site;

/**
 * ParameterGroupService
 * 
 * @author xxs
 * 
 */
public interface ParameterGroupService {
	public List<ParameterGroup> findList(Integer siteId);

	public List<ParameterGroup> findByNodeAndSite(Node node, Site site);

	public ParameterGroup get(Integer id);

	public ParameterGroup save(ParameterGroup bean, Integer siteId);

	public ParameterGroup update(ParameterGroup bean);

	public ParameterGroup save(ParameterGroup bean, Integer[] infoPermIds,
			Integer[] nodePermIds, String[] itemName, Integer siteId);

	public ParameterGroup update(ParameterGroup bean, Integer[] infoPermIds,
			Integer[] nodePermIds, Integer[] itemId, String[] itemName);

	public RowSide<ParameterGroup> findSide(Integer siteId,
			Map<String, String[]> params, ParameterGroup bean,
			Integer position, Sort sort);

	public ParameterGroup[] batchUpdate(Integer[] id, String[] name);

	public ParameterGroup delete(Integer id);

	public ParameterGroup[] delete(Integer[] ids);
}
