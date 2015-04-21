package com.jspxcms.core.service.impl;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Role;
import com.jspxcms.core.domain.WorkflowStep;
import com.jspxcms.core.domain.WorkflowStepRole;
import com.jspxcms.core.listener.RoleDeleteListener;
import com.jspxcms.core.repository.WorkflowStepRoleDao;
import com.jspxcms.core.service.RoleService;
import com.jspxcms.core.service.WorkflowStepRoleService;
import com.jspxcms.core.support.DeleteException;

@Service
@Transactional(readOnly = true)
public class WorkflowStepRoleServiceImpl implements WorkflowStepRoleService,
		RoleDeleteListener {
	@Transactional
	public void save(WorkflowStep step, Integer[] roleIds) {
		if (roleIds != null) {
			WorkflowStepRole stepRole;
			Role role;
			for (int i = 0, len = roleIds.length; i < len; i++) {
				role = roleService.get(roleIds[i]);
				stepRole = new WorkflowStepRole(step, role, i);
				dao.save(stepRole);
			}
		}
	}

	@Transactional
	public void update(WorkflowStep step, Integer[] roleIds) {
		dao.delete(step.getStepRoles());
		save(step, roleIds);
	}

	public void preRoleDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByRoleId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("workflowStep.management");
			}
		}
	}

	private RoleService roleService;

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private WorkflowStepRoleDao dao;

	@Autowired
	public void setDao(WorkflowStepRoleDao dao) {
		this.dao = dao;
	}
}
