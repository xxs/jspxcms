package com.jspxcms.core.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Tag
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_tag")
public class Tag implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public void applyDefaultValue() {
		if (getCreationDate() == null) {
			setCreationDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getRefers() == null) {
			setRefers(0);
		}
	}

	private Integer id;
	
	private Site site;
	
	private String name;
	private Date creationDate;
	private Integer refers;

	public Tag() {
	}

	public Tag(Site site, String name) {
		this.site = site;
		this.name = name;
	}

	@Id
	@Column(name = "f_tag_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_tag", pkColumnValue = "cms_tag", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_tag")
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

	@Column(name = "f_name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_creation_date", nullable = false, length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "f_refers", nullable = false)
	public Integer getRefers() {
		return refers;
	}

	public void setRefers(Integer refers) {
		this.refers = refers;
	}

}
