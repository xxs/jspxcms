package com.jspxcms.core.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.QuerydslUtils;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domaindsl.QAttribute;
import com.jspxcms.core.domaindsl.QInfo;
import com.jspxcms.core.domaindsl.QInfoAttribute;
import com.jspxcms.core.domaindsl.QInfoDetail;
import com.jspxcms.core.domaindsl.QInfoMemberGroup;
import com.jspxcms.core.domaindsl.QInfoNode;
import com.jspxcms.core.domaindsl.QInfoOrg;
import com.jspxcms.core.domaindsl.QInfoSpecial;
import com.jspxcms.core.domaindsl.QInfoTag;
import com.jspxcms.core.domaindsl.QNodeMemberGroup;
import com.jspxcms.core.domaindsl.QNodeOrg;
import com.jspxcms.core.domaindsl.QSpecial;
import com.jspxcms.core.domaindsl.QTag;
import com.jspxcms.core.repository.InfoDaoPlus;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.StringPath;

/**
 * InfoDaoImpl
 * 
 * @author liufang
 * 
 */
public class InfoDaoImpl implements InfoDaoPlus {
	public List<Info> findList(Integer[] nodeId, Integer[] attrId,
			Integer[] specialId, Integer[] tagId, Integer[] siteId,
			Integer[] mainNodeId, Integer[] userId, Integer[] viewGroupId,
			Integer[] viewOrgId, String[] treeNumber, String[] specialTitle,
			String[] tagName, Integer[] priority, Date beginDate, Date endDate,
			String[] title, Integer[] includeId, Integer[] excludeId,
			Integer[] excludeMainNodeId, String[] excludeTreeNumber,
			Boolean isWithImage, String[] status, Limitable limitable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QInfo info = QInfo.info;
		predicate(query, info, nodeId, attrId, specialId, tagId, siteId,
				mainNodeId, userId, viewGroupId, viewOrgId, treeNumber,
				specialTitle, tagName, priority, beginDate, endDate, title,
				includeId, excludeId, excludeMainNodeId, excludeTreeNumber,
				isWithImage, status);
		return QuerydslUtils.list(query, info, limitable);
	}

	public Page<Info> findPage(Integer[] nodeId, Integer[] attrId,
			Integer[] specialId, Integer[] tagId, Integer[] siteId,
			Integer[] mainNodeId, Integer[] userId, Integer[] viewGroupId,
			Integer[] viewOrgId, String[] treeNumber, String[] specialTitle,
			String[] tagName, Integer[] priority, Date beginDate, Date endDate,
			String[] title, Integer[] includeId, Integer[] excludeId,
			Integer[] excludeMainNodeId, String[] excludeTreeNumber,
			Boolean isWithImage, String[] status, Pageable pageable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QInfo info = QInfo.info;
		predicate(query, info, nodeId, attrId, specialId, tagId, siteId,
				mainNodeId, userId, viewGroupId, viewOrgId, treeNumber,
				specialTitle, tagName, priority, beginDate, endDate, title,
				includeId, excludeId, excludeMainNodeId, excludeTreeNumber,
				isWithImage, status);
		return QuerydslUtils.page(query, info, pageable);
	}

