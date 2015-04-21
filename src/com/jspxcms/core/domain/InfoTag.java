package com.jspxcms.core.domain;

// Generated 2013-6-24 15:12:04 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * InfoTag
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_info_tag")
public class InfoTag implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@PrePersist
	@PreUpdate
	public void prepareIndex() {
		if (getInfo() != null) {
			setTagIndex(getInfo().getInfoTags().indexOf(this));
		}
	}

	private Integer id;
	private Info info;
	private Tag tag;
	
	private Integer tagIndex;

	@Id
	@Column(name = "f_infotag_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_info_tag", pkColumnValue = "cms_info_tag", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_info_tag")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_info_id", nullable = false)
	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_tag_id", nullable = false)
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@Column(name = "f_tag_index", nullable = false)
	public Integer getTagIndex() {
		return tagIndex;
	}

	public void setTagIndex(Integer tagIndex) {
		this.tagIndex = tagIndex;
	}

}
