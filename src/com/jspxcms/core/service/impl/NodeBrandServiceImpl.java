package com.jspxcms.core.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Brand;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeBrand;
import com.jspxcms.core.listener.BrandDeleteListener;
import com.jspxcms.core.listener.NodeDeleteListener;
import com.jspxcms.core.repository.NodeBrandDao;
import com.jspxcms.core.repository.NodeDao;
import com.jspxcms.core.service.BrandService;
import com.jspxcms.core.service.NodeBrandService;

@Service
@Transactional(readOnly = true)
public class NodeBrandServiceImpl implements NodeBrandService,
		NodeDeleteListener, BrandDeleteListener {
	@Transactional
	public NodeBrand save(Node node, Brand brand) {
		NodeBrand bean = new NodeBrand();
		bean.setNode(node);
		bean.setBrand(brand);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public void save(Node node, Integer[] brandIds) {
		NodeBrand bean;
		if (brandIds != null && brandIds.length > 0) {
			for (int i = 0; i < brandIds.length; i++) {
				bean = new NodeBrand();
				bean.setNode(node);
				bean.setBrand(brandService.get(brandIds[i]));
				bean.applyDefaultValue();
				dao.save(bean);
			}
		}
	}

	@Transactional
	public void update(Brand brand, Integer[] nodeIds) {
		Integer brandId = brand.getId();
		// 更新前先删除之前已经绑定的栏目ID
		dao.deleteByBrandId(brandId);
		System.out.println("已经执行了删除操作");
		Set<NodeBrand> nrs = brand.getNodeBrands();
		Node node = null;
		boolean contains;
		for (Integer nodeId : nodeIds) {
			contains = false;
			if (nrs != null && nrs.size() > 0) {
				for (NodeBrand nr : nrs) {
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
				save(node, brand);
			}
		}
	}

	@Transactional
	public void update(Node node, Integer[] brandIds) {
		Integer nodeId = node.getId();
		// 更新前先删除之前已经绑定的栏目ID
		dao.deleteByNodeId(nodeId);
		save(node, brandIds);
	}

	public void preNodeDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.deleteByNodeId(id);
			}
		}
	}

	public void preBrandDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.deleteByBrandId(id);
			}
		}
	}

	private BrandService brandService;

	@Autowired
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	private NodeDao nodeDao;

	@Autowired
	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	private NodeBrandDao dao;

	@Autowired
	public void setDao(NodeBrandDao dao) {
		this.dao = dao;
	}

}
