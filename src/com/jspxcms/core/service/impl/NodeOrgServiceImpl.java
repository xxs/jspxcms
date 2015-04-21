package com.jspxcms.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeOrg;
import com.jspxcms.core.domain.Org;
import com.jspxcms.core.listener.NodeDeleteListener;
import com.jspxcms.core.listener.OrgDeleteListener;
import com.jspxcms.core.repository.NodeOrgDao;
import com.jspxcms.core.service.NodeOrgService;
import com.jspxcms.core.service.OrgService;

@Service
@Transactional(readOnly = true)
public class NodeOrgServiceImpl implements NodeOrgService, NodeDeleteListener,
		OrgDeleteListener {
	@Transactional
	public NodeOrg save(Node node, Org org, Boolean viewPerm) {
		NodeOrg bean = new NodeOrg();
		bean.setNode(node);
		bean.setOrg(org);
		bean.setViewPerm(viewPerm);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public void update(Node node, Integer[] viewOrgIds) {
		Integer nodeId = node.getId();
		List<Org> orgs = orgService.findList();
		List<NodeOrg> nos = dao.findByNodeId(nodeId);
		Integer orgId;
		boolean contains, viewPerm;
		for (Org org : orgs) {
			contains = false;
			orgId = org.getId();
			viewPerm = ArrayUtils.contains(viewOrgIds, orgId);
			for (NodeOrg no : nos) {
				if (no.getOrg().getId().equals(orgId)) {
					if (viewOrgIds != null) {
						no.setViewPerm(viewPerm);
					}
					contains = true;
				}
			}
			if (!contains) {
				save(node, org, viewPerm);
			}
		}
	}

	public void preNodeDelete(Integer[] ids) {
		if (ArrayUtils.isEmpty(ids)) {
			return;
		}
		for (Integer id : ids) {
			dao.deleteByNodeId(id);
		}
	}

	public void preOrgDelete(Integer[] ids) {
		if (ArrayUtils.isEmpty(ids)) {
			return;
		}
		for (Integer id : ids) {
			dao.deleteByOrgId(id);
		}
	}

	private OrgService orgService;

	@Autowired
	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	private NodeOrgDao dao;

	@Autowired
	public void setDao(NodeOrgDao dao) {
		this.dao = dao;
	}
}
