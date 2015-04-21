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
@Table(name = "cms_node_membergroup")
public class NodeMemberGroup implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getViewPerm() == null) {
			setViewPerm(true);
		}
		if (getContriPerm() == null) {
			setContriPerm(true);
		}
		if (getCommentPerm() == null) {
			setCommentPerm(true);
		}
	}

	private Integer id;
	private Node node;
	private MemberGroup group;
	private Boolean viewPerm;
	private Boolean contriPerm;
	private Boolean commentPerm;

	@Id
	@Column(name = "f_nodemgroup_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_node_membergroup", pkColumnValue = "cms_node_membergroup", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_node_membergroup")
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
	@JoinColumn(name = "f_membergroup_id", nullable = false)
	public MemberGroup getGroup() {
		return group;
	}

	public void setGroup(MemberGroup group) {
		this.group = group;
	}

	@Column(name = "f_is_view_perm", nullable = false, length = 1)
	public Boolean getViewPerm() {
		return this.viewPerm;
	}

	public void setViewPerm(Boolean viewPerm) {
		this.viewPerm = viewPerm;
	}

	@Column(name = "f_is_contri_perm", nullable = false, length = 1)
	public Boolean getContriPerm() {
		return this.contriPerm;
	}

	public void setContriPerm(Boolean contriPerm) {
		this.contriPerm = contriPerm;
	}

	@Column(name = "f_is_comment_perm", nullable = false, length = 1)
	public Boolean getCommentPerm() {
		return this.commentPerm;
	}

	public void setCommentPerm(Boolean commentPerm) {
		this.commentPerm = commentPerm;
	}

}
