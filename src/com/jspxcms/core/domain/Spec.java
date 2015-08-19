/*



 */
package com.jspxcms.core.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * Entity - 规格
 */
@Entity
@Table(name = "cms_spec")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_spec_sequence")
public class Spec implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
	}
	
	@Transient
	public Set<Node> getInfoPerms() {
		Set<NodeSpec> nodeSpecs = getNodeSpecs();
		Set<Node> nodes = new HashSet<Node>();
		for (NodeSpec nr : nodeSpecs) {
			nodes.add(nr.getNode());
		}
		return nodes;
	}

	@Transient
	public Set<Node> getNodePerms() {
		Set<NodeSpec> nodeRoles = getNodeSpecs();
		Set<Node> nodes = new HashSet<Node>();
		for (NodeSpec nr : nodeRoles) {
			nodes.add(nr.getNode());
		}
		return nodes;
	}
	
	private Integer id;
	
	/** 名称 */
	private String name;
	
	private Site site;
	
	
	private Integer seq;

	/** 备注 */
	private String memo;

	/** 绑定分类 */
	private Set<NodeSpec> nodeSpecs = new HashSet<NodeSpec>(0);
	
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "spec")
	public Set<NodeSpec> getNodeSpecs() {
		return nodeSpecs;
	}

	public void setNodeSpecs(Set<NodeSpec> nodeSpecs) {
		this.nodeSpecs = nodeSpecs;
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
	@OneToMany(mappedBy = "spec", fetch = FetchType.LAZY)
	@OrderBy("seq asc")
	public List<SpecValue> getSpecValues() {
		return specValues;
	}

	/**
	 * 设置规格值
	 * 
	 * @param specValues
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