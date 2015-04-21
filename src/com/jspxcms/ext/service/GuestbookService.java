package com.jspxcms.ext.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.ext.domain.Guestbook;

/**
 * GuestbookService
 * 
 * @author yangxing
 * 
 */
public interface GuestbookService {
	public Page<Guestbook> findAll(Map<String, String[]> params,
			Pageable pageable, Integer siteId);

	public List<Guestbook> findList(Integer[] siteId, String[] type,
			Integer[] typeId, Boolean isRecommend, Integer[] status,
			Limitable limitable);

	public Page<Guestbook> findPage(Integer[] siteId, String[] type,
			Integer[] typeId, Boolean isRecommend, Integer[] status,
			Pageable pageable);

	public RowSide<Guestbook> findSide(Map<String, String[]> params,
			Integer siteId, Guestbook bean, Integer position, Sort sort);

	public Guestbook get(Integer id);

	public Guestbook save(Guestbook bean, Integer userId, Integer typeId,
			String ip, Integer siteId);

	public Guestbook update(Guestbook bean, Integer userId, Integer typeId,
			String ip);

	public Guestbook delete(Integer id);

	public Guestbook[] delete(Integer[] ids);
}
