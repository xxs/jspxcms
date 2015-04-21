package com.jspxcms.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoTag;
import com.jspxcms.core.domain.Tag;
import com.jspxcms.core.repository.InfoTagDao;
import com.jspxcms.core.service.InfoTagService;
import com.jspxcms.core.service.TagService;

@Service
@Transactional(readOnly = true)
public class InfoTagServiceImpl implements InfoTagService {
	@Transactional
	public List<InfoTag> save(Info info, String[] tagNames) {
		int len = ArrayUtils.getLength(tagNames);
		List<InfoTag> infoTags = new ArrayList<InfoTag>(len);
		info.setInfoTags(infoTags);
		if (len > 0) {
			InfoTag infoTag;
			Tag tag;
			for (String tagName : tagNames) {
				infoTag = new InfoTag();
				tag = tagService.refer(tagName, info.getSite().getId());
				infoTag.setTag(tag);
				infoTag.setInfo(info);
				infoTags.add(infoTag);
				dao.save(infoTag);
			}
		}
		return infoTags;
	}

	@Transactional
	public List<InfoTag> update(Info info, String[] tagNames) {
		List<Tag> tags = info.getTags();
		tagService.derefer(tags);
		dao.deleteByInfoId(info.getId());
		List<InfoTag> infoTags = save(info, tagNames);
		return infoTags;
	}

	@Transactional
	public int deleteByInfoId(Integer infoId) {
		return dao.deleteByInfoId(infoId);
	}

	@Transactional
	public int deleteByTagId(Integer tagId) {
		return dao.deleteByTagId(tagId);
	}

	private TagService tagService;

	@Autowired
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	private InfoTagDao dao;

	@Autowired
	public void setDao(InfoTagDao dao) {
		this.dao = dao;
	}
}
