package com.jspxcms.core.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoOrg;
import com.jspxcms.core.domain.Org;
import com.jspxcms.core.listener.InfoDeleteListener;
import com.jspxcms.core.listener.OrgDeleteListener;
import com.jspxcms.core.repository.InfoOrgDao;
import com.jspxcms.core.service.InfoOrgService;
import com.jspxcms.core.service.OrgService;

@Service
@Transactional(readOnly = true)
public class InfoOrgServiceImpl implements InfoOrgService, InfoDeleteListener,
		OrgDeleteListener {
	@Transactional
	public InfoOrg save(Info info, Org org, Boolean viewPerm) {
		InfoOrg bean = new InfoOrg();
		bean.setInfo(info);
		bean.setOrg(org);
		bean.setViewPerm(viewPerm);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public void update(Info info, Integer[] viewOrgIds) {
		Integer infoId = info.getId();
		List<Org> orgs = orgService.findList();
		List<InfoOrg> ios = dao.findByInfoId(infoId);
		Integer orgId;
		boolean contains, viewPerm;
		for (Org org : orgs) {
			contains = false;
			orgId = org.getId();
			viewPerm = ArrayUtils.contains(viewOrgIds, orgId);
			for (InfoOrg io : ios) {
				if (io.getOrg().getId().equals(orgId)) {
					if (viewOrgIds != null) {
						io.setViewPerm(viewPerm);
					}
					contains = true;
				}
			}
			if (!contains) {
				save(info, org, viewPerm);
			}
		}
	}

	public void preInfoDelete(Integer[] ids) {
		if (ArrayUtils.isEmpty(ids)) {
			return;
		}
		dao.deleteByInfoId(Arrays.asList(ids));
	}

	public void preOrgDelete(Integer[] ids) {
		if (ArrayUtils.isEmpty(ids)) {
			return;
		}
		dao.deleteByOrgId(Arrays.asList(ids));
	}

	private OrgService orgService;

	@Autowired
	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	private InfoOrgDao dao;

	@Autowired
	public void setDao(InfoOrgDao dao) {
		this.dao = dao;
	}
}
