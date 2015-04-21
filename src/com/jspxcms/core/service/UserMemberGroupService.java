package com.jspxcms.core.service;

import com.jspxcms.core.domain.User;

public interface UserMemberGroupService {
	public void save(User user, Integer[] groupIds, Integer groupId);

	public void update(User user, Integer[] groupIds, Integer groupId);
}
