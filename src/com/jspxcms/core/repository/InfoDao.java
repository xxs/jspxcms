package com.jspxcms.core.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.Info;

/**
 * InfoDao
 * 
 * @author liufang
 * 
 */
public interface InfoDao extends Repository<Info, Integer>, InfoDaoPlus {
	public Page<Info> findAll(Specification<Info> spec, Pageable pageable);

	public List<Info> findAll(Specification<Info> spec, Limitable limitable);
	
	public List<Info> findAll(Iterable<Integer> ids);

	public Info findOne(Integer id);

	public Info save(Info bean);

	public void delete(Info bean);

	// --------------------

	@Query("select count(*) from Info bean where bean.creator.id in ?1")
	public long countByUserId(Collection<Integer> userIds);

	@Query("select count(*) from Info bean where bean.node.id in ?1")
	public long countByNodeId(Collection<Integer> nodeIds);

	@Query("select count(*) from Info bean where bean.org.id in ?1")
	public long countByOrgId(Collection<Integer> orgIds);

	@Query("select count(*) from Info bean where bean.site.id in ?1")
	public long countBySiteId(Collection<Integer> siteIds);

}
