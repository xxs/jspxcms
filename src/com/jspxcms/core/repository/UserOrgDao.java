package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.UserOrg;

public interface UserOrgDao extends Repository<UserOrg, Integer> {

	public UserOrg findOne(Integer id);

	public UserOrg save(UserOrg bean);

	public void delete(UserOrg bean);

	// --------------------

	@Modifying
	@Query("delete from UserOrg bean where bean.user.id=?1")
	public int deleteByUserId(Integer userId);

	@Modifying
	@Query("delete from UserOrg bean where bean.org.id=?1")
	public int deleteByOrgId(Integer orgId);
}
