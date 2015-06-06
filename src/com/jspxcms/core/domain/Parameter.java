package com.jspxcms.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * Entity - 参数
 */
@Entity
@Table(name = "cms_parameter")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_parameter_sequence")
public class Parameter implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Transient
	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
	}
	
	private Integer id;

	/** 名称 */
	private String name;
	
	private Site site;

	/** 参数组 */
	private ParameterGroup parameterGroup;
	
	private Integer seq;

	
	@Id
	@Column(name = "f_parameter_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_parameter", pkColumnValue = "cms_parameter", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_parameter")
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
	
	@Column(name = "f_seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@Column(name = "f_name", nullable = false, length = 100)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取参数组
	 * 
	 * @return 参数组
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_parametergroup_id", nullable = false)
	public ParameterGroup getParameterGroup() {
		return parameterGroup;
	}

	/**
	 * 设置参数组
	 * 
	 * @param parameterGroup
	 *            参数组
	 */
	public void setParameterGroup(ParameterGroup parameterGroup) {
		this.parameterGroup = parameterGroup;
	}

}