package com.jspxcms.core.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.InfoOrg;

public interface InfoOrgDao extends Repository<InfoOrg, Integer> {
	public InfoOrg findOne(Integer id);

	public InfoOrg save(InfoOrg bean);

	public void delete(InfoOrg bean);

	// --------------------

	public List<InfoOrg> findByInfoId(Integer infoId);

	@Modifying
	@Query("delete InfoOrg bean where bean.info.id in (?1)")
	public int deleteByInfoId(Collection<Integer> infoIds);

	@Modifying
	@Query("delete InfoOrg bean where bean.org.id in (?1)")
	public int deleteByOrgId(Collection<Integer> orgIds);

}
