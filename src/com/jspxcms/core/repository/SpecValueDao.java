package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.SpecValue;

public interface SpecValueDao extends Repository<SpecValue, Integer> {
	public List<SpecValue> findAll(Specification<SpecValue> spec, Sort sort);

	public List<SpecValue> findAll(Specification<SpecValue> spec,
			Limitable limit);

	public SpecValue findOne(Integer id);

	public SpecValue save(SpecValue bean);

	public void delete(SpecValue bean);

}
