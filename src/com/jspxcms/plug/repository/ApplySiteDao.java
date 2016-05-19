package com.jspxcms.plug.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.ApplySite;

public interface ApplySiteDao extends Repository<ApplySite, Integer> {
	public Page<ApplySite> findAll(Specification<ApplySite> spec, Pageable pageable);

	public List<ApplySite> findAll(Specification<ApplySite> spec, Limitable limitable);

	public ApplySite findOne(Integer id);

	public ApplySite save(ApplySite bean);

	public void delete(ApplySite bean);
}
