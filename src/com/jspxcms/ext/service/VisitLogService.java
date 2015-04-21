package com.jspxcms.ext.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Site;
import com.jspxcms.ext.domain.VisitLog;

public interface VisitLogService {
	public Page<VisitLog> findAll(Map<String, String[]> params,
			Pageable pageable);

	public RowSide<VisitLog> findSide(Map<String, String[]> params,
			VisitLog bean, Integer position, Sort sort);

	public List<Object[]> trafficByDate(String date, Integer siteId);

	public Page<Object[]> trafficByDate(String begin, String end,
			Integer siteId, Pageable pageable);

	public Page<Object[]> urlByDate(String begin, String end, Integer siteId,
			Pageable pageable);

	public VisitLog get(Integer id);

	public VisitLog save(String url, String referrer, String ip, String cookie,
			Site site);

	public VisitLog delete(Integer id);

	public List<VisitLog> delete(Integer[] ids);

	public long deleteByDate(String before);
}
