package com.jspxcms.core.service;

import com.jspxcms.core.domain.Parameter;
import com.jspxcms.core.domain.ParameterGroup;

public interface ParameterService {
	public Parameter get(Integer id);

	public Parameter[] save(String[] name, ParameterGroup parameter);

	public Parameter[] update(Integer[] id, String[] name, ParameterGroup parameter);

	public Parameter delete(Parameter bean);
}
