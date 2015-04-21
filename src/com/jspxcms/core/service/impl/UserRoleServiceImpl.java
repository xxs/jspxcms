package com.jspxcms.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Role;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserRole;
import com.jspxcms.core.listener.RoleDeleteListener;
import com.jspxcms.core.repository.UserRoleDao;
import com.jspxcms.core.service.RoleService;
import com.jspxcms.core.service.UserRoleService;

@Service
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService, RoleDeleteListener {
	@Transactional
	public void save(User user, Integer[] roleIds) {
		if (roleIds == null) {
			return;
		}
		Role role;
		for (int i = 0, len = roleIds.length; i < len; i++) {
			role = roleService.get(roleIds[i]);
			save(user, role, i);
		}
	}

	@Transactional
	public UserRole save(User user, Role role, Integer roleIndex) {
		UserRole bean = new UserRole(user, role, roleIndex);
		bean.applyDefaultValue();
		dao.save(bean);
		return bean;
	}

	@Transactional
	public void update(User user, Integer[] roleIds, Integer siteId) {
		List<UserRole> urs = user.getUserRoles();
		for (UserRole ur : urs) {
			if (siteId == null || ur.getRole().getSite().getId().equals(siteId)) {
				dao.delete(ur);
			}
		}
		save(user, roleIds);
	}

	@Transactional
	public int deleteByUserId(Integer userId) {
		return dao.deleteByUserId(userId);
	}

	@Transactional
	public int deleteByRoleId(Integer roleId) {
		return dao.deleteByRoleId(roleId);
	}

	public void preRoleDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.deleteByRoleId(id);
			}
		}
	}

	private RoleService roleService;

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private UserRoleDao dao;

	@Autowired
	public void setDao(UserRoleDao dao) {
		this.dao = dao;
	}
}
