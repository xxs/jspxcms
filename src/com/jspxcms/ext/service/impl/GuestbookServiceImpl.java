package com.jspxcms.ext.service.impl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.listener.SiteDeleteListener;
import com.jspxcms.core.listener.UserDeleteListener;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.DeleteException;
import com.jspxcms.ext.domain.Guestbook;
import com.jspxcms.ext.domain.GuestbookType;
import com.jspxcms.ext.listener.GuestbookTypeDeleteListener;
import com.jspxcms.ext.repository.GuestbookDao;
import com.jspxcms.ext.service.GuestbookService;
import com.jspxcms.ext.service.GuestbookTypeService;

/**
 * GuestbookServiceImpl
 * 
 * @author yangxing
 * 
 */
@Service
@Transactional(readOnly = true)
public class GuestbookServiceImpl implements GuestbookService,
		SiteDeleteListener, UserDeleteListener, GuestbookTypeDeleteListener {
	public Page<Guestbook> findAll(Map<String, String[]> params,
			Pageable pageable, Integer siteId) {
		return dao.findAll(spec(siteId, params), pageable);
	}

	public List<Guestbook> findList(Integer[] siteId, String[] type,
			Integer[] typeId, Boolean isRecommend, Integer[] status,
			Limitable limitable) {
		return dao.findList(siteId, type, typeId, isRecommend, status,
				limitable);
	}

	public Page<Guestbook> findPage(Integer[] siteId, String[] type,
			Integer[] typeId, Boolean isRecommend, Integer[] status,
			Pageable pageable) {
		return dao
				.findPage(siteId, type, typeId, isRecommend, status, pageable);
	}

	public RowSide<Guestbook> findSide(Map<String, String[]> params,
			Integer siteId, Guestbook bean, Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<Guestbook>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<Guestbook> list = dao.findAll(spec(siteId, params), limit);
		return RowSide.create(list, bean);
	}

	private Specification<Guestbook> spec(final Integer siteId,
			Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Guestbook> fs = SearchFilter.spec(filters,
				Guestbook.class);
		Specification<Guestbook> sp = new Specification<Guestbook>() {
			public Predicate toPredicate(Root<Guestbook> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fs.toPredicate(root, query, cb);
				if (siteId != null) {
					pred = cb.and(pred,
							cb.equal(root.get("site").get("id"), siteId));
				}
				return pred;
			}
		};
		return sp;
	}

	public Guestbook get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public Guestbook save(Guestbook bean, Integer userId, Integer typeId,
			String ip, Integer siteId) {
		Site site = siteService.get(siteId);
		User user = userService.get(userId);
		bean.setSite(site);
		bean.setCreator(user);
		GuestbookType type = guestbookTypeService.get(typeId);
		if (StringUtils.isBlank(bean.getCreationIp())) {
			bean.setCreationIp(ip);
		}
		bean.setType(type);
		if (StringUtils.isBlank(bean.getReplyText())) {
			bean.setReplyer(null);
			bean.setReplyIp(null);
			bean.setReplyDate(null);
			bean.setReply(false);
		} else {
			bean.setReplyer(user);
			bean.setReplyIp(ip);
			bean.setReplyDate(new Timestamp(System.currentTimeMillis()));
			bean.setReply(true);
		}
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Guestbook update(Guestbook bean, Integer userId, Integer typeId,
			String ip) {
		User user = userService.get(userId);
		if (StringUtils.isBlank(bean.getCreationIp())) {
			bean.setCreationIp(ip);
		}
		if (bean.getCreationDate() == null) {
			bean.setCreationDate(new Timestamp(System.currentTimeMillis()));
		}
		if (StringUtils.isBlank(bean.getReplyText())) {
			bean.setReplyer(null);
			bean.setReplyIp(null);
			bean.setReplyDate(null);
			bean.setReply(false);
		} else {
			if (bean.getReplyer() == null) {
				bean.setReplyer(user);
			}
			if (StringUtils.isBlank(bean.getReplyIp())) {
				bean.setReplyIp(ip);
			}
			if (bean.getReplyDate() == null) {
				bean.setReplyDate(new Timestamp(System.currentTimeMillis()));
			}
			bean.setReply(true);
		}
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Guestbook delete(Integer id) {
		Guestbook entity = dao.findOne(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public Guestbook[] delete(Integer[] ids) {
		Guestbook[] beans = new Guestbook[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = delete(ids[i]);
		}
		return beans;
	}

	public void preGuestbookTypeDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByTypeId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("guestbook.management");
			}
		}
	}

	public void preSiteDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countBySiteId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("guestbook.management");
			}
		}
	}

	public void preUserDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			// 如果用户回复了留言，则用户不允许删除。
			if (dao.countByReplyerId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("guestbook.management");
			}
			// 删除用户的留言
			dao.deleteByUserId(Arrays.asList(ids));
		}
	}

	@Autowired
	private SiteService siteService;
	@Autowired
	private GuestbookTypeService guestbookTypeService;
	@Autowired
	private UserService userService;
	@Autowired
	private GuestbookDao dao;

}
