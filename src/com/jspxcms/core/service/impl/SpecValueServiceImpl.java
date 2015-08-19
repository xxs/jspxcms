package com.jspxcms.core.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Spec;
import com.jspxcms.core.domain.SpecValue;
import com.jspxcms.core.listener.SpecValueDeleteListener;
import com.jspxcms.core.repository.SpecValueDao;
import com.jspxcms.core.service.SpecValueService;

@Service
@Transactional(readOnly = true)
public class SpecValueServiceImpl implements SpecValueService {
	
	public SpecValue get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public SpecValue[] save(String[] name, String[] image,
			Spec spec) {
		int len = name != null ? name.length : 0;
		SpecValue[] beans = new SpecValue[len];
		SpecValue bean;
		for (int i = 0; i < len; i++) {
			bean = new SpecValue();
			bean.setSpec(spec);
			bean.setName(name[i]);
//			bean.setImage(image[i]);
			bean.setSeq(i);
			bean.applyDefaultValue();
			dao.save(bean);
			beans[i] = bean;
		}
		spec.setSpecValues(Arrays.asList(beans));
		return beans;
	}

	@Transactional
	public SpecValue[] update(Integer[] id, String[] name,
			String[] image, Spec spec) {
		int len = id != null ? id.length : 0;
		SpecValue[] beans = new SpecValue[len];
		SpecValue bean;
		// 修改和新增
		for (int i = 0; i < len; i++) {
			if (id[i] != null) {
				bean = dao.findOne(id[i]);
			} else {
				bean = new SpecValue();
			}
			bean.setSpec(spec);
			bean.setName(name[i]);
//			bean.setImage(image[i]);
			bean.setSeq(i);
			bean.applyDefaultValue();
			dao.save(bean);
			beans[i] = bean;
		}
		// 删除
		for (SpecValue item : spec.getSpecValues()) {
			if (!ArrayUtils.contains(id, item.getId())) {
				delete(item);
			}
		}
		spec.setSpecValues(Arrays.asList(beans));
		return beans;
	}

	@Transactional
	public SpecValue delete(SpecValue bean) {
		firePreDelete(new Integer[] { bean.getId() });
		dao.delete(bean);
		return bean;
	}

	private void firePreDelete(Integer[] ids) {
		if (!CollectionUtils.isEmpty(deleteListeners)) {
			for (SpecValueDeleteListener listener : deleteListeners) {
				listener.preSpecValueDelete(ids);
			}
		}
	}

	private List<SpecValueDeleteListener> deleteListeners;

	@Autowired(required = false)
	public void setDeleteListeners(List<SpecValueDeleteListener> deleteListeners) {
		this.deleteListeners = deleteListeners;
	}

	private SpecValueDao dao;

	@Autowired
	public void setDao(SpecValueDao dao) {
		this.dao = dao;
	}

}
