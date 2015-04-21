package com.jspxcms.core.service;

import com.jspxcms.core.domain.User;

public interface UserOrgService {
	public void save(User user, Integer[] orgIds, Integer orgId);

	public void update(User user, Integer[] orgIds, Integer orgId,
			Integer topOrgId);
}
