package com.jspxcms.common.orm;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.LockModeRepositoryPostProcessor;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;

/**
 * JPA Repository工厂
 * 
 * @author liufang
 * 
 */
public class MyJpaRepositoryFactory extends JpaRepositoryFactory {
	private final LockModeRepositoryPostProcessor lockModePostProcessor;

	public MyJpaRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		this.lockModePostProcessor = LockModeRepositoryPostProcessor.INSTANCE;
	}

	protected <T, ID extends Serializable> JpaRepository<?, ?> getTargetRepository(
			RepositoryMetadata metadata, EntityManager entityManager) {
		Class<?> repositoryInterface = metadata.getRepositoryInterface();
		JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata
				.getDomainType());
		@SuppressWarnings({ "unchecked", "rawtypes" })
		SimpleJpaRepository<?, ?> repo = isQueryDslExecutor(repositoryInterface) ? new MyQueryDslJpaRepository(
				entityInformation, entityManager) : new MySimpleJpaRepository(
				entityInformation, entityManager);
		repo.setLockMetadataProvider(lockModePostProcessor
				.getLockMetadataProvider());

		return repo;
	}

	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		if (isQueryDslExecutor(metadata.getRepositoryInterface())) {
			return QueryDslJpaRepository.class;
		} else {
			return MySimpleJpaRepository.class;
		}
	}

	private boolean isQueryDslExecutor(Class<?> repositoryInterface) {
		return QUERY_DSL_PRESENT
				&& MyQueryDslPredicateExecutor.class
						.isAssignableFrom(repositoryInterface);
	}
}
