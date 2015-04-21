package com.jspxcms.ext.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

import org.apache.commons.lang3.StringUtils;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;

@Entity
@Table(name = "cms_collect")
public class Collect implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 就绪
	 */
	public static final int READY = 0;
	/**
	 * 运行中
	 */
	public static final int RUNNING = 1;
	/**
	 * 暂停
	 */
	// TODO 考虑去掉
	public static final int PAUSE = 2;

	public static final String PLACEHOLDER = "(*)";

	@Transient
	public boolean isRunning() {
		return getStatus() == RUNNING;
	}

	@Transient
	public boolean isReady() {
		return getStatus() == READY;
	}

	@Transient
	public static String getContent(String html, String code) {
		code = StringUtils.remove(code, '\r');
		code = StringUtils.remove(code, '\n');
		int index = code.indexOf(PLACEHOLDER);
		if (index == -1) {
			return null;
		}
		String beginCode = code.substring(0, index);
		String endCode = code.substring(index + PLACEHOLDER.length());
		int beginIndex = html.indexOf(beginCode);
		if (beginIndex == -1) {
			return null;
		}
		beginIndex += beginCode.length();
		int endIndex = html.indexOf(endCode, beginIndex);
		if (endIndex == -1) {
			return null;
		}
		String areaHtml = html.substring(beginIndex, endIndex);
		return areaHtml;
	}

	@Transient
	public List<String> getUrls() {
		List<String> urls = new ArrayList<String>();
		String[] lines = StringUtils.split(getUrl(), "\r\n");
		for (String line : lines) {
			if (line.indexOf(PLACEHOLDER) != -1) {
				for (int i = getBegin(), len = getEnd(); i <= len; i++) {
					urls.add(StringUtils.replace(line, PLACEHOLDER,
							String.valueOf(i)));
				}
			} else {
				urls.add(line);
			}
		}
		if (getDesc()) {
			Collections.reverse(urls);
		}
		return urls;
	}

	@Transient
	public List<String> getItemUrls(String html) {
		String itemUrl = getItemUrl();
		itemUrl = StringUtils.remove(itemUrl, '\r');
		itemUrl = StringUtils.remove(itemUrl, '\n');
		int index = itemUrl.indexOf(PLACEHOLDER);
		if (index == -1) {
			return null;
		}
		String beginCode = itemUrl.substring(0, index);
		String endCode = itemUrl.substring(index + PLACEHOLDER.length());
		List<String> urls = new ArrayList<String>();
		int beginIndex = 0, endIndex = 0;
		int beginLength = beginCode.length();
		int endLength = endCode.length();
		int htmlLength = html.length();
		while (beginIndex < htmlLength) {
			beginIndex = html.indexOf(beginCode, beginIndex);
			if (beginIndex == -1) {
				break;
			}
			beginIndex += beginLength;
			endIndex = html.indexOf(endCode, beginIndex);
			if (endIndex == -1) {
				return null;
			}
			urls.add(html.substring(beginIndex, endIndex));
			beginIndex = endIndex + endLength;
		}
		if (getDesc()) {
			Collections.reverse(urls);
		}
		return urls;
	}

	@Transient
	public String getItemAreaHtml(String html) {
		return getContent(html, getItemArea());
	}

	@Transient
	public String getTitleHtml(String html) {
		return getContent(html, getTitle());
	}

	@Transient
	public String getTextHtml(String html) {
		return getContent(html, getText());
	}

	@Transient
	public void applyDefaultValue() {
		if (getBegin() == null) {
			setBegin(2);
		}
		if (getEnd() == null) {
			setEnd(10);
		}
		if (getDesc() == null) {
			setDesc(true);
		}
		if (getStatus() == null) {
			setStatus(READY);
		}
	}

	private Integer id;

	private Site site;
	private User user;
	private Node node;

	private String name;
	private String charset;
	private String url;
	private Integer begin;
	private Integer end;
	private Boolean desc;
	private String itemArea;
	private String itemUrl;
	private String title;
	private String text;
	private Integer status;

	@Id
	@Column(name = "f_collect_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_collect", pkColumnValue = "cms_collect", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_collect")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_node_id", nullable = false)
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
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
	@JoinColumn(name = "f_user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "f_name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "f_charset", nullable = false, length = 100)
	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Column(name = "f_url", nullable = false, length = 2000)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "f_begin", nullable = false)
	public Integer getBegin() {
		return this.begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	@Column(name = "f_end", nullable = false)
	public Integer getEnd() {
		return this.end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	@Column(name = "f_is_desc", nullable = false, length = 1)
	public Boolean getDesc() {
		return this.desc;
	}

	public void setDesc(Boolean desc) {
		this.desc = desc;
	}

	@Column(name = "f_item_area")
	public String getItemArea() {
		return this.itemArea;
	}

	public void setItemArea(String itemArea) {
		this.itemArea = itemArea;
	}

	@Column(name = "f_item_url")
	public String getItemUrl() {
		return this.itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	@Column(name = "f_title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "f_text")
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "f_status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
