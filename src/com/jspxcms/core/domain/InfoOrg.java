package com.jspxcms.core.domain;

import java.util.Comparator;

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
@Table(name = "cms_info_org")
public class InfoOrg implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getViewPerm() == null) {
			setViewPerm(false);
		}
	}

	private Integer id;

	private Info info;
	private Org org;

	private Boolean viewPerm;

	@Id
	@Column(name = "f_infoorg_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_info_org", pkColumnValue = "cms_info_org", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_info_org")
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
	@JoinColumn(name = "f_org_id", nullable = false)
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	@Column(name = "f_is_view_perm", nullable = false, length = 1)
	public Boolean getViewPerm() {
		return this.viewPerm;
	}

	public void setViewPerm(Boolean viewPerm) {
		this.viewPerm = viewPerm;
	}

	public static class InfoOrgComparator implements Comparator<InfoOrg> {
		public int compare(InfoOrg o1, InfoOrg o2) {
			return o1.getOrg().getTreeNumber()
					.compareTo(o2.getOrg().getTreeNumber());
		}
	}
}
