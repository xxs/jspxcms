package com.jspxcms.common.orm;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.PathBuilder;

/**
 * QueryDsl JpaRepository
 * 
 * @author liufang
 * 
 * @param <T>
 * @param <ID>
 */
public class MyQueryDslJpaRepository<T, ID extends Serializable> extends
		QueryDslJpaRepository<T, ID> implements MyQueryDslPredicateExecutor<T> {

	private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;
	private final EntityPath<T> path;
	private final PathBuilder<T> builder;
	private final Querydsl querydsl;

	public MyQueryDslJpaRepository(
			JpaEntityInformation<T, ID> entityInformation,
			EntityManager entityManager) {
		this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
	}

	public MyQueryDslJpaRepository(
			JpaEntityInformation<T, ID> entityInformation,
			EntityManager entityManager, EntityPathResolver resolver) {
		super(entityInformation, entityManager, resolver);
		this.path = resolver.createPath(entityInformation.getJavaType());
		this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
		this.querydsl = new Querydsl(entityManager, builder);
	}

	public List<T> findAll(Predicate predicate, Sort sort) {
		JPQLQuery query = createQuery(predicate);
		querydsl.applySorting(sort, query);
		return query.list(path);
	}

	public List<T> findAll(Predicate predicate, Limitable limitable) {
		JPQLQuery query = createQuery(predicate);
		if (limitable != null) {
			querydsl.applySorting(limitable.getSort(), query);
			Integer offset = limitable.getFirstResult();
			if (offset != null && offset > 0) {
				query.offset(offset);
			}
			Integer limit = limitable.getMaxResults();
			if (limit != null && limit > 0) {
				query.limit(limit);
			}
		}
		return query.list(path);
	}

}