	private void predicate(JPAQuery query, QInfo info, Integer[] nodeId,
			Integer[] attrId, Integer[] specialId, Integer[] tagId,
			Integer[] siteId, Integer[] mainNodeId, Integer[] userId,
			Integer[] viewGroupId, Integer[] viewOrgId, String[] treeNumber,
			String[] specialTitle, String[] tagName, Integer[] priority,
			Date beginDate, Date endDate, String[] title, Integer[] includeId,
			Integer[] excludeId, Integer[] excludeMainNodeId,
			String[] excludeTreeNumber, Boolean isWithImage, String[] status) {
		boolean isDistinct = false;
		query.from(info);
		BooleanBuilder exp = new BooleanBuilder();
		if (ArrayUtils.isNotEmpty(nodeId)) {
			QInfoNode infoNode = QInfoNode.infoNode;
			query.innerJoin(info.infoNodes, infoNode);
			exp = exp.and(infoNode.node.id.in(nodeId));
			if (nodeId.length > 1) {
				isDistinct = true;
			}
		}
		if (ArrayUtils.isNotEmpty(attrId)) {
			QInfoAttribute infoAttr = QInfoAttribute.infoAttribute;
			QAttribute attr = QAttribute.attribute;
			query.innerJoin(info.infoAttrs, infoAttr).innerJoin(
					infoAttr.attribute, attr);
			exp = exp.and(attr.id.in(attrId));
			if (attrId.length > 1) {
				isDistinct = true;
			}
		}
		boolean isSpecialId = ArrayUtils.isNotEmpty(specialId);
		boolean isSpecialTitle = ArrayUtils.isNotEmpty(specialTitle);
		if (isSpecialId || isSpecialTitle) {
			QInfoSpecial infoSpeical = QInfoSpecial.infoSpecial;
			query.innerJoin(info.infoSpecials, infoSpeical);
			QSpecial special = QSpecial.special;
			query.innerJoin(infoSpeical.special, special);
			BooleanBuilder e = new BooleanBuilder();
			if (isSpecialId) {
				for (int i = 0, len = specialId.length; i < len; i++) {
					e = e.or(special.id.eq(specialId[i]));
				}
				if (specialId.length > 1) {
					isDistinct = true;
				}
			}
			if (isSpecialTitle) {
				for (int i = 0, len = specialTitle.length; i < len; i++) {
					e = e.or(special.title.like(specialTitle[i]));
				}
				isDistinct = true;
			}
			exp = exp.and(e);
		}
		boolean isTagId = ArrayUtils.isNotEmpty(tagId);
		boolean isTagName = ArrayUtils.isNotEmpty(tagName);
		if (isTagId || isTagName) {
			QInfoTag infoTag = QInfoTag.infoTag;
			query.innerJoin(info.infoTags, infoTag);
			QTag tag = QTag.tag;
			query.innerJoin(infoTag.tag, tag);
			BooleanBuilder e = new BooleanBuilder();
			if (isTagId) {
				for (int i = 0, len = tagId.length; i < len; i++) {
					e = e.or(tag.id.eq(tagId[i]));
				}
				if (tagId.length > 1) {
					isDistinct = true;
				}
			}
			if (isTagName) {
				for (int i = 0, len = tagName.length; i < len; i++) {
					e = e.or(tag.name.like(tagName[i]));
				}
				isDistinct = true;
			}
			exp = exp.and(e);
		}
		if (ArrayUtils.isNotEmpty(siteId)) {
			exp = exp.and(info.site.id.in(siteId));
		}
		if (ArrayUtils.isNotEmpty(mainNodeId)) {
			BooleanExpression e = info.node.id.eq(mainNodeId[0]);
			for (int i = 1, len = mainNodeId.length; i < len; i++) {
				e = e.or(info.node.id.eq(mainNodeId[i]));
			}
			exp = exp.and(e);
		}
		if (ArrayUtils.isNotEmpty(excludeMainNodeId)) {
			exp = exp.and(info.node.id.notIn(excludeMainNodeId));
		}
		if (ArrayUtils.isNotEmpty(userId)) {
			BooleanExpression e = info.creator.id.eq(userId[0]);
			for (int i = 1, len = userId.length; i < len; i++) {
				e = e.or(info.creator.id.eq(userId[i]));
			}
			exp = exp.and(e);
		}
		if (viewGroupId != null || viewOrgId != null) {
			BooleanBuilder e = new BooleanBuilder();
			if (viewGroupId != null && viewGroupId.length > 0) {
				QNodeMemberGroup nodeGroup = QNodeMemberGroup.nodeMemberGroup;
				query.leftJoin(info.node.nodeGroups, nodeGroup);
				e = e.or(nodeGroup.group.id.in(viewGroupId).and(
						nodeGroup.viewPerm.eq(true)));
				QInfoMemberGroup infoGroup = QInfoMemberGroup.infoMemberGroup;
				query.leftJoin(info.infoGroups, infoGroup);
				e = e.or(infoGroup.group.id.in(viewGroupId).and(
						infoGroup.viewPerm.eq(true)));
			}
			if (viewOrgId != null && viewOrgId.length > 0) {
				QNodeOrg nodeOrg = QNodeOrg.nodeOrg;
				query.leftJoin(info.node.nodeOrgs, nodeOrg);
				e = e.or(nodeOrg.org.id.in(viewOrgId).and(
						nodeOrg.viewPerm.eq(true)));
				QInfoOrg infoOrg = QInfoOrg.infoOrg;
				query.leftJoin(info.infoOrgs, infoOrg);
				e = e.or(infoOrg.org.id.in(viewOrgId).and(
						infoOrg.viewPerm.eq(true)));
			}
		}
		boolean isTreeNumber = ArrayUtils.isNotEmpty(treeNumber);
		boolean isExcludeTreeNumber = ArrayUtils.isNotEmpty(excludeTreeNumber);
		if (isTreeNumber || isExcludeTreeNumber) {
			StringPath tnPath = info.node.treeNumber;
			if (isTreeNumber) {
				BooleanExpression e = tnPath.startsWith(treeNumber[0]);
				for (int i = 1, len = treeNumber.length; i < len; i++) {
					e = e.or(tnPath.startsWith(treeNumber[i]));
				}
				exp = exp.and(e);
			}
			if (isExcludeTreeNumber) {
				for (int i = 0, len = excludeTreeNumber.length; i < len; i++) {
					exp = exp
							.and(tnPath.startsWith(excludeTreeNumber[i]).not());
				}
			}
		}
		if (ArrayUtils.isNotEmpty(priority)) {
			BooleanExpression e = info.priority.eq(priority[0]);
			for (int i = 1, len = priority.length; i < len; i++) {
				e = e.or(info.priority.eq(priority[i]));
			}
			exp = exp.and(e);
		}
		if (beginDate != null) {
			exp = exp.and(info.publishDate.goe(beginDate));
		}
		if (endDate != null) {
			exp = exp.and(info.publishDate.loe(endDate));
		}
		if (ArrayUtils.isNotEmpty(title)) {
			QInfoDetail infoDetail = QInfoDetail.infoDetail;
			query.innerJoin(info.detail, infoDetail);
			BooleanExpression e = infoDetail.title.like(title[0]);
			for (int i = 1, len = title.length; i < len; i++) {
				e = e.or(infoDetail.title.like(title[i]));
			}
			exp = exp.and(e);
		}
		if (ArrayUtils.isNotEmpty(includeId)) {
			exp = exp.and(info.id.in(includeId));
		}
		if (ArrayUtils.isNotEmpty(excludeId)) {
			exp = exp.and(info.id.notIn(excludeId));
		}
		if (ArrayUtils.isNotEmpty(excludeTreeNumber)) {

		}
		if (isWithImage != null) {
			exp = exp.and(info.withImage.eq(isWithImage));
		}
		if (ArrayUtils.isNotEmpty(status)) {
			exp = exp.and(info.status.in(status));
		}
		query.where(exp);
		if (isDistinct) {
			query.distinct();
		}
	}

