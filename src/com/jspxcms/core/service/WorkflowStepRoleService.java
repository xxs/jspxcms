package com.jspxcms.core.service;

import com.jspxcms.core.domain.WorkflowStep;

public interface WorkflowStepRoleService {
	public void save(WorkflowStep step, Integer[] roleIds);

	public void update(WorkflowStep step, Integer[] roleIds);
}
