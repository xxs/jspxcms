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
@Table(name = "cms_user_membergroup")
public class UserMemberGroup implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getGroupIndex() == null) {
			setGroupIndex(0);
		}
	}

	private Integer id;
	private User user;
	private MemberGroup group;
	private Integer groupIndex;

	public UserMemberGroup() {
	}

	public UserMemberGroup(User user, MemberGroup group, Integer groupIndex) {
		this.user = user;
		this.group = group;
		this.groupIndex = groupIndex;
	}

	@Id
	@Column(name = "f_usermgroup_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_user_membergroup", pkColumnValue = "cms_user_membergroup", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_user_membergroup")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_membergroup_id", nullable = false)
	public MemberGroup getGroup() {
		return group;
	}

	public void setGroup(MemberGroup group) {
		this.group = group;
	}

	@Column(name = "f_group_index", nullable = false)
	public Integer getGroupIndex() {
		return this.groupIndex;
	}

	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}

}
