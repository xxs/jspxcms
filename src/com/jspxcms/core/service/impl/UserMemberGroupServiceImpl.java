package com.jspxcms.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserMemberGroup;
import com.jspxcms.core.listener.MemberGroupDeleteListener;
import com.jspxcms.core.repository.UserMemberGroupDao;
import com.jspxcms.core.service.MemberGroupService;
import com.jspxcms.core.service.UserMemberGroupService;

@Service
@Transactional(readOnly = true)
public class UserMemberGroupServiceImpl implements UserMemberGroupService,
		MemberGroupDeleteListener {

	@Transactional
	public void save(User user, Integer[] groupIds, Integer groupId) {
		MemberGroup group = memberGroupService.get(groupId);
		dao.save(new UserMemberGroup(user, group, 0));
		if (groupIds != null) {
			for (int i = 0, len = groupIds.length; i < len; i++) {
				if (groupId.equals(groupIds[i])) {
					continue;
				}
				group = memberGroupService.get(groupIds[i]);
				dao.save(new UserMemberGroup(user, group, i + 1));
			}
		}
	}

	@Transactional
	public void update(User user, Integer[] groupIds, Integer groupId) {
		// 主用户组不存在，不更新
		if (groupId == null) {
			return;
		}
		List<UserMemberGroup> ugs = user.getUserGroups();
		MemberGroup group;
		if (!ugs.isEmpty()) {
			dao.delete(ugs.get(0));
			ugs.remove(0);
		}
		group = memberGroupService.get(groupId);
		dao.save(new UserMemberGroup(user, group, 0));
		if (groupIds == null) {
			return;
		}
		for (UserMemberGroup ug : ugs) {
			dao.delete(ug);
		}
		for (int i = 0, len = groupIds.length; i < len; i++) {
			if (groupId.equals(groupIds[i])) {
				continue;
			}
			group = memberGroupService.get(groupIds[i]);
			dao.save(new UserMemberGroup(user, group, i + 1));
		}
	}

	public void preMemberGroupDelete(Integer[] ids) {
		if (ids == null) {
			return;
		}
		for (Integer id : ids) {
			dao.deleteByGroupId(id);
		}
	}

	private MemberGroupService memberGroupService;

	@Autowired
	public void setMemberGroupService(MemberGroupService memberGroupService) {
		this.memberGroupService = memberGroupService;
	}

	private UserMemberGroupDao dao;

	@Autowired
	public void setDao(UserMemberGroupDao dao) {
		this.dao = dao;
	}
}
