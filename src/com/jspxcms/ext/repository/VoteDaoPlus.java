package com.jspxcms.ext.repository;

import com.jspxcms.ext.domain.Vote;

public interface VoteDaoPlus {
	/**
	 * 查询排列最前的投票
	 * 
	 * @param siteId
	 * @return
	 */
	public Vote findLatest(Integer[] status, Integer siteId);

	/**
	 * 按编码查询投票
	 * 
	 * @param number
	 * @param siteId
	 * @return
	 */
	public Vote findByNumber(String number, Integer[] status, Integer siteId);
}
