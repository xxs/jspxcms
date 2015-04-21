package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.InfoTag;

public interface InfoTagDao extends Repository<InfoTag, Integer> {
	public List<InfoTag> findAll(Specification<InfoTag> spec, Sort sort);

	public List<InfoTag> findAll(Specification<InfoTag> spec, Limitable limit);

	public InfoTag findOne(Integer id);

	public InfoTag save(InfoTag bean);

	public void delete(InfoTag bean);

	// --------------------

	@Modifying
	@Query("delete from InfoTag t where t.info.id=?1")
	public int deleteByInfoId(Integer infoId);

	@Modifying
	@Query("delete from InfoTag t where t.tag.id=?1")
	public int deleteByTagId(Integer tagId);
}
