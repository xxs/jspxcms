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
	public List<InfoParameter> save(Info info, Integer[] parameterIds, String[] values) {
		int len = ArrayUtils.getLength(parameterIds);
		List<InfoParameter> infoParameters = new ArrayList<InfoParameter>(len);
		if (len > 0) {
			InfoParameter infoParameter;
			Parameter parameter;
			for (int i = 0; i < parameterIds.length; i++) {
				infoParameter = new InfoParameter();
				parameter = parameterService.get(parameterIds[i]);
				parameter.setSite(info.getSite());
				infoParameter.setParameter(parameter);
				infoParameter.setInfo(info);
				infoParameter.setValue(values[i]);
				infoParameters.add(infoParameter);
				dao.save(infoParameter);
			}
		}
		return infoParameters;
	}

	@Transactional
	public List<InfoParameter> update(Info info, Integer[] parameterIds, String[] values) {
		dao.deleteByInfoId(info.getId());
		List<InfoParameter> infoParameters = save(info, parameterIds,values);
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
