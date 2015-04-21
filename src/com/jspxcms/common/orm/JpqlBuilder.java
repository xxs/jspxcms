package com.jspxcms.common.orm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;

/**
 * Java Persistence Query Language 查询
 * 
 * @author liufang
 * 
 */
public class JpqlBuilder {
	private StringBuilder jpql;
	private List<Parameter> parameters = new ArrayList<Parameter>();
	private static final String COUNT_STRING = "^\\s*?(select\\s+([\\s\\S]*?)\\s+)?from\\s+[\\s\\S]*?(\\sorder\\s[\\s\\S]*)?";
	private static final Pattern COUNT_PATTERN = Pattern.compile(COUNT_STRING,
			Pattern.CASE_INSENSITIVE);

	public JpqlBuilder() {
		jpql = new StringBuilder();
	}

	public JpqlBuilder(String queryString) {
		jpql = new StringBuilder(queryString);
	}

	public JpqlBuilder append(String queryString) {
		jpql.append(queryString);
		return this;
	}

	public JpqlBuilder setParameter(String name, Object value) {
		parameters.add(new Parameter(name, value, null));
		return this;
	}

	public JpqlBuilder setParameter(String name, Date value,
			TemporalType temporalType) {
		parameters.add(new Parameter(name, value, temporalType));
		return this;
	}

	public JpqlBuilder setParameter(String name, Calendar value,
			TemporalType temporalType) {
		parameters.add(new Parameter(name, value, temporalType));
		return this;
	}

	public Query createNativeQuery(EntityManager em) {
		String sql = jpql.toString();
		Query query = em.createNativeQuery(sql);
		for (Parameter parameter : parameters) {
			String name = parameter.getName();
			Object value = parameter.getValue();
			TemporalType temporalType = parameter.getTemporalType();
			if (temporalType == null) {
				query.setParameter(name, value);
			} else {
				if (value instanceof Date) {
					query.setParameter(name, (Date) value, temporalType);
				} else if (value instanceof Calendar) {
					query.setParameter(name, (Calendar) value, temporalType);
				} else {
					throw new IllegalStateException(
							"value must be java.utile.Date or java.util.Calendar");
				}
			}
		}
		return query;
	}

	public Query createQuery(EntityManager em, Sort sort) {
		String jpqlString = jpql.toString();
		String alias = QueryUtils.detectAlias(jpqlString);
		String sortedQueryString = QueryUtils.applySorting(jpqlString, sort,
				alias);
		Query query = em.createQuery(sortedQueryString);
		for (Parameter parameter : parameters) {
			String name = parameter.getName();
			Object value = parameter.getValue();
			TemporalType temporalType = parameter.getTemporalType();
			if (temporalType == null) {
				query.setParameter(name, value);
			} else {
				if (value instanceof Date) {
					query.setParameter(name, (Date) value, temporalType);
				} else if (value instanceof Calendar) {
					query.setParameter(name, (Calendar) value, temporalType);
				} else {
					throw new IllegalStateException(
							"value must be java.utile.Date or java.util.Calendar");
				}
			}
		}
		return query;
	}

	public <T> TypedQuery<T> createQuery(EntityManager em,
			Class<T> resultClass, Sort sort) {
		String jpqlString = jpql.toString();
		String alias = QueryUtils.detectAlias(jpqlString);
		String sortedQueryString = QueryUtils.applySorting(jpqlString, sort,
				alias);
		TypedQuery<T> query = em.createQuery(sortedQueryString, resultClass);
		for (Parameter parameter : parameters) {
			String name = parameter.getName();
			Object value = parameter.getValue();
			TemporalType temporalType = parameter.getTemporalType();
			if (temporalType == null) {
				query.setParameter(name, value);
			} else {
				if (value instanceof Date) {
					query.setParameter(name, (Date) value, temporalType);
				} else if (value instanceof Calendar) {
					query.setParameter(name, (Calendar) value, temporalType);
				} else {
					throw new IllegalStateException(
							"value must be java.utile.Date or java.util.Calendar");
				}
			}
		}
		return query;
	}

	public Query createQuery(EntityManager em) {
		Sort sort = null;
		return createQuery(em, sort);
	}

	public <T> TypedQuery<T> createQuery(EntityManager em, Class<T> resultClass) {
		return createQuery(em, resultClass, null);
	}

