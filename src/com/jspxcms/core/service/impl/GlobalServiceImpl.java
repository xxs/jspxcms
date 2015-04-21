package com.jspxcms.core.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Global;
import com.jspxcms.core.repository.GlobalDao;
import com.jspxcms.core.service.GlobalService;
import com.jspxcms.core.support.Configurable;

/**
 * GlobalServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class GlobalServiceImpl implements GlobalService {
	public Global findUnique() {
		Global global = dao.findOne(1);
		if (global == null) {
			throw new IllegalStateException("Global not exist!");
		}
		return dao.findOne(1);
	}

	@Transactional
	public Global update(Global bean) {
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public void updateConf(Configurable conf) {
		Global global = findUnique();
		Map<String, String> customs = global.getCustoms();
		Global.removeAttr(customs, conf.getPrefix());
		customs.putAll(conf.getCustoms());
	}

	private GlobalDao dao;

	@Autowired
	public void setGlobalDao(GlobalDao dao) {
		this.dao = dao;
	}
}
