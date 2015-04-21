package com.jspxcms.core.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.QueryHints;

import com.jspxcms.core.domain.User;
import com.jspxcms.core.domaindsl.QUser;
import com.jspxcms.core.repository.UserDaoPlus;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * UserDaoImpl
 * 
 * @author liufang
 * 
 */
public class UserDaoImpl implements UserDaoPlus {
	public List<User> findByUsername(String[] usernames) {
		if (ArrayUtils.isEmpty(usernames)) {
			return Collections.emptyList();
		}
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QUser user = QUser.user;
		query.from(user);
		BooleanExpression exp = user.username.eq(usernames[0]);
		for (int i = 1, len = usernames.length; i < len; i++) {
			exp = exp.or(user.username.eq(usernames[i]));
		}
		query.where(exp);
		return query.list(user);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
