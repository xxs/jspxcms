package com.jspxcms.core.service;

import java.util.List;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoTag;

public interface InfoTagService {
	public List<InfoTag> save(Info info, String[] tagNames);

	public List<InfoTag> update(Info info, String[] tagNames);

	public int deleteByInfoId(Integer infoId);

	public int deleteByTagId(Integer tagId);
}
