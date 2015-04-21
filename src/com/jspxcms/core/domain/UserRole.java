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
@Table(name = "cms_user_role")
public class UserRole implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	// @PrePersist
	// @PreUpdate
	// public void prepareIndex() {
	// if (getUser() != null) {
	// setRoleIndex(getUser().getUserRoles().indexOf(this));
	// }
	// }

	@Transient
	public void applyDefaultValue() {
		if (getRoleIndex() == null) {
			setRoleIndex(0);
		}
	}

	public UserRole() {
	}

	public UserRole(User user, Role role, Integer roleIndex) {
		this.user = user;
		this.role = role;
		this.roleIndex = roleIndex;
	}

	private Integer id;
	private User user;
	private Role role;
	private Integer roleIndex;

	@Id
	@Column(name = "f_userrole_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_user_role", pkColumnValue = "cms_user_role", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_user_role")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_role_id", nullable = false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "f_role_index", nullable = false)
	public Integer getRoleIndex() {
		return this.roleIndex;
	}

	public void setRoleIndex(Integer roleIndex) {
		this.roleIndex = roleIndex;
	}

}
