package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.Parameter;

public interface ParameterDao extends Repository<Parameter, Integer> {
	public List<Parameter> findAll(Specification<Parameter> spec, Sort sort);

	public List<Parameter> findAll(Specification<Parameter> spec,
			Limitable limit);

	public Parameter findOne(Integer id);

	public Parameter save(Parameter bean);

	public void delete(Parameter bean);

}
