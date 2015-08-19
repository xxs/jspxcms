package com.jspxcms.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoParameter;
import com.jspxcms.core.domain.Parameter;
import com.jspxcms.core.repository.InfoParameterDao;
import com.jspxcms.core.service.InfoParameterService;
import com.jspxcms.core.service.ParameterService;

@Service
@Transactional(readOnly = true)
public class InfoParameterServiceImpl implements InfoParameterService {
	@Transactional
	public List<InfoParameter> save(Info info, String[] parameterNames) {
		int len = ArrayUtils.getLength(parameterNames);
		List<InfoParameter> infoParameters = new ArrayList<InfoParameter>(len);
		if (len > 0) {
			InfoParameter infoParameter;
			Parameter parameter;
			for (String parameterName : parameterNames) {
				infoParameter = new InfoParameter();
				parameter = parameterService.refer(parameterName, info.getSite().getId());
				parameter.setSite(info.getSite());
				parameter.set
				infoParameter.setParameter(parameter);
				infoParameter.setInfo(info);
				infoParameters.add(infoParameter);
				dao.save(infoParameter);
			}
		}
		return infoParameters;
	}

	@Transactional
	public List<InfoParameter> update(Info info, String[] parameterNames) {
		List<Parameter> parameters = info.getParameters();
		parameterService.derefer(parameters);
		dao.deleteByInfoId(info.getId());
		List<InfoParameter> infoParameters = save(info, parameterNames);
		return infoParameters;
	}

	@Transactional
	public int deleteByInfoId(Integer infoId) {
		return dao.deleteByInfoId(infoId);
	}

	@Transactional
	public int deleteByParameterId(Integer parameterId) {
		return dao.deleteByParameterId(parameterId);
	}

	private ParameterService parameterService;

	@Autowired
	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	private InfoParameterDao dao;

	@Autowired
	public void setDao(InfoParameterDao dao) {
		this.dao = dao;
	}
}
