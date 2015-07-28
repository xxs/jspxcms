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

@Entity
@Table(name = "cms_parameter_group")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_parameter_group_sequence")
public class ParameterGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
	}
	
	@Transient
	public Set<Node> getNodePerms() {
		Set<Node> nodes = new HashSet<Node>();
		nodes.add(this.node);
		return nodes;
	}
	
	private Integer id;
	
	/** 名称 */
	private String name;

	/** 绑定分类 */
	private Node node;
	
	private Site site;
	
	private Integer seq;
	
	/** 参数 */
	private List<Parameter> parameters = new ArrayList<Parameter>();
	
	@Id
	@Column(name = "f_parametergroup_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_parametergroup", pkColumnValue = "cms_parametergroup", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_parametergroup")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_site_id", nullable = false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	/**
	 * 获取绑定node
	 * 
	 * @return 绑定node
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_node_id", nullable = false)
	public Node getNode() {
		return node;
	}

	/**
	 * 设置绑定node
	 * 
	 * @param node
	 *            绑定node
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * 获取参数
	 * 
	 * @return 参数
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parameterGroup")
	@OrderBy("seq asc")
	public List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * 设置参数
	 * 
	 * @param parameters
	 *            参数
	 */
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

}