package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.Node;

/**
 * NodeDaoPlus
 * 
 * @author liufang
 * 
 */
public interface NodeDaoPlus {
	public List<Node> findList(Integer[] siteId, Integer parentId,
			String treeNumber, Boolean isRealNode, Boolean isHidden,
			Limitable limitable);

	public Page<Node> findPage(Integer[] siteId, Integer parentId,
			String treeNumber, Boolean isRealNode, Boolean isHidden,
			Pageable pageable);

	public List<Node> findByNumbersLike(String[] numbers);

	public List<Node> findByNumbers(String[] numbers);
}
