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

/**
 * Entity - 规格值
 */
@Entity
@Table(name = "cms_spec_value")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_spec_val_sequence")
public class SpecValue implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/** 名称 */
	private String name;

	/** 图片 */
	private String image;
	
	/** 描述 */
	private String description;

	/** 规格 */
	private Spec specification;

//	/** 文档 */
//	private Set<Info> infos = new HashSet<Info>();
	
	@Id
	@Column(name = "f_spec_value_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_spec_value", pkColumnValue = "cms_spec_value", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_spec_value")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@Column(name = "f_name", nullable = false, length = 50)
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
	 * 获取描述
	 * 
	 * @return 描述
	 */
	@Column(name = "f_description", length = 500)
	public String getDescription() {
		return description;
	}
	
	/**
	 * 设置描述
	 * 
	 * @param description
	 *            描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取图片
	 * 
	 * @return 图片
	 */
	@Column(name = "f_image", length = 500)
	public String getImage() {
		return image;
	}

	/**
	 * 设置图片
	 * 
	 * @param image
	 *            图片
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取规格
	 * 
	 * @return 规格
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Spec getSpecification() {
		return specification;
	}

	/**
	 * 设置规格
	 * 
	 * @param specification
	 *            规格
	 */
	public void setSpecification(Spec specification) {
		this.specification = specification;
	}

//	/**
//	 * 获取文档
//	 * 
//	 * @return 文档
//	 */
//	@ManyToMany(mappedBy = "specificationValues", fetch = FetchType.LAZY)
//	public Set<Info> getInfos() {
//		return infos;
//	}
//
//	/**
//	 * 设置文档
//	 * 
//	 * @param infos
//	 *            文档
//	 */
//	public void setInfos(Set<Info> infos) {
//		this.infos = infos;
//	}

}