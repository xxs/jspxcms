package com.jspxcms.core.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.jspxcms.common.web.Anchor;

/**
 * Comment
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_comment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "f_ftype", discriminatorType = DiscriminatorType.STRING)
public abstract class Comment implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 未审核
	 */
	public static final int SAVED = 0;
	/**
	 * 已审核
	 */
	public static final int AUDITED = 1;
	/**
	 * 推荐
	 */
	public static final int RECOMMEND = 2;
	/**
	 * 屏蔽
	 */
	public static final int DISABLED = 3;

	@Transient
	public abstract Anchor getAnchor();

	public void applyDefaultValue() {
		if (getCreationDate() == null) {
			setCreationDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getScore() == null) {
			setScore(0);
		}
		if (getStatus() == null) {
			setStatus(SAVED);
		}
	}

	private Integer id;
	
	private Site site;
	private User creator;
	private User auditor;

	private String ftype;
	private Integer fid;
	private Date creationDate;
	private Date auditDate;
	private String ip;
	private Integer score;
	private Integer status;
	private String text;

	@Id
	@Column(name = "f_comment_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_comment", pkColumnValue = "cms_comment", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_comment")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_site_id", nullable = false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_creator_id", nullable = false)
	public User getCreator() {
		return this.creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_auditor_id", nullable = false)
	public User getAuditor() {
		return this.auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}

	@Column(name = "f_ftype", nullable = false, length = 50, insertable = false, updatable = false)
	public String getFtype() {
		return this.ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	@Column(name = "f_fid", nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_creation_date", nullable = false, length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_audit_date", length = 19)
	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@Column(name = "f_score", nullable = false)
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "f_ip", nullable = true, length = 100)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "f_status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "f_text")
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
