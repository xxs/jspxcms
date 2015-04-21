package com.jspxcms.ext.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.ext.domain.Collect;

public interface CollectService {
	public Page<Collect> findAll(Integer siteId, Map<String, String[]> params,
			Pageable pageable);

	public RowSide<Collect> findSide(Integer siteId,
			Map<String, String[]> params, Collect bean, Integer position,
			Sort sort);

	public Collect get(Integer id);

	public Collect save(Collect bean, Integer nodeId, Integer userId,
			Integer siteId);

	public Collect update(Collect bean, Integer nodeId);

	public Collect delete(Integer id);

	public List<Collect> delete(Integer[] ids);

	public void running(Integer id);

	public void ready(Integer id);

	public boolean isRunning(Integer id);

}
