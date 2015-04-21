package com.jspxcms.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeMemberGroup;
import com.jspxcms.core.listener.MemberGroupDeleteListener;
import com.jspxcms.core.listener.NodeDeleteListener;
import com.jspxcms.core.repository.NodeMemberGroupDao;
import com.jspxcms.core.service.MemberGroupService;
import com.jspxcms.core.service.NodeMemberGroupService;
import com.jspxcms.core.service.NodeQueryService;

@Service
@Transactional(readOnly = true)
public class NodeMemberGroupServiceImpl implements NodeMemberGroupService,
		NodeDeleteListener, MemberGroupDeleteListener {
	@Transactional
	public NodeMemberGroup save(Node node, MemberGroup group, Boolean viewPerm,
			Boolean contriPerm, Boolean commentPerm) {
		NodeMemberGroup bean = new NodeMemberGroup();
		bean.setNode(node);
		bean.setGroup(group);
		bean.setViewPerm(viewPerm);
		bean.setContriPerm(contriPerm);
		bean.setCommentPerm(commentPerm);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public void update(MemberGroup group, Integer[] viewNodeIds,
			Integer[] contriNodeIds, Integer[] commentNodeIds, Integer siteId) {
		Integer groupId = group.getId();
		List<Node> nodes = nodeQueryService.findList(siteId, null);
		List<NodeMemberGroup> ngs = dao.findByGroupId(groupId);
		Integer nodeId;
		boolean contains, viewPerm, contriPerm, commentPerm;
		for (Node node : nodes) {
			contains = false;
			nodeId = node.getId();
			viewPerm = ArrayUtils.contains(viewNodeIds, nodeId);
			contriPerm = ArrayUtils.contains(contriNodeIds, nodeId);
			commentPerm = ArrayUtils.contains(commentNodeIds, nodeId);
			for (NodeMemberGroup ng : ngs) {
				if (ng.getNode().getId().equals(nodeId)) {
					if (viewNodeIds != null) {
						ng.setViewPerm(viewPerm);
					}
					if (contriNodeIds != null) {
						ng.setContriPerm(contriPerm);
					}
					if (commentNodeIds != null) {
						ng.setCommentPerm(commentPerm);
					}
					contains = true;
				}
			}
			if (!contains) {
				save(node, group, viewPerm, contriPerm, commentPerm);
			}
		}
	}

	@Transactional
	public void update(Node node, Integer[] viewGroupIds,
			Integer[] contriGroupIds, Integer[] commentGroupIds) {
		Integer nodeId = node.getId();
		List<MemberGroup> groups = memberGroupService.findList();
		List<NodeMemberGroup> ngs = dao.findByNodeId(nodeId);
		Integer groupId;
		boolean contains, viewPerm, contriPerm, commentPerm;
		for (MemberGroup group : groups) {
			contains = false;
			groupId = group.getId();
			viewPerm = ArrayUtils.contains(viewGroupIds, groupId);
			contriPerm = ArrayUtils.contains(contriGroupIds, groupId);
			commentPerm = ArrayUtils.contains(commentGroupIds, groupId);
			for (NodeMemberGroup ng : ngs) {
				if (ng.getGroup().getId().equals(groupId)) {
					if (viewGroupIds != null) {
						ng.setViewPerm(viewPerm);
					}
					if (contriGroupIds != null) {
						ng.setContriPerm(contriPerm);
					}
					if (commentGroupIds != null) {
						ng.setCommentPerm(commentPerm);
					}
					contains = true;
				}
			}
			if (!contains) {
				save(node, group, viewPerm, contriPerm, commentPerm);
			}
		}
	}

	public void preMemberGroupDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.deleteByGroupId(id);
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

	private NodeQueryService nodeQueryService;
	private MemberGroupService memberGroupService;

	@Autowired
	public void setNodeQueryService(NodeQueryService nodeQueryService) {
		this.nodeQueryService = nodeQueryService;
	}

	@Autowired
	public void setMemberGroupService(MemberGroupService memberGroupService) {
		this.memberGroupService = memberGroupService;
	}

	private NodeMemberGroupDao dao;

	@Autowired
	public void setDao(NodeMemberGroupDao dao) {
		this.dao = dao;
	}
}