	public Query createCountQuery(EntityManager em) {
		Query query = em.createQuery(getCountQueryString());
		for (Parameter parameter : parameters) {
			String name = parameter.getName();
			Object value = parameter.getValue();
			TemporalType temporalType = parameter.getTemporalType();
			if (temporalType == null) {
				query.setParameter(name, value);
			} else {
				if (value instanceof Date) {
					query.setParameter(name, (Date) value, temporalType);
				} else if (value instanceof Calendar) {
					query.setParameter(name, (Calendar) value, temporalType);
				} else {
					throw new IllegalStateException(
							"value must be java.utile.Date or java.util.Calendar");
				}
			}
		}
		return query;
	}

	public String getQueryString() {
		return jpql.toString();
	}

	public String getCountQueryString() {
		Matcher m = COUNT_PATTERN.matcher(jpql);
		if (!m.matches()) {
			throw new IllegalStateException("query string invalidated: " + jpql);
		}
		StringBuilder countJpql = new StringBuilder();
		int countStart = m.start(2);
		int countEnd = m.end(2);
		int orderStart = m.start(3);
		if (countStart != -1) {
			countJpql.append(jpql.substring(0, countStart));
			countJpql.append(" count( ");
			countJpql.append(jpql.substring(countStart, countEnd));
			countJpql.append(" ) ");
			if (orderStart != -1) {
				countJpql.append(jpql.substring(countEnd, orderStart));
			} else {
				countJpql.append(jpql.substring(countEnd));
			}
		} else {
			countJpql.append("select count(*) ");
			if (orderStart != -1) {
				countJpql.append(jpql.substring(0, orderStart));
			} else {
				countJpql.append(jpql);
			}
		}
		// 清除fetch关键字
		String queryCount = StringUtils.replace(countJpql.toString(),
				" fetch ", " ");
		return queryCount;
	}

	@SuppressWarnings("rawtypes")
	public List list(EntityManager em) {
		return createQuery(em).getResultList();
	}

	public <T> List<T> list(EntityManager em, Class<T> resultClass) {
		return createQuery(em, resultClass).getResultList();
	}

	@SuppressWarnings("rawtypes")
	public List list(EntityManager em, Sort sort) {
		return createQuery(em, sort).getResultList();
	}

	public <T> List<T> list(EntityManager em, Class<T> resultClass, Sort sort) {
		return createQuery(em, resultClass, sort).getResultList();
	}

	@SuppressWarnings("rawtypes")
	public List list(EntityManager em, Limitable limitable) {
		Query query = createQuery(em, limitable.getSort());
		Integer firstResult = limitable.getFirstResult();
		if (firstResult != null && firstResult > 0) {
			query.setFirstResult(firstResult);
		}
		Integer maxResults = limitable.getMaxResults();
		if (maxResults != null && maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		return query.getResultList();
	}

	public <T> List<T> list(EntityManager em, Class<T> resultClass,
			Limitable limitable) {
		TypedQuery<T> query = createQuery(em, resultClass, limitable.getSort());
		Integer firstResult = limitable.getFirstResult();
		if (firstResult != null && firstResult > 0) {
			query.setFirstResult(firstResult);
		}
		Integer maxResults = limitable.getMaxResults();
		if (maxResults != null && maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		return query.getResultList();
	}

	@SuppressWarnings("rawtypes")
	public Page page(EntityManager em, Pageable pageable) {
		Query countQuery = this.createCountQuery(em);
		long total = ((Number) countQuery.getSingleResult()).longValue();

		List content;
		if (total > pageable.getOffset()) {
			Query query = this.createQuery(em, pageable.getSort());
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
			content = query.getResultList();
		} else {
			content = Collections.EMPTY_LIST;
		}
		@SuppressWarnings("unchecked")
		Page page = new PageImpl(content, pageable, total);
		return page;
	}

	private static class Parameter {
		private String name;
		private Object value;
		private TemporalType temporalType;

		public Parameter(String name, Object val, TemporalType temporalType) {
			this.name = name;
			this.value = val;
			this.temporalType = temporalType;
		}

		public String getName() {
			return name;
		}

		public Object getValue() {
			return value;
		}

		public TemporalType getTemporalType() {
			return temporalType;
		}
	}

	public static void main(String[] args) throws Exception {
		String s = "From User fetch t";
		JpqlBuilder builder = new JpqlBuilder(s);
		System.out.println(builder.getCountQueryString());
	}
}
