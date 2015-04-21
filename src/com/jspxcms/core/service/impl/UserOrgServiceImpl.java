package com.jspxcms.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Org;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserOrg;
import com.jspxcms.core.listener.OrgDeleteListener;
import com.jspxcms.core.repository.UserOrgDao;
import com.jspxcms.core.service.OrgService;
import com.jspxcms.core.service.UserOrgService;

@Service
@Transactional(readOnly = true)
public class UserOrgServiceImpl implements UserOrgService, OrgDeleteListener {
	@Transactional
	public void save(User user, Integer[] orgIds, Integer orgId) {
		Org org = orgService.get(orgId);
		dao.save(new UserOrg(user, org, 0));
		if (orgIds != null) {
			for (int i = 0, len = orgIds.length; i < len; i++) {
				// 扩展组织不重复主组织
				if (orgId.equals(orgIds[i])) {
					continue;
				}
				org = orgService.get(orgIds[i]);
				dao.save(new UserOrg(user, org, i + 1));
			}
		}
	}

	@Transactional
	public void update(User user, Integer[] orgIds, Integer orgId,
			Integer topOrgId) {
		// 主组织ID不存在，不更新。
		if (orgId == null) {
			return;
		}
		List<UserOrg> uos = user.getUserOrgs();
		Org org;
		if (!uos.isEmpty()) {
			dao.delete(uos.get(0));
			uos.remove(0);
		}
		org = orgService.get(orgId);
		dao.save(new UserOrg(user, org, 0));
		// 扩展组织ID不存在，不更新扩展组织。
		if (orgIds == null) {
			return;
		}
		String treeNumber = null;
		if (topOrgId != null) {
			treeNumber = orgService.get(topOrgId).getTreeNumber();
		}
		for (UserOrg uo : uos) {
			if (treeNumber == null
					|| uo.getOrg().getTreeNumber().startsWith(treeNumber)) {
				dao.delete(uo);
			}
		}
		for (int i = 0, len = orgIds.length; i < len; i++) {
			// 扩展组织不重复主组织
			if (orgId.equals(orgIds[i])) {
				continue;
			}
			org = orgService.get(orgIds[i]);
			dao.save(new UserOrg(user, org, i + 1));
		}
	}

	@Transactional
	public int deleteByUserId(Integer userId) {
		return dao.deleteByUserId(userId);
	}

	@Transactional
	public int deleteByOrgId(Integer orgId) {
		return dao.deleteByOrgId(orgId);
	}

	@Transactional
	public void preOrgDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.deleteByOrgId(id);
			}
		}
	}

	private OrgService orgService;

	@Autowired
	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	private UserOrgDao dao;

	@Autowired
	public void setDao(UserOrgDao dao) {
		this.dao = dao;
	}
}
