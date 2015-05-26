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
import javax.persistence.Transient;

@Entity
@Table(name = "cms_node_attr")
public class NodeAttr implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getNodePerm() == null) {
			setNodePerm(true);
		}
		if (getInfoPerm() == null) {
			setInfoPerm(true);
		}
	}

	private Integer id;
	private Node node;
	private Attr attr;
	private Boolean nodePerm;
	private Boolean infoPerm;

	@Id
	@Column(name = "f_nodeattr_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_node_attr", pkColumnValue = "cms_node_attr", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_node_attr")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_node_id", nullable = false)
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_attr_id", nullable = false)
	public Attr getAttr() {
		return attr;
	}

	public void setAttr(Attr attr) {
		this.attr = attr;
	}

	@Column(name = "f_is_node_perm", nullable = false, length = 1)
	public Boolean getNodePerm() {
		return this.nodePerm;
	}

	public void setNodePerm(Boolean nodePerm) {
		this.nodePerm = nodePerm;
	}

	@Column(name = "f_is_info_perm", nullable = false, length = 1)
	public Boolean getInfoPerm() {
		return this.infoPerm;
	}

	public void setInfoPerm(Boolean infoPerm) {
		this.infoPerm = infoPerm;
	}

}
