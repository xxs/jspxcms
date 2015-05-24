package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.InfoAttr;

/**
 * InfoAttrDao
 * 
 * @author liufang
 * 
 */
public interface InfoAttrDao extends Repository<InfoAttr, Integer> {
	public List<InfoAttr> findAll(Specification<InfoAttr> spec,
			Sort sort);

	public List<InfoAttr> findAll(Specification<InfoAttr> spec,
			Limitable limit);

	public InfoAttr findOne(Integer id);

	public InfoAttr save(InfoAttr bean);

	public void delete(InfoAttr bean);

	// --------------------

	@Modifying
	@Query("delete from InfoAttr t where t.info.id=?1")
	public int deleteByInfoId(Integer infoId);

	@Modifying
	@Query("delete from InfoAttr t where t.attr.id=?1")
	public int deleteByAttrId(Integer attrId);
}
