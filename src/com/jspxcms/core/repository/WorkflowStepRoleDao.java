package com.jspxcms.core.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.WorkflowStepRole;

public interface WorkflowStepRoleDao extends
		Repository<WorkflowStepRole, Integer> {

	public WorkflowStepRole findOne(Integer id);

	public WorkflowStepRole save(WorkflowStepRole bean);

	void delete(Iterable<WorkflowStepRole> entities);

	// --------------------

	@Query("select count(*) from WorkflowStepRole bean where bean.role.id in (?1)")
	public long countByRoleId(Collection<Integer> roleIds);
}
