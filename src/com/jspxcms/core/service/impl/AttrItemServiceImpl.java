package com.jspxcms.core.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.AttrItem;
import com.jspxcms.core.listener.AttrItemDeleteListener;
import com.jspxcms.core.repository.AttrItemDao;
import com.jspxcms.core.service.AttrItemService;

@Service
@Transactional(readOnly = true)
public class AttrItemServiceImpl implements AttrItemService {
	public AttrItem get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public AttrItem[] save(String[] name, Attr attr) {
		int len = name != null ? name.length : 0;
		AttrItem[] beans = new AttrItem[len];
		AttrItem bean;
		for (int i = 0; i < len; i++) {
			bean = new AttrItem();
			bean.setAttr(attr);
			bean.setSite(attr.getSite());
			bean.setName(name[i]);
			bean.setSeq(i);
			bean.applyDefaultValue();
			dao.save(bean);
			beans[i] = bean;
		}
		attr.setItems(Arrays.asList(beans));
		return beans;
	}

	@Transactional
	public AttrItem[] update(Integer[] id, String[] name, Attr attr) {
		int len = id != null ? id.length : 0;
		AttrItem[] beans = new AttrItem[len];
		AttrItem bean;
		// 修改和新增
		for (int i = 0; i < len; i++) {
			if (id[i] != null) {
				bean = dao.findOne(id[i]);
			} else {
				bean = new AttrItem();
			}
			bean.setAttr(attr);
			bean.setSite(attr.getSite());
			bean.setName(name[i]);
			bean.setSeq(i);
			bean.applyDefaultValue();
			dao.save(bean);
			beans[i] = bean;
		}
		// 删除
		for (AttrItem item : attr.getItems()) {
			if (!ArrayUtils.contains(id, item.getId())) {
				delete(item);
			}
		}
		attr.setItems(Arrays.asList(beans));
		return beans;
	}

	@Transactional
	public AttrItem delete(AttrItem bean) {
		firePreDelete(new Integer[] { bean.getId() });
		dao.delete(bean);
		return bean;
	}

	private void firePreDelete(Integer[] ids) {
		if (!CollectionUtils.isEmpty(deleteListeners)) {
			for (AttrItemDeleteListener listener : deleteListeners) {
				listener.preAttrItemDelete(ids);
			}
		}
	}

	private List<AttrItemDeleteListener> deleteListeners;

	@Autowired(required = false)
	public void setDeleteListeners(List<AttrItemDeleteListener> deleteListeners) {
		this.deleteListeners = deleteListeners;
	}

	private AttrItemDao dao;

	@Autowired
	public void setDao(AttrItemDao dao) {
		this.dao = dao;
	}
}
