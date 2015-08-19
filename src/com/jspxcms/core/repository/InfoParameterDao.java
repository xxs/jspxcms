package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.InfoParameter;

public interface InfoParameterDao extends Repository<InfoParameter, Integer> {
	public List<InfoParameter> findAll(Specification<InfoParameter> spec, Sort sort);

	public List<InfoParameter> findAll(Specification<InfoParameter> spec, Limitable limit);

	public InfoParameter findOne(Integer id);

	public InfoParameter save(InfoParameter bean);

	public void delete(InfoParameter bean);

	// --------------------

	@Modifying
	@Query("delete from InfoParameterValue t where t.info.id=?1")
	public int deleteByInfoId(Integer infoId);

	@Modifying
	@Query("delete from InfoParameterValue t where t.parameter.id=?1")
	public int deleteByParameterId(Integer parameterId);
}
