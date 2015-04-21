package com.jspxcms.ext.domain;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.jspxcms.core.domain.Site;

@Entity
@Table(name = "cms_visit_log")
public class VisitLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final String format(Date date) {
		return DATE_FORMAT.format(date);
	}

	@Transient
	public void applyDefaultValue() {
		if (getTime() == null) {
			setTime(new Timestamp(System.currentTimeMillis()));
		}
		if (getDate() == null) {
			setDate(format(getTime()));
		}
		if (getIp() != null) {
			setIpDate(getIp() + getDate());
		}
		if (getCookie() != null) {
			setCookieDate(getCookie() + getDate());
		}
	}

	private Integer id;

	private Site site;

	private String url;
	private String referrer;
	private String ip;
	private String ipDate;
	private String cookie;
	private String cookieDate;
	private String date;
	private Date time;

	@Id
	@Column(name = "f_visitlog_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_visit_log", pkColumnValue = "cms_visit_log", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_visit_log")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_site_id", nullable = false)
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "f_url", nullable = false, length = 255)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "f_referrer", length = 255)
	public String getReferrer() {
		return this.referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	@Column(name = "f_ip", length = 100)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "f_ip_date", length = 100)
	public String getIpDate() {
		return ipDate;
	}

	public void setIpDate(String ipDate) {
		this.ipDate = ipDate;
	}

	@Column(name = "f_cookie", length = 100)
	public String getCookie() {
		return this.cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	@Column(name = "f_cookie_date", length = 100)
	public String getCookieDate() {
		return cookieDate;
	}

	public void setCookieDate(String cookieDate) {
		this.cookieDate = cookieDate;
	}

	@Column(name = "f_date", length = 10)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_time", nullable = false, length = 19)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
