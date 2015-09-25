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
 * InfoSpec 高级规格
 * 
 * @author xxs
 * 
 */
@Entity
@Table(name = "cms_info_spec")
public class InfoSpec implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Info info;
	private Spec spec;

	private String value;

	public InfoSpec() {
	}

	public InfoSpec(Info info, Spec spec, String value) {
		this.info = info;
		this.spec = spec;
		this.value = value;
	}

	@Id
	@Column(name = "f_infospec_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_info_spec", pkColumnValue = "cms_info_spec", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_info_spec")
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
	@JoinColumn(name = "f_spec_id", nullable = false)
	public Spec getSpec() {
		return this.spec;
	}

	public void setSpec(Spec spec) {
		this.spec = spec;
	}
	
	@Column(name = "f_value")
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
