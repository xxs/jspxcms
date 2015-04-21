package com.jspxcms.core.domain;

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

/**
 * InfoAttribute
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_info_attribute")
public class InfoAttribute implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Info info;
	private Attribute attribute;

	private String image;

	public InfoAttribute() {
	}

	public InfoAttribute(Info info, Attribute attribute, String image) {
		this.info = info;
		this.attribute = attribute;
		this.image = image;
	}

	@Id
	@Column(name = "f_infoattr_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_info_attribute", pkColumnValue = "cms_info_attribute", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_info_attribute")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_info_id", nullable = false)
	public Info getInfo() {
		return this.info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_attribute_id", nullable = false)
	public Attribute getAttribute() {
		return this.attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	@Column(name = "f_image")
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
