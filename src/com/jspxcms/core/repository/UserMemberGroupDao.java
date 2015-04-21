package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.UserMemberGroup;

public interface UserMemberGroupDao extends
		Repository<UserMemberGroup, Integer> {
	public UserMemberGroup findOne(Integer id);

	public UserMemberGroup save(UserMemberGroup bean);

	public void delete(UserMemberGroup bean);

	// --------------------

	@Modifying
	@Query("delete from UserMemberGroup bean where bean.user.id=?1")
	public int deleteByUserId(Integer userId);

	@Modifying
	@Query("delete from UserMemberGroup bean where bean.group.id=?1")
	public int deleteByGroupId(Integer groupId);
}
