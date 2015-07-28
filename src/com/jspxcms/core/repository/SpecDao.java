package com.jspxcms.core.repository;

import java.util.Collection;
import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.Parameter;
import com.jspxcms.core.domain.ParameterGroup;
import com.jspxcms.core.domain.Spec;

public interface SpecDao extends Repository<Spec, Integer> {

	public List<Spec> findAll(Specification<Spec> spec, Sort sort);

	public List<Spec> findAll(Specification<Spec> spec,
			Limitable limit);

	public Spec findOne(Integer id);

	public Spec save(Spec bean);

	public void delete(Spec bean);
	
	@QueryHints(@QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true"))
	public List<Spec> findBySiteId(Integer siteId, Sort sort);
	
	@Query("select count(*) from Spec bean where bean.site.id in ?1")
	public long countBySiteId(Collection<Integer> siteIds);

}
