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
@Table(name = "cms_user_org")
public class UserOrg implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getOrgIndex() == null) {
			setOrgIndex(0);
		}
	}

	private Integer id;

	private User user;
	private Org org;

	private Integer orgIndex;

	public UserOrg() {
	}

	public UserOrg(User user, Org org, Integer orgIndex) {
		this.user = user;
		this.org = org;
		this.orgIndex = orgIndex;
	}

	@Id
	@Column(name = "f_userorg_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_user_org", pkColumnValue = "cms_user_org", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_user_org")
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
	@JoinColumn(name = "f_org_id", nullable = false)
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	@Column(name = "f_org_index", nullable = false)
	public Integer getOrgIndex() {
		return this.orgIndex;
	}

	public void setOrgIndex(Integer orgIndex) {
		this.orgIndex = orgIndex;
	}

}
