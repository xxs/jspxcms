package com.jspxcms.ext.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.ext.domain.VisitLog;

public interface VisitLogDao extends Repository<VisitLog, Integer>,
		VisitLogDaoPlus {
	public Page<VisitLog> findAll(Specification<VisitLog> spec,
			Pageable pageable);

	public List<VisitLog> findAll(Specification<VisitLog> spec,
			Limitable limitable);

	public VisitLog findOne(Integer id);

	public VisitLog save(VisitLog bean);

	public void delete(VisitLog bean);

	// --------------------

	@Query("select count(*) as _pv,count(distinct bean.cookie) as _uv,count(distinct bean.ip) as _ip from VisitLog bean where bean.date = ?1 and bean.site.id= ?2")
	public List<Object[]> trafficByDate(String date, Integer siteId);

	@Query("select bean.date as _date,count(*) as _pv,count(distinct bean.cookie) as _uv,count(distinct bean.ip) as _ip from VisitLog bean where bean.date >= ?1 and bean.date <= ?2 and bean.site.id = ?3 group by bean.date order by bean.date desc")
	public Page<Object[]> trafficByDate(String begin, String end,
			Integer siteId, Pageable pageable);

	@Query("select bean.url as _url,count(bean.url) as _pv,count(distinct bean.cookieDate) as _uv,count(distinct bean.ipDate) as _ip from VisitLog bean where bean.date >= ?1 and bean.date <= ?2 and bean.site.id = ?3 group by bean.url order by count(bean.url) desc")
	public Page<Object[]> urlByDate(String begin, String end, Integer siteId,
			Pageable pageable);

	@Modifying
	@Query("delete from VisitLog bean where bean.date <= ?1")
	public int deleteByDate(String before);

	@Modifying
	@Query("delete from VisitLog bean where bean.site.id in (?1)")
	public int deleteBySiteId(Collection<Integer> siteIds);

}
