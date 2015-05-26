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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * Attr
 * 
 * @author xxs
 * 
 */
@Entity
@Table(name = "cms_attr")
public class Attr implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
	}

	@Transient
	public Set<Node> getInfoPerms() {
		Set<NodeAttr> nodeAttrs = getNodeAttrs();
		Set<Node> nodes = new HashSet<Node>();
		for (NodeAttr nr : nodeAttrs) {
			if (nr.getInfoPerm()) {
				nodes.add(nr.getNode());
			}
		}
		return nodes;
	}

	@Transient
	public Set<Node> getNodePerms() {
		Set<NodeAttr> nodeAttrs = getNodeAttrs();
		Set<Node> nodes = new HashSet<Node>();
		for (NodeAttr nr : nodeAttrs) {
			if (nr.getNodePerm()) {
				nodes.add(nr.getNode());
			}
		}
		return nodes;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attr")
	public Set<NodeAttr> getNodeAttrs() {
		return nodeAttrs;
	}

	public void setNodeAttrs(Set<NodeAttr> nodeAttrs) {
		this.nodeAttrs = nodeAttrs;
	}
	
	private Set<NodeAttr> nodeAttrs = new HashSet<NodeAttr>(0);
	
	private Integer id;
	
	private Site site;
	
	private String number;
	private String name;
	private Integer seq;
	
	/** 可选项 */
	private List<AttrItem> items = new ArrayList<AttrItem>();
	
	@Id
	@Column(name = "f_attr_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_attr", pkColumnValue = "cms_attr", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_attr")
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

	@Column(name = "f_number", length = 20)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "f_name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "f_seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	@OrderBy("seq asc")
	public List<AttrItem> getItems() {
		return this.items;
	}

	public void setItems(List<AttrItem> items) {
		this.items = items;
	}
	
}
