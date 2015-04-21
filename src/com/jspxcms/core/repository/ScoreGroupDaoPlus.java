package com.jspxcms.core.repository;

import com.jspxcms.core.domain.ScoreGroup;

public interface ScoreGroupDaoPlus {
	public ScoreGroup findTopOne(Integer siteId);
}
