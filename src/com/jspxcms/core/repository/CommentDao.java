package com.jspxcms.core.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.Comment;

/**
 * CommentDao
 * 
 * @author liufang
 * 
 */
public interface CommentDao extends Repository<Comment, Integer>,
		CommentDaoPlus {
	public Page<Comment> findAll(Specification<Comment> spec, Pageable pageable);

	public List<Comment> findAll(Specification<Comment> spec,
			Limitable limitable);

	public Comment findOne(Integer id);

	public Comment save(Comment bean);

	public void delete(Comment bean);

	// --------------------

	@Modifying
	@Query("delete from Comment bean where bean.ftype=?1 and bean.fid=?2")
	public int deleteByFtypeAndFid(String ftype, Integer fid);

	@Modifying
	@Query("delete from Comment bean where bean.site.id in (?1)")
	public int deleteBySiteId(Collection<Integer> siteIds);

	@Modifying
	@Query("delete from Comment bean where bean.creator.id in (?1)")
	public int deleteByCreatorId(Collection<Integer> creatorIds);

	@Modifying
	@Query("delete from Comment bean where bean.auditor.id in (?1)")
	public int deleteByAuditorId(Collection<Integer> auditorIds);
}
