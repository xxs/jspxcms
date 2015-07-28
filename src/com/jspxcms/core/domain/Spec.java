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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.Valid;

/**
 * Entity - 规格
 */
@Entity
@Table(name = "cms_spec")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_spec_sequence")
public class Spec implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/** 名称 */
	private String name;
	
	private Site site;
	
	private Integer seq;

	/** 备注 */
	private String memo;

	/** 规格值 */
	private List<SpecValue> specValues = new ArrayList<SpecValue>();

//	/** 文档 */
//	private Set<Info> infos = new HashSet<Info>();

	@Id
	@Column(name = "f_spec_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_spec", pkColumnValue = "cms_spec", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_spec")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "f_seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_site_id", nullable = false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
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
	@OneToMany(mappedBy = "specification", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	//@OrderBy("order asc")
	public List<SpecValue> getSpecValues() {
		return specValues;
	}

	/**
	 * 设置规格值
	 * 
	 * @param specificationValues
	 *            规格值
	 */
	public void setSpecValues(List<SpecValue> specValues) {
		this.specValues = specValues;
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