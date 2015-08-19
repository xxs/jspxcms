package com.jspxcms.core.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeParameterGroup;
import com.jspxcms.core.domain.ParameterGroup;
import com.jspxcms.core.listener.NodeDeleteListener;
import com.jspxcms.core.listener.ParameterGroupDeleteListener;
import com.jspxcms.core.repository.NodeDao;
import com.jspxcms.core.repository.NodeParameterGroupDao;
import com.jspxcms.core.service.NodeParameterGroupService;
import com.jspxcms.core.service.ParameterGroupService;

@Service
@Transactional(readOnly = true)
public class NodeParameterGroupServiceImpl implements NodeParameterGroupService,
		NodeDeleteListener, ParameterGroupDeleteListener {
	@Transactional
	public NodeParameterGroup save(Node node, ParameterGroup parameterGroup) {
		NodeParameterGroup bean = new NodeParameterGroup();
		bean.setNode(node);
		bean.setParameterGroup(parameterGroup);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public void update(ParameterGroup parameterGroup, Integer[] nodeIds) {
		Integer parameterGroupId = parameterGroup.getId();
		//更新前先删除之前已经绑定的栏目ID
		dao.deleteByParameterGroupId(parameterGroupId);
		System.out.println("已经执行了删除操作");
		Set<Node> nrs = parameterGroup.getNodePerms();
		Node node = null;
		boolean contains;
		for (Integer nodeId : nodeIds) {
			contains = false;
			if (nrs != null && nrs.size() > 0) {
				for (Node nr : nrs) {
					if (!nr.getId().equals(nodeId)) {
						node = nodeDao.findOne(nodeId);
						contains = true;
						break;
					}
				}
			} else {
				node = nodeDao.findOne(nodeId);
				contains = true;
			}
			if (contains) {
				save(node, parameterGroup);
			}
		}
	}

	@Transactional
	public void update(Node node, Integer[] nodePermIds) {
		List<ParameterGroup> parameterGroups = parameterGroupService.findList(node.getSite().getId());
		Set<ParameterGroup> nrs = node.getNodeParameterGroupSet();
		Integer parameterGroupId;
		boolean contains;
		for (ParameterGroup parameterGroup : parameterGroups) {
			contains = false;
			parameterGroupId = parameterGroup.getId();
			for (ParameterGroup nr : nrs) {
				if (!nr.getId().equals(parameterGroupId)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				save(node, parameterGroup);
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

	public void preParameterGroupDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.deleteByParameterGroupId(id);
			}
		}
	}

	public List<NodeParameterGroup> getByNodeId(Integer nodeId) {
		return null;
	}

	private ParameterGroupService parameterGroupService;

	@Autowired
	public void setParameterGroupService(ParameterGroupService parameterGroupService) {
		this.parameterGroupService = parameterGroupService;
	}

	private NodeDao nodeDao;

	@Autowired
	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	private NodeParameterGroupDao dao;
	
	@Autowired
	public void setDao(NodeParameterGroupDao dao) {
		this.dao = dao;
	}
}
