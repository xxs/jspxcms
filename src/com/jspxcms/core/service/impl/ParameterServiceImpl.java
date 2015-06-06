package com.jspxcms.core.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Parameter;
import com.jspxcms.core.domain.ParameterGroup;
import com.jspxcms.core.listener.ParameterDeleteListener;
import com.jspxcms.core.repository.ParameterDao;
import com.jspxcms.core.service.ParameterService;

@Service
@Transactional(readOnly = true)
public class ParameterServiceImpl implements ParameterService {
	public Parameter get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public Parameter[] save(String[] name, ParameterGroup parameterGroup) {
		int len = name != null ? name.length : 0;
		Parameter[] beans = new Parameter[len];
		Parameter bean;
		for (int i = 0; i < len; i++) {
			bean = new Parameter();
			bean.setParameterGroup(parameterGroup);
			bean.setSite(parameterGroup.getSite());
			bean.setName(name[i]);
			bean.setSeq(i);
			bean.applyDefaultValue();
			dao.save(bean);
			beans[i] = bean;
		}
		parameterGroup.setParameters(Arrays.asList(beans));
		return beans;
	}

	@Transactional
	public Parameter[] update(Integer[] id, String[] name, ParameterGroup parameterGroup) {
		int len = id != null ? id.length : 0;
		Parameter[] beans = new Parameter[len];
		Parameter bean;
		// 修改和新增
		for (int i = 0; i < len; i++) {
			if (id[i] != null) {
				bean = dao.findOne(id[i]);
			} else {
				bean = new Parameter();
			}
			bean.setParameterGroup(parameterGroup);
			bean.setSite(parameterGroup.getSite());
			bean.setName(name[i]);
			bean.setSeq(i);
			bean.applyDefaultValue();
			dao.save(bean);
			beans[i] = bean;
		}
		// 删除
		for (Parameter item : parameterGroup.getParameters()) {
			if (!ArrayUtils.contains(id, item.getId())) {
				delete(item);
			}
		}
		parameterGroup.setParameters(Arrays.asList(beans));
		return beans;
	}

	@Transactional
	public Parameter delete(Parameter bean) {
		firePreDelete(new Integer[] { bean.getId() });
		dao.delete(bean);
		return bean;
	}

	private void firePreDelete(Integer[] ids) {
		if (!CollectionUtils.isEmpty(deleteListeners)) {
			for (ParameterDeleteListener listener : deleteListeners) {
				listener.preParameterDelete(ids);
			}
		}
	}

	private List<ParameterDeleteListener> deleteListeners;

	@Autowired(required = false)
	public void setDeleteListeners(List<ParameterDeleteListener> deleteListeners) {
		this.deleteListeners = deleteListeners;
	}

	private ParameterDao dao;

	@Autowired
	public void setDao(ParameterDao dao) {
		this.dao = dao;
	}
}
