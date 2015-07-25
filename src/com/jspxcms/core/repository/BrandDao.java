package com.jspxcms.core.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.Brand;

/**
 * BrandDao
 * 
 * @author xxs
 * 
 */
public interface BrandDao extends Repository<Brand, Integer>,
BrandDaoPlus {

	public List<Brand> findAll(Specification<Brand> spec, Sort sort);

	public List<Brand> findAll(Specification<Brand> spec,
			Limitable limit);

	public Brand findOne(Integer id);

	public Brand save(Brand bean);

	public void delete(Brand bean);

	// --------------------

	public List<Brand> findBySiteId(Integer siteId, Sort sort);

	@Query("select count(*) from Brand bean where bean.site.id in ?1")
	public long countBySiteId(Collection<Integer> siteIds);

}
