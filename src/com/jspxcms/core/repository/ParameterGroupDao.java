package com.jspxcms.core.repository;

import java.util.Collection;
import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.ParameterGroup;

/**
 * ParameterGroupDao
 * 
 * @author xxs
 * 
 */
public interface ParameterGroupDao extends Repository<ParameterGroup, Integer> {

	public ParameterGroup findOne(Integer id);

	public ParameterGroup save(ParameterGroup bean);

	public void delete(ParameterGroup bean);

	@QueryHints(@QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true"))
	public List<ParameterGroup> findBySiteId(Integer siteId, Sort sort);

	@Query("select count(*) from ParameterGroup bean where bean.site.id in ?1")
	public long countBySiteId(Collection<Integer> siteIds);
}