	public Info findNext(Integer siteId, Integer nodeId, Integer id,
			Date publishDate) {
		JPAQuery query = new JPAQuery(this.em);
		QInfo info = QInfo.info;
		query.from(info);
		BooleanBuilder exp = new BooleanBuilder();
		if (nodeId != null) {
			exp = exp.and(info.node.id.eq(nodeId));
		} else if (siteId != null) {
			exp = exp.and(info.site.id.eq(siteId));
		}
		exp = exp.and(info.publishDate.goe(publishDate));
		exp = exp.and(info.id.lt(id).or(info.id.gt(id)));
		query.where(exp);
		query.orderBy(info.publishDate.asc(), info.id.asc());
		query.limit(1);
		List<Info> list = query.list(info);
		return list.isEmpty() ? null : list.get(0);
	}

	public Info findPrev(Integer siteId, Integer nodeId, Integer id,
			Date publishDate) {
		JPAQuery query = new JPAQuery(this.em);
		QInfo info = QInfo.info;
		query.from(info);
		BooleanBuilder expr = new BooleanBuilder();
		if (nodeId != null) {
			expr = expr.and(info.node.id.eq(nodeId));
		} else if (siteId != null) {
			expr = expr.and(info.site.id.eq(siteId));
		}
		expr = expr.and(info.publishDate.loe(publishDate));
		expr = expr.and(info.id.lt(id).or(info.id.gt(id)));
		query.where(expr);
		query.orderBy(info.publishDate.desc(), info.id.desc());
		query.limit(1);
		List<Info> list = query.list(info);
		return list.isEmpty() ? null : list.get(0);
	}

	private EntityManager em;

	@javax.persistence.PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
