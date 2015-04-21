package com.jspxcms.core.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoMemberGroup;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.listener.InfoDeleteListener;
import com.jspxcms.core.listener.MemberGroupDeleteListener;
import com.jspxcms.core.repository.InfoMemberGroupDao;
import com.jspxcms.core.service.InfoMemberGroupService;
import com.jspxcms.core.service.MemberGroupService;

@Service
@Transactional(readOnly = true)
public class InfoMemberGroupServiceImpl implements InfoMemberGroupService,
		InfoDeleteListener, MemberGroupDeleteListener {
	@Transactional
	public InfoMemberGroup save(Info info, MemberGroup group, Boolean viewPerm) {
		InfoMemberGroup bean = new InfoMemberGroup();
		bean.setInfo(info);
		bean.setGroup(group);
		bean.setViewPerm(viewPerm);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public void update(Info info, Integer[] viewGroupIds) {
		Integer infoId = info.getId();
		List<MemberGroup> groups = memberGroupService.findList();
		List<InfoMemberGroup> igs = dao.findByInfoId(infoId);
		Integer groupId;
		boolean contains, viewPerm;
		for (MemberGroup group : groups) {
			contains = false;
			groupId = group.getId();
			viewPerm = ArrayUtils.contains(viewGroupIds, groupId);
			for (InfoMemberGroup ig : igs) {
				if (ig.getGroup().getId().equals(groupId)) {
					if (viewGroupIds != null) {
						ig.setViewPerm(viewPerm);
					}
					contains = true;
				}
			}
			if (!contains) {
				save(info, group, viewPerm);
			}
		}
	}

	public void preInfoDelete(Integer[] ids) {
		if (ArrayUtils.isEmpty(ids)) {
			return;
		}
		dao.deleteByInfoId(Arrays.asList(ids));
	}

	public void preMemberGroupDelete(Integer[] ids) {
		if (ArrayUtils.isEmpty(ids)) {
			return;
		}
		dao.deleteByGroupId(Arrays.asList(ids));
	}

	private MemberGroupService memberGroupService;

	@Autowired
	public void setMemberGroupService(MemberGroupService memberGroupService) {
		this.memberGroupService = memberGroupService;
	}

	private InfoMemberGroupDao dao;

	@Autowired
	public void setDao(InfoMemberGroupDao dao) {
		this.dao = dao;
	}
}
