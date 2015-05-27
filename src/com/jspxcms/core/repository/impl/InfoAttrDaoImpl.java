package com.jspxcms.core.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.core.domain.InfoAttr;
import com.jspxcms.core.repository.InfoAttrDaoPlus;

/**
 * InfoAttrDaoImpl
 * 
 * @author xxs
 * 
 */
public class InfoAttrDaoImpl implements InfoAttrDaoPlus {
	@SuppressWarnings("unchecked")
	public List<InfoAttr> getByInfoId(Integer infoId) {
		JpqlBuilder jqpl = new JpqlBuilder("from InfoAttr bean where 1=1");
		if (infoId!=null) {
			jqpl.append(" and bean.info.id = (:infoId)");
			jqpl.setParameter("infoId", infoId);
		}
		return jqpl.list(em);
	}

	private EntityManager em;

	@javax.persistence.PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
