package com.jspxcms.core.domain;

import static com.jspxcms.core.support.Constants.DYNAMIC_SUFFIX;
import static com.jspxcms.core.support.Constants.IDENTITY_COOKIE_NAME;
import static com.jspxcms.core.support.Constants.SITE_PREFIX;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;
import org.springframework.web.util.WebUtils;

import com.jspxcms.core.support.ForeContext;

/**
 * Site
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_site")
public class Site implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 模型类型
	 */
	public static final String MODEL_TYPE = "site";
	/**
	 * 正常状态
	 */
	public static final int NORMAL = 0;
	/**
	 * 禁用状态
	 */
	public static final int DISABELD = 1;
	/**
	 * 树编码长度
	 */
	public static final int TREE_NUMBER_LENGTH = 4;

	public static String getIdentityCookie(HttpServletRequest request,
			HttpServletResponse response) {
		String value;
		Cookie cookie = WebUtils.getCookie(request, IDENTITY_COOKIE_NAME);
		if (cookie != null && StringUtils.isNotBlank(cookie.getValue())) {
			value = cookie.getValue();
		} else {
			value = UUID.randomUUID().toString();
			value = StringUtils.remove(value, '-');
			cookie = new Cookie(IDENTITY_COOKIE_NAME, value);
			String ctx = request.getContextPath();
			if (StringUtils.isBlank(ctx)) {
				ctx = "/";
			}
			cookie.setPath(ctx);
			cookie.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(cookie);
		}
		return value;
	}

	@Transient
	public static String long2hex(long num) {
		BigInteger big = BigInteger.valueOf(num);
		String hex = big.toString(Character.MAX_RADIX);
		return StringUtils.leftPad(hex, TREE_NUMBER_LENGTH, '0');
	}

	@Transient
	public static long hex2long(String hex) {
		BigInteger big = new BigInteger(hex, Character.MAX_RADIX);
		return big.longValue();
	}

	@Transient
	public Boolean isNormal() {
		Integer status = getStatus();
		if (status == null) {
			return null;
		}
		return status == NORMAL;
	}

	@Transient
	public long getTreeMaxLong() {
		BigInteger big = new BigInteger(getTreeMax(), Character.MAX_RADIX);
		return big.longValue();
	}

	@Transient
	public String getDisplayName() {
		StringBuilder sb = new StringBuilder();
		Site bean = this;
		sb.append(bean.getName());
		bean = bean.getParent();
		while (bean != null) {
			sb.insert(0, " - ");
			sb.insert(0, bean.getName());
			bean = bean.getParent();
		}
		return sb.toString();
	}

	@Transient
	public void addChild(Site bean) {
		List<Site> set = getChildren();
		if (set == null) {
			set = new ArrayList<Site>();
			setChildren(set);
		}
		set.add(bean);
	}

	@Transient
	public String getUrl() {
		return getUrl(getWithDomain());
	}

	@Transient
	public String getUrlFull() {
		return getUrl(true);
	}

	@Transient
	public String getUrl(boolean isFull) {
		StringBuilder sb = new StringBuilder();
		if (isFull) {
			sb.append("//").append(getDomain());
			if (getPort() != null) {
				sb.append(":").append(getPort());
			}
		}
		if (StringUtils.isNotBlank(getContextPath())) {
			sb.append(getContextPath());
		}
		// 首页是否静态页在节点中设置，站点中无法展现判断是动态页还是静态页，只能给动态页地址。
		if (!getIdentifyDomain() && !getDef()) {
			sb.append(SITE_PREFIX).append(getNumber()).append(DYNAMIC_SUFFIX);
		}
		if (sb.length() == 0) {
			// 如果为根目录，要加上"/"
			sb.append("/");
		}
		return sb.toString();
	}

	@Transient
	public String getBase() {
		String ctx = getContextPath();
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(ctx)) {
			sb.append(ctx);
		}
		if (!getIdentifyDomain() && !getDef()) {
			sb.append(SITE_PREFIX).append(getNumber());
		}
		return sb.toString();
	}

	/**
	 * 获得当前模板主题的资源文件路径
	 * 
	 * 例如：/jspxcms/files/1/bluewise/_files
	 * 
	 * @param path
	 * @return
	 */
	@Transient
	public String getFilesPath() {
		return getFilesPath(null);
	}

	/**
	 * 获得当前模板主题的资源文件路径
	 * 
	 * 例如：/jspxcms/files/1/bluewise/_files
	 * 
	 * @param path
	 * @return
	 */
	@Transient
	public String getFilesPath(String path) {
		return getFilesPath(path, true);
	}

	/**
	 * 获得当前模板主题的资源文件路径
	 * 
	 * 例如：/jspxcms/files/1/bluewise/_files 或 /files/1/bluewise/_files
	 * 
	 * @param path
	 * @return
	 */
	@Transient
	public String getFilesPath(String path, boolean withContextPath) {
		StringBuilder sb = new StringBuilder();
		if (withContextPath && StringUtils.isNotBlank(getContextPath())) {
			sb.append(getContextPath());
		}
		sb.append(ForeContext.FILES_PATH);
		sb.append("/").append(getId());
		sb.append("/").append(getTemplateTheme());
		sb.append("/").append(ForeContext.FILES);
		if (StringUtils.isNotBlank(path)) {
			sb.append(path);
		}
		return sb.toString();
	}

	/**
	 * 获得模板路径
	 * 
	 * 例如：/1/bluewise/index.html
	 * 
	 * @param tpl
	 * @return
	 */
	@Transient
	public String getTemplate(String tpl) {
		StringBuilder sb = new StringBuilder();
		sb.append("/").append(getId());
		sb.append("/").append(getTemplateTheme());
		if (tpl != null) {
			if (!tpl.startsWith("/")) {
				sb.append("/");
			}
			sb.append(tpl);
		}
		return sb.toString();
	}

	/**
	 * 获得模板的基础路径
	 * 
	 * 例如：/files/1
	 * 
	 * @param path
	 * @return
	 */
	@Transient
	public String getFilesBasePath(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append(ForeContext.FILES_PATH);
		sb.append("/").append(getId());
		if (StringUtils.isNotBlank(path)) {
			if (!path.startsWith("/")) {
				sb.append("/");
			}
			sb.append(path);
		}
		return sb.toString();
	}

	@Transient
	public String getProtocol() {
		return getGlobal() != null ? getGlobal().getProtocol() : null;
	}

	@Transient
	public Integer getPort() {
		return getGlobal() != null ? getGlobal().getPort() : null;
	}

	@Transient
	public String getContextPath() {
		return getGlobal() != null ? getGlobal().getContextPath() : null;
	}

	@Transient
	public String getVersion() {
		return getGlobal() != null ? getGlobal().getVersion() : null;
	}

	@Transient
	public Boolean getWithDomain() {
		return getGlobal() != null ? getGlobal().getWithDomain() : null;
	}

	@Transient
	public String getNoPictureUrl() {
		return getFilesPath(getNoPicture());
	}

	@Transient
	public String getFullNameOrName() {
		String fullName = getFullName();
		return StringUtils.isBlank(fullName) ? getName() : fullName;
	}

	@Transient
	public SiteWatermark getWatermark() {
		return new SiteWatermark(this);
	}

	@Transient
	public Object getConf(String className) {
		try {
			return Class.forName(className).getConstructor(Site.class)
					.newInstance(this);
		} catch (Exception e) {
			throw new IllegalArgumentException("Class '" + className
					+ "' is not Conf Class", e);
		}
	}

	@Transient
	public <T> T getConf(Class<T> clazz) {
		try {
			return clazz.getConstructor(Site.class).newInstance(this);
		} catch (Exception e) {
			throw new IllegalArgumentException("Class '" + clazz.getName()
					+ "' is not Conf Class", e);
		}
	}

	@Transient
	public void applyDefaultValue() {
		if (getTemplateTheme() == null) {
			setTemplateTheme("default");
		}
		if (getNoPicture() == null) {
			setNoPicture("/img/no_picture.jpg");
		}
		if (getStatus() == null) {
			setStatus(NORMAL);
		}
	}

	private Integer id;
	private List<Site> children = new ArrayList<Site>(0);
	private List<Role> roles = new ArrayList<Role>(0);
	private Map<String, String> customs = new HashMap<String, String>(0);
	private Map<String, String> clobs = new HashMap<String, String>(0);

	private Global global;
	private Org org;
	private Site parent;

	private String name;
	private String number;
	private String fullName;
	private String htmlPath;
	private String domain;
	private String templateTheme;
	private String noPicture;
	private Boolean urlRewrite;
	private Boolean identifyDomain;
	private Boolean def;
	private String treeNumber;
	private Integer treeLevel;
	private String treeMax;
	private Integer status;

	@Id
	@Column(name = "f_site_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_site", pkColumnValue = "cms_site", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_site")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	@OrderBy(value = "treeNumber asc, id asc")
	public List<Site> getChildren() {
		return children;
	}

	public void setChildren(List<Site> children) {
		this.children = children;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "site")
	@OrderBy(value = "seq asc, id asc")
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@ElementCollection
	@CollectionTable(name = "cms_site_custom", joinColumns = @JoinColumn(name = "f_site_id"))
	@MapKeyColumn(name = "f_key", length = 50)
	@Column(name = "f_value", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	@ElementCollection
	@CollectionTable(name = "cms_site_clob", joinColumns = @JoinColumn(name = "f_site_id"))
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_parent_id")
	public Site getParent() {
		return parent;
	}

	public void setParent(Site parent) {
		this.parent = parent;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_global_id", nullable = false)
	public Global getGlobal() {
		return this.global;
	}

	public void setGlobal(Global global) {
		this.global = global;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_org_id", nullable = false)
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	@Column(name = "f_name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "f_number", nullable = false, unique = true, length = 100)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "f_full_name", length = 100)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "f_html_path", length = 100)
	public String getHtmlPath() {
		return this.htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
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

	@Column(name = "f_no_picture", nullable = true)
	public String getNoPicture() {
		return noPicture;
	}

	public void setNoPicture(String noPicture) {
		this.noPicture = noPicture;
	}

	@Column(name = "f_is_url_rewrite", nullable = false, length = 1)
	public Boolean getUrlRewrite() {
		return urlRewrite;
	}

	public void setUrlRewrite(Boolean urlRewrite) {
		this.urlRewrite = urlRewrite;
	}

	@Column(name = "f_is_identify_domain", nullable = false, length = 1)
	public Boolean getIdentifyDomain() {
		return identifyDomain;
	}

	public void setIdentifyDomain(Boolean identifyDomain) {
		this.identifyDomain = identifyDomain;
	}

	@Column(name = "f_is_def", nullable = false, length = 1)
	public Boolean getDef() {
		return def;
	}

	public void setDef(Boolean def) {
		this.def = def;
	}

	@Column(name = "f_tree_number", nullable = false, length = 100)
	public String getTreeNumber() {
		return this.treeNumber;
	}

	public void setTreeNumber(String treeNumber) {
		this.treeNumber = treeNumber;
	}

	@Column(name = "f_tree_level", nullable = false)
	public Integer getTreeLevel() {
		return this.treeLevel;
	}

	public void setTreeLevel(Integer treeLevel) {
		this.treeLevel = treeLevel;
	}

	@Column(name = "f_tree_max", nullable = false, length = 20)
	public String getTreeMax() {
		return this.treeMax;
	}

	public void setTreeMax(String treeMax) {
		this.treeMax = treeMax;
	}

	@Column(name = "f_status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
