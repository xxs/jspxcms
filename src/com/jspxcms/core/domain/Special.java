package com.jspxcms.core.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.jspxcms.common.web.Anchor;
import com.jspxcms.common.web.ImageAnchor;
import com.jspxcms.common.web.ImageAnchorBean;
import com.jspxcms.core.support.Siteable;

/**
 * Special
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_special")
public class Special implements java.io.Serializable, Anchor, Siteable {
	private static final long serialVersionUID = 1L;

	@Transient
	public String getUrl() {
		// TODO
		return null;
	}

	@Transient
	public Boolean getNewWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transient
	public String getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transient
	public Boolean getStrong() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transient
	public Boolean getEm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transient
	public String getKeywords() {
		String keywords = getMetaKeywords();
		if (StringUtils.isBlank(keywords)) {
			return getTitle();
		} else {
			return keywords;
		}
	}

	@Transient
	public String getDescription() {
		String description = getMetaDescription();
		if (StringUtils.isBlank(description)) {
			return getTitle();
		} else {
			return description;
		}
	}

	@Transient
	public String getSmallImageUrl() {
		String url = getSmallImage();
		return StringUtils.isBlank(url) ? getSite().getNoPictureUrl() : url;
	}

	@Transient
	public ImageAnchor getSmallImageBean() {
		ImageAnchorBean bean = new ImageAnchorBean();
		bean.setTitle(getTitle());
		bean.setUrl(getUrl());
		bean.setSrc(getSmallImageUrl());
		return bean;
	}

	@Transient
	public String getLargeImageUrl() {
		String url = getLargeImage();
		return StringUtils.isBlank(url) ? getSite().getNoPictureUrl() : url;
	}

	@Transient
	public ImageAnchor getLargeImageBean() {
		ImageAnchorBean bean = new ImageAnchorBean();
		bean.setTitle(getTitle());
		bean.setUrl(getUrl());
		bean.setSrc(getLargeImageUrl());
		return bean;
	}

	@Transient
	public void applyDefaultValue() {
		setWithImage(StringUtils.isNotBlank(getSmallImage()));
		if (getCreationDate() == null) {
			setCreationDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getRefers() == null) {
			setRefers(0);
		}
		if (getViews() == null) {
			setViews(0);
		}
		if (getRecommend() == null) {
			setRecommend(false);
		}
	}

	private Integer id;
	private Map<String, String> customs = new HashMap<String, String>(0);

	private SpecialCategory category;
	private Site site;
	private User creator;

	private Date creationDate;
	private String title;
	private String metaKeywords;
	private String metaDescription;
	private String smallImage;
	private String largeImage;
	private String video;
	private Integer refers;
	private Integer views;
	private Boolean withImage;
	private Boolean recommend;

	public Special() {
	}

	public Special(String title, SpecialCategory category, Site site) {
		this.title = title;
		this.category = category;
		this.site = site;
	}

	@Id
	@Column(name = "f_special_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_special", pkColumnValue = "cms_special", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_special")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ElementCollection
	@CollectionTable(name = "cms_special_custom", joinColumns = @JoinColumn(name = "f_special_id"))
	@MapKeyColumn(name = "f_key", length = 50)
	@Column(name = "f_value", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_speccate_id", nullable = false)
	public SpecialCategory getCategory() {
		return this.category;
	}

	public void setCategory(SpecialCategory category) {
		this.category = category;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_site_id", nullable = false)
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_creator_id", nullable = false)
	public User getCreator() {
		return this.creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_creation_date", nullable = false, length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "f_title", nullable = false, length = 150)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "f_meta_keywords", length = 150)
	public String getMetaKeywords() {
		return this.metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(name = "f_meta_description")
	public String getMetaDescription() {
		return this.metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@Column(name = "f_small_image")
	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	@Column(name = "f_large_image")
	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	@Column(name = "f_video")
	public String getVideo() {
		return this.video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Column(name = "f_refers", nullable = false)
	public Integer getRefers() {
		return refers;
	}

	public void setRefers(Integer refers) {
		this.refers = refers;
	}

	@Column(name = "f_views", nullable = false)
	public Integer getViews() {
		return this.views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	@Column(name = "f_is_with_image", nullable = false, length = 1)
	public Boolean getWithImage() {
		return this.withImage;
	}

	public void setWithImage(Boolean withImage) {
		this.withImage = withImage;
	}

	@Column(name = "f_is_recommend", nullable = false, length = 1)
	public Boolean getRecommend() {
		return this.recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}
}
