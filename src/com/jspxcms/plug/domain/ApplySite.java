package com.jspxcms.plug.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table(name = "plug_resume")
public class ApplySite implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public static final String APPLY = "A";
	public static final String SUCCESS = "S";
	public static final String FAIL = "F";
	@Transient
	public void applyDefaultValue() {
		if (getTemplateTheme() == null) {
			setTemplateTheme("default");
		}
		if (getStatus() == null) {
			setStatus(APPLY);
		}
	}

	private Integer id;

	private String name;
	private String fullName;
	private String domain;
	private String templateTheme;
	private String status;
	private String linkMan;
	private String linkPhone;

	@Id
	@Column(name = "f_applysite_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_plug_applysite", pkColumnValue = "plug_applysite", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_plug_applysite")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "f_name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "f_full_name", length = 100)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "f_domain", nullable = false, length = 100)
	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Column(name = "f_template_theme", nullable = false, length = 100)
	public String getTemplateTheme() {
		return this.templateTheme;
	}

	public void setTemplateTheme(String templateTheme) {
		this.templateTheme = templateTheme;
	}

	@Column(name = "f_status", nullable = false)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "f_link_man", nullable = false)
	public String getLinkMan() {
		return linkMan;
	}
	
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	@Column(name = "f_link_phone", nullable = false)
	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

}
