package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.AttrItem;

public interface AttrItemDao extends Repository<AttrItem, Integer> {
	public List<AttrItem> findAll(Specification<AttrItem> spec, Sort sort);

	public List<AttrItem> findAll(Specification<AttrItem> spec,
			Limitable limit);

	public AttrItem findOne(Integer id);

	public AttrItem save(AttrItem bean);

	public void delete(AttrItem bean);

}
