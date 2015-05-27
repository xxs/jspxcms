package com.jspxcms.core.service;

import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.AttrItem;

public interface AttrItemService {
	public AttrItem get(Integer id);

	public AttrItem[] save(String[] name, Attr attr);

	public AttrItem[] update(Integer[] id, String[] name, Attr attr);

	public AttrItem delete(AttrItem bean);
}
