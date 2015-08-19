package com.jspxcms.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeSpec;
import com.jspxcms.core.domain.Spec;
import com.jspxcms.core.listener.NodeDeleteListener;
import com.jspxcms.core.listener.SpecDeleteListener;
import com.jspxcms.core.repository.NodeDao;
import com.jspxcms.core.repository.NodeSpecDao;
import com.jspxcms.core.repository.NodeSpecDaoPlus;
import com.jspxcms.core.service.NodeSpecService;
import com.jspxcms.core.service.SpecService;

@Service
@Transactional(readOnly = true)
public class NodeSpecServiceImpl implements NodeSpecService,
		NodeDeleteListener, SpecDeleteListener {
	@Transactional
	public NodeSpec save(Node node, Spec attr) {
		NodeSpec bean = new NodeSpec();
		bean.setNode(node);
		bean.setSpec(attr);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public void update(Spec attr, Integer[] nodeIds) {
		Integer attrId = attr.getId();
		//更新前先删除之前已经绑定的栏目ID
		dao.deleteBySpecId(attrId);
		System.out.println("已经执行了删除操作");
		List<NodeSpec> nrs = daoPlus.findBySpecId(attrId);
		Node node = null;
		boolean contains;
		for (Integer nodeId : nodeIds) {
			contains = false;
			if (nrs != null && nrs.size() > 0) {
				for (NodeSpec nr : nrs) {
					if (!nr.getNode().getId().equals(nodeId)) {
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
				save(node, attr);
			}
		}
	}

	@Transactional
	public void update(Node node, Integer[] nodePermIds) {
		Integer nodeId = node.getId();
		List<Spec> attrs = attrService.findList(node.getSite().getId());
		List<NodeSpec> nrs = daoPlus.findByNodeId(nodeId);
		Integer attrId;
		boolean contains;
		for (Spec attr : attrs) {
			contains = false;
			attrId = attr.getId();
			for (NodeSpec nr : nrs) {
				if (!nr.getSpec().getId().equals(attrId)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				save(node, attr);
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

	public void preSpecDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.deleteBySpecId(id);
			}
		}
	}

	public List<NodeSpec> getByNodeId(Integer nodeId) {
		return daoPlus.findByNodeId(nodeId);
	}

	private SpecService attrService;

	@Autowired
	public void setSpecService(SpecService attrService) {
		this.attrService = attrService;
	}

	private NodeDao nodeDao;

	@Autowired
	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	private NodeSpecDao dao;
	
	@Autowired
	public void setDao(NodeSpecDao dao) {
		this.dao = dao;
	}
	
	private NodeSpecDaoPlus daoPlus;

	@Autowired
	public void setDao(NodeSpecDaoPlus daoPlus) {
		this.daoPlus = daoPlus;
	}
}
