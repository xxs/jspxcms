package com.jspxcms.common.orm;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.mysema.query.SimpleQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.PathBuilder;

/**
 * Querydsl工具类
 * 
 * @author liufang
 * 
 */
public abstract class QuerydslUtils {
	public static <T> List<T> list(JPAQuery query, EntityPathBase<T> entityPath) {
		return query.list(entityPath);
	}

	public static <T> List<T> list(JPAQuery query,
			EntityPathBase<T> entityPath, Sort sort) {
		applySorting(query, entityPath, sort);
		return query.list(entityPath);
	}

	public static <T> List<T> list(JPAQuery query,
			EntityPathBase<T> entityPath, Limitable limitable) {
		applySorting(query, entityPath, limitable.getSort());
		Integer firstResult = limitable.getFirstResult();
		if (firstResult != null && firstResult > 0) {
			query.offset(firstResult);
		}
		Integer maxResults = limitable.getMaxResults();
		if (maxResults != null && maxResults > 0) {
			query.limit(maxResults);
		}
		return query.list(entityPath);
	}

	public static <T> Page<T> page(JPAQuery query,
			EntityPathBase<T> entityPath, Pageable pageable) {
		long total = query.count();
		List<T> content;
		if (total > pageable.getOffset()) {
			query.offset(pageable.getOffset());
			query.limit(pageable.getPageSize());
			applySorting(query, entityPath, pageable.getSort());
			content = query.list(entityPath);
		} else {
			content = Collections.emptyList();
		}
		Page<T> page = new PageImpl<T>(content, pageable, total);
		return page;
	}

	public static <T, Q extends SimpleQuery<Q>> void applySorting(
			SimpleQuery<Q> query, EntityPath<T> entityPath, Sort sort) {
		if (sort == null) {
			return;
		}
		PathBuilder<T> builder = new PathBuilder<T>(entityPath.getType(),
				entityPath.getMetadata());
		for (Order order : sort) {
			query.orderBy(toOrder(builder, order));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OrderSpecifier<?> toOrder(PathBuilder builder, Order order) {
		Expression<Object> property = builder.get(order.getProperty());
		return new OrderSpecifier(
				order.isAscending() ? com.mysema.query.types.Order.ASC
						: com.mysema.query.types.Order.DESC, property);
	}
}
