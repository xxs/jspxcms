package com.jspxcms.plug.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.ApplySite;

public interface ApplySiteService {

	public Page<ApplySite> findAll(Map<String, String[]> params, Pageable pageable);

	public List<ApplySite> findAll(Map<String, String[]> params, Limitable limitable);

	public ApplySite get(Integer id);

	public ApplySite save(ApplySite bean, Integer siteId);

	public ApplySite update(ApplySite bean);

	public ApplySite delete(Integer id);

	public ApplySite[] delete(Integer[] ids);
}
