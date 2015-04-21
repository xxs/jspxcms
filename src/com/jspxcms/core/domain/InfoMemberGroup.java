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
@Table(name = "cms_info_membergroup")
public class InfoMemberGroup implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getViewPerm() == null) {
			setViewPerm(false);
		}
	}

	private Integer id;

	private Info info;
	private MemberGroup group;

	private Boolean viewPerm;

	@Id
	@Column(name = "f_infomgroup_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_info_membergroup", pkColumnValue = "cms_info_membergroup", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_info_membergroup")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_info_id", nullable = false)
	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
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
}
