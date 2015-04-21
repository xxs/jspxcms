package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.InfoAttribute;

/**
 * InfoAttributeDao
 * 
 * @author liufang
 * 
 */
public interface InfoAttributeDao extends Repository<InfoAttribute, Integer> {
	public List<InfoAttribute> findAll(Specification<InfoAttribute> spec,
			Sort sort);

	public List<InfoAttribute> findAll(Specification<InfoAttribute> spec,
			Limitable limit);

	public InfoAttribute findOne(Integer id);

	public InfoAttribute save(InfoAttribute bean);

	public void delete(InfoAttribute bean);

	// --------------------

	@Modifying
	@Query("delete from InfoAttribute t where t.info.id=?1")
	public int deleteByInfoId(Integer infoId);

	@Modifying
	@Query("delete from InfoAttribute t where t.attribute.id=?1")
	public int deleteByAttributeId(Integer attributeId);
}
