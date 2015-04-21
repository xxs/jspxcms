package com.jspxcms.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Info;

/**
 * InfoQueryService
 * 
 * @author liufang
 * 
 */
public interface InfoQueryService {
	public Page<Info> findAll(Integer siteId, Integer mainNodeId,
			Integer nodeId, String treeNumber, Integer userId,
			boolean allInfoPerm, int infoRightType, String status,
			Map<String, String[]> params, Pageable pageable);

	public RowSide<Info> findSide(Integer siteId, Integer mainNodeId,
			Integer nodeId, String treeNumber, Integer userId,
			boolean allInfoPerm, int infoPermType, String status,
			Map<String, String[]> params, Info bean, Integer position, Sort sort);

	public List<Info> findAll(Iterable<Integer> ids);

	public List<Info> findList(Integer[] nodeId, Integer[] attrId,
			Integer[] specialId, Integer[] tagId, Integer[] siteId,
			Integer[] mainNodeId, Integer[] userId, Integer[] viewGroupId,
			Integer[] viewOrgId, String[] treeNumber, String[] specialTitle,
			String[] tagName, Integer[] priority, Date beginDate, Date endDate,
			String[] title, Integer[] includeId, Integer[] excludeId,
			Integer[] excludeMainNodeId, String[] excludeTreeNumber,
			Boolean isWithImage, String[] status, Limitable limitable);

	public Page<Info> findPage(Integer[] nodeId, Integer[] attrId,
			Integer[] specialId, Integer[] tagId, Integer[] siteId,
			Integer[] mainNodeId, Integer[] userId, Integer[] viewGroupId,
			Integer[] viewOrgId, String[] treeNumber, String[] specialTitle,
			String[] tagName, Integer[] priority, Date beginDate, Date endDate,
			String[] title, Integer[] includeId, Integer[] excludeId,
			Integer[] excludeMainNodeId, String[] excludeTreeNumber,
			Boolean isWithImage, String[] status, Pageable pageable);

	public Info findNext(Integer id, boolean bySite);

	public Info findPrev(Integer id, boolean bySite);

	public Info get(Integer id);
}
