package com.jspxcms.core.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.Model;

/**
 * ModelDao
 * 
 * @author liufang
 * 
 */
public interface ModelDao extends Repository<Model, Integer>, ModelDaoPlus {
	public List<Model> findAll(Specification<Model> spec, Sort sort);

	public List<Model> findAll(Specification<Model> spec, Limitable limit);

	public Model findOne(Integer id);

	public Model save(Model bean);

	public void delete(Model bean);

	// --------------------

	@Query("select count(*) from Model bean where bean.site.id in ?1")
	public long countBySiteId(Collection<Integer> siteIds);
}
