package com.jspxcms.core.service;

import java.util.List;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoParameter;

public interface InfoParameterService {
	public List<InfoParameter> save(Info info, String[] parameterNames);

	public List<InfoParameter> update(Info info, String[] parameterNames);

	public int deleteByInfoId(Integer infoId);

	public int deleteByTagId(Integer parameterId);
}
