package com.jspxcms.core.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeRole;
import com.jspxcms.core.domain.Role;
import com.jspxcms.core.domain.WorkflowProcess;
import com.jspxcms.core.repository.InfoDao;
import com.jspxcms.core.service.InfoQueryService;

/**
 * 信息Service实现
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class InfoQueryServiceImpl implements InfoQueryService {
	public Page<Info> findAll(Integer siteId, Integer mainNodeId,
			Integer nodeId, String treeNumber, Integer userId,
			boolean allInfoPerm, int infoRightType, String status,
			Map<String, String[]> params, Pageable pageable) {
		return dao.findAll(
				spec(siteId, mainNodeId, nodeId, treeNumber, userId,
						allInfoPerm, infoRightType, status, params), pageable);
	}

	public RowSide<Info> findSide(Integer siteId, Integer mainNodeId,
			Integer nodeId, String treeNumber, Integer userId,
			boolean allInfoPerm, int infoRightType, String status,
			Map<String, String[]> params, Info bean, Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<Info>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<Info> list = dao.findAll(
				spec(siteId, mainNodeId, nodeId, treeNumber, userId,
						allInfoPerm, infoRightType, status, params), limit);
		return RowSide.create(list, bean);
	}

	private Specification<Info> spec(final Integer siteId,
			final Integer mainNodeId, final Integer nodeId,
			final String treeNumber, final Integer userId,
			final boolean allInfoPerm, final int infoPermType,
			final String status, Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Info> fsp = SearchFilter.spec(filters, Info.class);
		Specification<Info> sp = new Specification<Info>() {
			public Predicate toPredicate(Root<Info> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				boolean distinct = false;
				Predicate pred = fsp.toPredicate(root, query, cb);
				if (siteId != null) {
					pred = cb.and(pred,
							cb.equal(root.get("site").get("id"), siteId));
				}
				if (mainNodeId != null) {
					pred = cb.and(pred,
							cb.equal(root.get("node").get("id"), mainNodeId));
				} else if (nodeId != null) {
					Path<Integer> nodesPath = root.join("infoNodes")
							.get("node").get("id");
					pred = cb.and(pred, cb.equal(nodesPath, nodeId));
					distinct = true;
				} else if (StringUtils.isNotBlank(treeNumber)) {
					pred = cb.and(pred, cb.like(
							root.get("node").<String> get("treeNumber"),
							treeNumber + "%"));
				}
				if (!allInfoPerm) {
					Join<Node, NodeRole> nodeRoleJoin = root.join("node").join(
							"nodeRoles");
					Path<Integer> userPath = nodeRoleJoin.join("role")
							.join("userRoles").get("user").<Integer> get("id");
					pred = cb.and(pred, cb.equal(userPath, userId));
					pred = cb.and(pred,
							cb.equal(nodeRoleJoin.get("infoPerm"), true));
					query.distinct(true);
				}

				if (infoPermType == Role.INFO_PERM_SELF) {
					pred = cb.and(pred, cb.equal(root.get("creator")
							.<Integer> get("id"), userId));
				} else if (infoPermType == Role.INFO_PERM_ORG) {
					pred = cb.and(pred, cb.equal(
							root.join("org").join("userOrgs").get("user")
									.<Integer> get("id"), userId));
					distinct = true;
				}
				if (StringUtils.isNotBlank(status)) {
					if (status.length() == 1) {
						pred = cb.and(pred,
								cb.equal(root.get("status"), status));
					} else if (status.equals("pending")
							|| status.equals("notpassed")) {
						boolean rejection = "notpassed".equals(status);
						Subquery<Integer> sq = query.subquery(Integer.class);
						Root<WorkflowProcess> root2 = sq
								.from(WorkflowProcess.class);
						sq.select(root2.<Integer> get("dataId"));
						sq.where(
								cb.equal(root2.get("rejection"), rejection),
								cb.equal(root2.get("dataType"), 1),
								cb.equal(root2.get("end"), false),
								cb.equal(
										root2.join("step").join("stepRoles")
												.join("role").join("userRoles")
												.get("user").get("id"), userId));
						pred = cb.and(pred, cb.in(root.<Integer> get("id"))
								.value(sq));
					}
				}
				if (distinct) {
					query.distinct(true);
				}
				return pred;
			}
		};
		return sp;
	}

	public List<Info> findAll(Iterable<Integer> ids) {
		return dao.findAll(ids);
	}

	public List<Info> findList(Integer[] nodeId, Integer[] attrId,
			Integer[] specialId, Integer[] tagId, Integer[] siteId,
			Integer[] mainNodeId, Integer[] userId, Integer[] viewGroupId,
			Integer[] viewOrgId, String[] treeNumber, String[] specialTitle,
			String[] tagName, Integer[] priority, Date beginDate, Date endDate,
			String[] title, Integer[] includeId, Integer[] excludeId,
			Integer[] excludeMainNodeId, String[] excludeTreeNumber,
			Boolean isWithImage, String[] status, Limitable limitable) {
		return dao.findList(nodeId, attrId, specialId, tagId, siteId,
				mainNodeId, userId, viewGroupId, viewOrgId, treeNumber,
				specialTitle, tagName, priority, beginDate, endDate, title,
				includeId, excludeId, excludeMainNodeId, excludeTreeNumber,
				isWithImage, status, limitable);
	}

	public Page<Info> findPage(Integer[] nodeId, Integer[] attrId,
			Integer[] specialId, Integer[] tagId, Integer[] siteId,
			Integer[] mainNodeId, Integer[] userId, Integer[] viewGroupId,
			Integer[] viewOrgId, String[] treeNumber, String[] specialTitle,
			String[] tagName, Integer[] priority, Date beginDate, Date endDate,
			String[] title, Integer[] includeId, Integer[] excludeId,
			Integer[] excludeMainNodeId, String[] excludeTreeNumber,
			Boolean isWithImage, String[] status, Pageable pageable) {
		return dao.findPage(nodeId, attrId, specialId, tagId, siteId,
				mainNodeId, userId, viewGroupId, viewOrgId, treeNumber,
				specialTitle, tagName, priority, beginDate, endDate, title,
				includeId, excludeId, excludeMainNodeId, excludeTreeNumber,
				isWithImage, status, pageable);
	}

	public Info findNext(Integer id, boolean bySite) {
		Info info = get(id);
		if (info != null) {
			Integer siteId = bySite ? info.getSite().getId() : null;
			Integer nodeId = bySite ? null : info.getNode().getId();
			return dao.findNext(siteId, nodeId, id, info.getPublishDate());
		} else {
			return null;
		}
	}

	public Info findPrev(Integer id, boolean bySite) {
		Info info = get(id);
		if (info != null) {
			Integer siteId = bySite ? info.getSite().getId() : null;
			Integer nodeId = bySite ? null : info.getNode().getId();
			return dao.findPrev(siteId, nodeId, id, info.getPublishDate());
		} else {
			return null;
		}
	}

	public Info get(Integer id) {
		return dao.findOne(id);
	}

	private InfoDao dao;

	@Autowired
	public void setDao(InfoDao dao) {
		this.dao = dao;
	}
}
