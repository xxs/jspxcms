package com.jspxcms.core.service;

import java.util.List;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoSpecial;

public interface InfoSpecialService {
	public List<InfoSpecial> save(Info info, Integer[] specialIds);

	public List<InfoSpecial> update(Info info, Integer[] specialIds);

	public int deleteByInfoId(Integer infoId);

	public int deleteBySpecialId(Integer specialId);
}
