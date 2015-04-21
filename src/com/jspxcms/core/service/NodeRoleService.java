package com.jspxcms.core.service;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeRole;
import com.jspxcms.core.domain.Role;

public interface NodeRoleService {
	public NodeRole save(Node node, Role role, Boolean infoPerm,
			Boolean nodePerm);

	public void update(Role role, Integer[] infoPermIds, Integer[] nodePermIds);

	public void update(Node node, Integer[] infoPermIds, Integer[] nodePermIds);
}
