package com.jspxcms.core.service;

import com.jspxcms.core.domain.Global;
import com.jspxcms.core.support.Configurable;

/**
 * GlobalService
 * 
 * @author liufang
 * 
 */
public interface GlobalService {
	public Global findUnique();

	public Global update(Global bean);

	public void updateConf(Configurable conf);
}
