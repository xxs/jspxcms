package com.jspxcms.common.orm;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.LockMetadataProvider;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

/**
 * Simple JPA Repository
 * 
 * @author liufang
 * 
 * @param <T>
 * @param <ID>
 */
public class MySimpleJpaRepository<T, ID extends Serializable> extends
		SimpleJpaRepository<T, ID> implements MyJpaRepository<T, ID>,
		MyJpaSpecificationExecutor<T> {

	private final JpaEntityInformation<T, ?> entityInformation;
	private final EntityManager em;
	private LockMetadataProvider lockMetadataProvider;

	public MySimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation,
			EntityManager entityManager) {
		super(entityInformation, entityManager);

		this.entityInformation = entityInformation;
		this.em = entityManager;
	}

	public MySimpleJpaRepository(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getMetadata(domainClass, em), em);
	}

	public void setLockMetadataProvider(
			LockMetadataProvider lockMetadataProvider) {
		super.setLockMetadataProvider(lockMetadataProvider);
		this.lockMetadataProvider = lockMetadataProvider;
	}

	private Class<T> getDomainClass() {
		return entityInformation.getJavaType();
	}

	public List<T> findAll(Limitable limitable) {
		if (limitable != null) {
			TypedQuery<T> query = getQuery(null, limitable.getSort());
			Integer firstResult = limitable.getFirstResult();
			if (firstResult != null && firstResult > 0) {
				query.setFirstResult(firstResult);
			}
			Integer maxResults = limitable.getMaxResults();
			if (maxResults != null && maxResults > 0) {
				query.setMaxResults(maxResults);
			}
			return query.getResultList();
		} else {
			return findAll();
		}
	}

	public List<T> findAll(Specification<T> spec, Limitable limitable) {
		if (limitable != null) {
			TypedQuery<T> query = getQuery(spec, limitable.getSort());
			if (limitable.getFirstResult() != null) {
				query.setFirstResult(limitable.getFirstResult());
			}
			if (limitable.getMaxResults() != null) {
				query.setMaxResults(limitable.getMaxResults());
			}
			return query.getResultList();
		} else {
			return findAll(spec);
		}
	}

	private TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getDomainClass());

		Root<T> root = applySpecificationToCriteria(spec, query);
		query.select(root);

		if (sort != null) {
			query.orderBy(toOrders(sort, root, builder));
		}

		return applyLockMode(em.createQuery(query));
	}

	private <S> Root<T> applySpecificationToCriteria(Specification<T> spec,
			CriteriaQuery<S> query) {

		Assert.notNull(query);
		Root<T> root = query.from(getDomainClass());

		if (spec == null) {
			return root;
		}

		CriteriaBuilder builder = em.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

		return root;
	}

	private TypedQuery<T> applyLockMode(TypedQuery<T> query) {

		LockModeType type = lockMetadataProvider == null ? null
				: lockMetadataProvider.getLockModeType();
		return type == null ? query : query.setLockMode(type);
	}
}
