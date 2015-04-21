package com.jspxcms.core.domain;

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
 * InfoSpecial
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_info_special")
public class InfoSpecial implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@PrePersist
	@PreUpdate
	public void prepareIndex() {
		if (getInfo() != null) {
			setSpecialIndex(getInfo().getInfoSpecials().indexOf(this));
		}
	}

	private Integer id;
	private Info info;
	private Special special;
	
	private Integer specialIndex;

	@Id
	@Column(name = "f_infospecial_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_info_special", pkColumnValue = "cms_info_special", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_info_special")
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
	@JoinColumn(name = "f_special_id", nullable = false)
	public Special getSpecial() {
		return special;
	}

	public void setSpecial(Special special) {
		this.special = special;
	}

	@Column(name = "f_special_index", nullable = false)
	public Integer getSpecialIndex() {
		return this.specialIndex;
	}

	public void setSpecialIndex(Integer specialIndex) {
		this.specialIndex = specialIndex;
	}

}
