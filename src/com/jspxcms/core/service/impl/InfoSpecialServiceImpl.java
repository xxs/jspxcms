package com.jspxcms.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoSpecial;
import com.jspxcms.core.domain.Special;
import com.jspxcms.core.repository.InfoSpecialDao;
import com.jspxcms.core.service.InfoSpecialService;
import com.jspxcms.core.service.SpecialService;

@Service
@Transactional(readOnly = true)
public class InfoSpecialServiceImpl implements InfoSpecialService {
	@Transactional
	public List<InfoSpecial> save(Info info, Integer[] specialIds) {
		int len = ArrayUtils.getLength(specialIds);
		List<InfoSpecial> infoSpecials = new ArrayList<InfoSpecial>(len);
		info.setInfoSpecials(infoSpecials);
		if (len > 0) {
			InfoSpecial infoSpecial;
			Special special;
			for (Integer specialId : specialIds) {
				infoSpecial = new InfoSpecial();
				special = specialService.refer(specialId);
				infoSpecial.setSpecial(special);
				infoSpecial.setInfo(info);
				infoSpecials.add(infoSpecial);
				dao.save(infoSpecial);
			}
		}
		return infoSpecials;
	}

	@Transactional
	public List<InfoSpecial> update(Info info, Integer[] specialIds) {
		List<Special> specials = info.getSpecials();
		specialService.derefer(specials);
		dao.deleteByInfoId(info.getId());
		List<InfoSpecial> infoSpecials = save(info, specialIds);
		return infoSpecials;
	}

	@Transactional
	public int deleteByInfoId(Integer infoId) {
		return dao.deleteByInfoId(infoId);
	}

	@Transactional
	public int deleteBySpecialId(Integer specialId) {
		return dao.deleteBySpecialId(specialId);
	}

	private SpecialService specialService;

	@Autowired
	public void setSpecialService(SpecialService specialService) {
		this.specialService = specialService;
	}

	private InfoSpecialDao dao;

	@Autowired
	public void setDao(InfoSpecialDao dao) {
		this.dao = dao;
	}
}
