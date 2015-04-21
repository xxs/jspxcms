package com.jspxcms.ext.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.QueryHints;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.QuerydslUtils;
import com.jspxcms.ext.domain.FriendlinkType;
import com.jspxcms.ext.domaindsl.QFriendlinkType;
import com.jspxcms.ext.repository.FriendlinkTypeDaoPlus;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * FriendlinkTypeDaoImpl
 * 
 * @author yangxing
 * 
 */
public class FriendlinkTypeDaoImpl implements FriendlinkTypeDaoPlus {

	public List<FriendlinkType> getList(Integer[] siteId, Limitable limitable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QFriendlinkType friendlinkType = QFriendlinkType.friendlinkType;
		query.from(friendlinkType);
		BooleanBuilder exp = new BooleanBuilder();
		if (ArrayUtils.isNotEmpty(siteId)) {
			exp = exp.and(friendlinkType.site.id.in(siteId));
		}
		query.where(exp);
		return QuerydslUtils.list(query, friendlinkType, limitable);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
