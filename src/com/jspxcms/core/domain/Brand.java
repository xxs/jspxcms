package com.jspxcms.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.jspxcms.core.domain.Site;

/**
 * Brand
 * 
 * @author xxs
 * 
 */
@Entity
@Table(name = "cms_brand")
public class Brand implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 已审核
	 */
	public static final int AUDITED = 0;
	/**
	 * 未审核
	 */
	public static final int SAVED = 1;
	
	
	@Transient
	public Set<Brand> getNodePerms() {
		Set<NodeBrand> groups = getNodeBrands();
		Set<Brand> brands = new HashSet<Brand>();
		for (NodeBrand nr : groups) {
			brands.add(nr.getBrand());
		}
		return brands;
	}
	
	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
		if(getStatus()==null){
			setStatus(AUDITED);
		}
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
	public Set<NodeBrand> getNodeBrands() {
		return nodeBrands;
	}
	
	public void setNodeBrands(Set<NodeBrand> nodeBrands) {
		this.nodeBrands = nodeBrands;
	}
	
	private Integer id;
	private Site site;
	
	private String name;
	private String url;
	private Integer seq;
	private String logo;
	private String description;
	private Boolean recommend;
	private Integer status;
	private Boolean withLogo;
	
	/** 绑定分类 */
	private Set<NodeBrand> nodeBrands = new HashSet<NodeBrand>(0);

	@Id
	@Column(name = "f_brand_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_brand", pkColumnValue = "cms_brand", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_brand")	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_site_id",nullable =false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "f_name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "f_url", nullable = false)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "f_seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "f_logo")
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "f_description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "f_is_recommend", nullable = false, length = 1)
	public Boolean getRecommend() {
		return this.recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}

	@Column(name = "f_status", nullable = false, length = 1)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "f_is_with_logo", nullable = false, length = 1)
	public Boolean getWithLogo() {
		return withLogo;
	}

	public void setWithLogo(Boolean withLogo) {
		this.withLogo = withLogo;
	}

}
