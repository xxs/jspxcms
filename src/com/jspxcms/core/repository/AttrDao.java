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
import com.jspxcms.core.domain.Attr;

/**
 * AttrDao
 * 
 * @author liufang
 * 
 */
public interface AttrDao extends Repository<Attr, Integer>,
		AttrDaoPlus {

	public Attr findOne(Integer id);

	public List<Attr> findAll(Specification<Attr> spec, Limitable limit);
	
	public Attr save(Attr bean);

	public void delete(Attr bean);

	// --------------------

	@QueryHints(@QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true"))
	public List<Attr> findBySiteId(Integer siteId, Sort sort);

	@Query("select count(*) from Attr bean where bean.number=?1 and bean.site.id=?2")
	public long countByNumber(String number, Integer siteId);

	@Query("select count(*) from Attr bean where bean.site.id in ?1")
	public long countBySiteId(Collection<Integer> siteIds);
}
