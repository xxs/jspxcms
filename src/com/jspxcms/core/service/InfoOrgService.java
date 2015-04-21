package com.jspxcms.core.service;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoOrg;
import com.jspxcms.core.domain.Org;

public interface InfoOrgService {
	public InfoOrg save(Info info, Org org, Boolean viewPerm);

	public void update(Info info, Integer[] viewOrgIds);
}
