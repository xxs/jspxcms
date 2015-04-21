package com.jspxcms.core.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.InfoMemberGroup;

public interface InfoMemberGroupDao extends
		Repository<InfoMemberGroup, Integer> {
	public InfoMemberGroup findOne(Integer id);

	public InfoMemberGroup save(InfoMemberGroup bean);

	public void delete(InfoMemberGroup bean);

	// --------------------

	public List<InfoMemberGroup> findByInfoId(Integer infoId);

	@Modifying
	@Query("delete InfoMemberGroup bean where bean.info.id in (?1)")
	public int deleteByInfoId(Collection<Integer> infoIds);

	@Modifying
	@Query("delete InfoMemberGroup bean where bean.group.id in (?1)")
	public int deleteByGroupId(Collection<Integer> groupIds);
}
