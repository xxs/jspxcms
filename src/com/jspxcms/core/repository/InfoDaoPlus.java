package com.jspxcms.core.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.Info;

/**
 * InfoDaoPlus
 * 
 * @author liufang
 * 
 */
public interface InfoDaoPlus {
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

	public Info findNext(Integer siteId, Integer nodeId, Integer id,
			Date publishDate);

	public Info findPrev(Integer siteId, Integer nodeId, Integer id,
			Date publishDate);
}
