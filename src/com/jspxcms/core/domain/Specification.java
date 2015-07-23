/*



 */
package com.jspxcms.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 规格
 * 


 */
@Entity
@Table(name = "cms_specification")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_specification_sequence")
public class Specification implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/**
	 * 类型
	 */
	public enum Type {

		/** 文本 */
		text,

		/** 图片 */
		image
	};

	/** 名称 */
	private String name;

	/** 类型 */
	private Type type;

	/** 备注 */
	private String memo;

	/** 规格值 */
	private List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>();

//	/** 文档 */
//	private Set<Info> infos = new HashSet<Info>();

	@Id
	@Column(name = "f_specification_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_specification", pkColumnValue = "cms_specification", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_specification")
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
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@NotNull
	@Column(nullable = false)
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	@Column(name = "f_memo", length = 200)
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取规格值
	 * 
	 * @return 规格值
	 */
	@Valid
	@NotEmpty
	@OneToMany(mappedBy = "specification", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	//@OrderBy("order asc")
	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	/**
	 * 设置规格值
	 * 
	 * @param specificationValues
	 *            规格值
	 */
	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

//	/**
//	 * 获取商品
//	 * 
//	 * @return 商品
//	 */
//	@ManyToMany(mappedBy = "specifications", fetch = FetchType.LAZY)
//	public Set<Info> getInfos() {
//		return infos;
//	}
//
//	/**
//	 * 设置商品
//	 * 
//	 * @param products
//	 *            商品
//	 */
//	public void setProducts(Set<Info> infos) {
//		this.infos = infos;
//	}

}