package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.InfoSpecial;

public interface InfoSpecialDao extends Repository<InfoSpecial, Integer> {
	public List<InfoSpecial> findAll(Specification<InfoSpecial> spec, Sort sort);

	public List<InfoSpecial> findAll(Specification<InfoSpecial> spec,
			Limitable limit);

	public InfoSpecial findOne(Integer id);

	public InfoSpecial save(InfoSpecial bean);

	public void delete(InfoSpecial bean);

	// --------------------

	@Modifying
	@Query("delete from InfoSpecial bean where bean.info.id=?1")
	public int deleteByInfoId(Integer infoId);

	@Modifying
	@Query("delete from InfoSpecial bean where bean.special.id=?1")
	public int deleteBySpecialId(Integer specialId);
}
