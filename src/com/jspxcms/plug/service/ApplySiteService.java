package com.jspxcms.plug.service;

import com.jspxcms.plug.domain.ApplySite;

public interface ApplySiteService {


	public ApplySite get(Integer id);

	public ApplySite save(ApplySite bean, Integer siteId);

	public ApplySite update(ApplySite bean);

	public ApplySite delete(Integer id);

	public ApplySite[] delete(Integer[] ids);
}
