package com.jspxcms.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "cms_info_node")
public class InfoNode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@PrePersist
	@PreUpdate
	public void prepareIndex() {
		if (getInfo() != null) {
			setNodeIndex(getInfo().getInfoNodes().indexOf(this));
		}
	}

	private Integer id;
	private Info info;
	private Node node;
	
	private Integer nodeIndex;

	@Id
	@Column(name = "f_infonode_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_info_node", pkColumnValue = "cms_info_node", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_info_node")
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
	@JoinColumn(name = "f_node_id", nullable = false)
	public Node getNode() {
		return this.node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@Column(name = "f_node_index", nullable = false)
	public Integer getNodeIndex() {
		return this.nodeIndex;
	}

	public void setNodeIndex(Integer nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

}
