package com.jspxcms.core.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import com.jspxcms.core.support.Constants;

/**
 * Global
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_global")
public class Global implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 模型类型
	 */
	public static final String MODEL_TYPE = "global";

	@Transient
	public static void removeAttr(Map<String, String> map, String prefix) {
		Set<String> keysToRemove = new HashSet<String>();
		for (String key : map.keySet()) {
			if (key.startsWith(prefix)) {
				keysToRemove.add(key);
			}
		}
		for (String key : keysToRemove) {
			map.remove(key);
		}
	}

	@Transient
	public String getVersion() {
		return Constants.VERSION;
	}

	@Transient
	public Set<String> getValidDomains() {
		Set<String> domains = new HashSet<String>();
		List<Site> sites = getSites();
		for (Site site : sites) {
			domains.add(site.getDomain());
		}
		return domains;
	}

	@Transient
	public GlobalMail getMail() {
		return new GlobalMail(getCustoms());
	}

	@Transient
	public GlobalRegister getRegister() {
		return new GlobalRegister(getCustoms());
	}

	@Transient
	public GlobalUpload getUpload() {
		return new GlobalUpload(getCustoms());
	}

	@Transient
	public Object getConf(String className) {
		try {
			return Class.forName(className).getConstructor(Map.class)
					.newInstance(getCustoms());
		} catch (Exception e) {
			throw new IllegalArgumentException("Class '" + className
					+ "' is not Conf Class", e);
		}
	}

	private Integer id;
	private List<Site> sites = new ArrayList<Site>(0);
	private Map<String, String> customs = new HashMap<String, String>(0);
	private Map<String, String> clobs = new HashMap<String, String>(0);

	private String protocol;
	private Integer port;
	private String contextPath;
	private Boolean withDomain;
	private Boolean allStatic;
	private String dataVersion;

	@Id
	@Column(name = "f_global_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "global")
	@OrderBy("treeNumber asc")
	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	@ElementCollection
	@CollectionTable(name = "cms_global_custom", joinColumns = @JoinColumn(name = "f_global_id"))
	@MapKeyColumn(name = "f_key", length = 50)
	@Column(name = "f_value", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	@ElementCollection
	@CollectionTable(name = "cms_global_clob", joinColumns = @JoinColumn(name = "f_global_id"))
	@MapKeyColumn(name = "f_key", length = 50)
	@MapKeyType(value = @Type(type = "string"))
	@Lob
	@Column(name = "f_value", nullable = false)
	public Map<String, String> getClobs() {
		return this.clobs;
	}

	public void setClobs(Map<String, String> clobs) {
		this.clobs = clobs;
	}

	@Length(max = 50)
	@Column(name = "f_protocol", length = 50)
	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Column(name = "f_port")
	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Length(max = 255)
	@Column(name = "f_context_path", length = 255)
	public String getContextPath() {
		return this.contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Column(name = "f_is_with_domain", nullable = false, length = 1)
	public Boolean getWithDomain() {
		return this.withDomain;
	}
	
	public void setWithDomain(Boolean withDomain) {
		this.withDomain = withDomain;
	}
	
	@Column(name = "f_is_all_static", nullable = false, length = 1)
	public Boolean getAllStatic() {
		return this.allStatic;
	}

	public void setAllStatic(Boolean allStatic) {
		this.allStatic = allStatic;
	}

	@Length(max = 50)
	@Column(name = "f_version", nullable = false, length = 50)
	public String getDataVersion() {
		return this.dataVersion;
	}

	public void setDataVersion(String dataVersion) {
		this.dataVersion = dataVersion;
	}
}
