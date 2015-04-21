package com.jspxcms.ext.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.ext.domain.Vote;

public interface VoteService {
	public Page<Vote> findAll(Integer siteId, Map<String, String[]> params,
			Pageable pageable);

	public RowSide<Vote> findSide(Integer siteId, Map<String, String[]> params,
			Vote bean, Integer position, Sort sort);

	public boolean numberExist(String number, Integer siteId);

	public Vote findByNumber(String number, Integer[] status, Integer siteId);

	public Vote findLatest(Integer[] status, Integer siteId);

	public Vote get(Integer id);

	public Vote vote(Integer id, Integer[] optionIds, Integer userId,
			String ip, String cookie);

	public Vote save(Vote bean, String[] title, Integer[] count, Integer siteId);

	public Vote update(Vote bean, Integer[] id, String[] title, Integer[] count);

	public Vote delete(Integer id);

	public Vote[] delete(Integer[] ids);

}
