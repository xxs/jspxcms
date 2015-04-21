package com.jspxcms.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeRole;
import com.jspxcms.core.domain.Role;
import com.jspxcms.core.listener.NodeDeleteListener;
import com.jspxcms.core.listener.RoleDeleteListener;
import com.jspxcms.core.repository.NodeRoleDao;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.service.NodeRoleService;
import com.jspxcms.core.service.RoleService;

@Service
@Transactional(readOnly = true)
public class NodeRoleServiceImpl implements NodeRoleService,
		NodeDeleteListener, RoleDeleteListener {
	@Transactional
	public NodeRole save(Node node, Role role, Boolean infoPerm,
			Boolean nodePerm) {
		NodeRole bean = new NodeRole();
		bean.setNode(node);
		bean.setRole(role);
		bean.setInfoPerm(infoPerm);
		bean.setNodePerm(nodePerm);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public void update(Role role, Integer[] infoPermIds, Integer[] nodePermIds) {
		Integer siteId = role.getSite().getId();
		Integer roleId = role.getId();
		List<Node> nodes = nodeQueryService.findList(siteId, null);
		List<NodeRole> nrs = dao.findByRoleId(roleId);
		Integer nodeId;
		boolean contains, infoPerm, nodePerm;
		for (Node node : nodes) {
			contains = false;
			nodeId = node.getId();
			infoPerm = ArrayUtils.contains(infoPermIds, nodeId);
			nodePerm = ArrayUtils.contains(nodePermIds, nodeId);
			for (NodeRole nr : nrs) {
				if (nr.getNode().getId().equals(nodeId)) {
					if (infoPermIds != null) {
						nr.setInfoPerm(infoPerm);
					}
					if (nodePermIds != null) {
						nr.setNodePerm(nodePerm);
					}
					contains = true;
					break;
				}
			}
			if (!contains) {
				save(node, role, infoPerm, nodePerm);
			}
		}
	}

	@Transactional
	public void update(Node node, Integer[] infoPermIds, Integer[] nodePermIds) {
		Integer nodeId = node.getId();
		List<Role> roles = roleService.findList(node.getSite().getId());
		List<NodeRole> nrs = dao.findByNodeId(nodeId);
		Integer roleId;
		boolean contains, infoPerm, nodePerm;
		for (Role role : roles) {
			contains = false;
			roleId = role.getId();
			infoPerm = ArrayUtils.contains(infoPermIds, roleId);
			nodePerm = ArrayUtils.contains(nodePermIds, roleId);
			for (NodeRole nr : nrs) {
				if (nr.getRole().getId().equals(roleId)) {
					if (infoPermIds != null) {
						nr.setInfoPerm(infoPerm);
					}
					if (nodePermIds != null) {
						nr.setNodePerm(nodePerm);
					}
					contains = true;
					break;
				}
			}
			if (!contains) {
				save(node, role, infoPerm, nodePerm);
			}
		}
	}

	public void preNodeDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.deleteByNodeId(id);
			}
		}
	}

	public void preRoleDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.deleteByRoleId(id);
			}
		}
	}

	private NodeQueryService nodeQueryService;
	private RoleService roleService;

	@Autowired
	public void setNodeQueryService(NodeQueryService nodeQueryService) {
		this.nodeQueryService = nodeQueryService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private NodeRoleDao dao;

	@Autowired
	public void setDao(NodeRoleDao dao) {
		this.dao = dao;
	}
}
