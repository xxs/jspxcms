package com.jspxcms.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoNode;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.repository.InfoNodeDao;
import com.jspxcms.core.service.InfoNodeService;
import com.jspxcms.core.service.NodeQueryService;

@Service
@Transactional(readOnly = true)
public class InfoNodeServiceImpl implements InfoNodeService {
	@Transactional
	public List<InfoNode> save(Info info, Integer[] nodeIds, Integer nodeId) {
		int len = ArrayUtils.getLength(nodeIds) + 1;
		List<InfoNode> infoNodes = new ArrayList<InfoNode>(len);
		info.setInfoNodes(infoNodes);
		if (len > 1) {
			for (Integer nid : nodeIds) {
				infoNodes.add(save(info, nid));
			}
		}
		infoNodes.add(save(info, nodeId));
		return infoNodes;
	}

	private InfoNode save(Info info, Integer nodeId) {
		InfoNode infoNode = new InfoNode();
		Node node = nodeQueryService.get(nodeId);
		infoNode.setNode(node);
		infoNode.setInfo(info);
		dao.save(infoNode);
		return infoNode;
	}

	@Transactional
	public List<InfoNode> update(Info info, Integer[] nodeIds, Integer nodeId) {
		dao.deleteByInfoId(info.getId());
		List<InfoNode> infoNodes = save(info, nodeIds, nodeId);
		return infoNodes;
	}

	@Transactional
	public int deleteByNodeId(Integer nodeId) {
		return dao.deleteByNodeId(nodeId);
	}

	@Transactional
	public int deleteByInfoId(Integer infoId) {
		return dao.deleteByNodeId(infoId);
	}

	private NodeQueryService nodeQueryService;

	@Autowired
	public void setNodeQueryService(NodeQueryService nodeQueryService) {
		this.nodeQueryService = nodeQueryService;
	}

	private InfoNodeDao dao;

	@Autowired
	public void setDao(InfoNodeDao dao) {
		this.dao = dao;
	}
}
