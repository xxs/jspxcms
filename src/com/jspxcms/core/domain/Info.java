package com.jspxcms.core.domain;

import static com.jspxcms.core.support.Constants.DYNAMIC_SUFFIX;
import static com.jspxcms.core.support.Constants.SITE_PREFIX;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;

import com.jspxcms.common.util.Files;
import com.jspxcms.common.util.Reflections;
import com.jspxcms.common.util.Strings;
import com.jspxcms.common.web.Anchor;
import com.jspxcms.common.web.ImageAnchor;
import com.jspxcms.common.web.ImageAnchorBean;
import com.jspxcms.common.web.PageUrlResolver;
import com.jspxcms.core.domain.InfoOrg.InfoOrgComparator;
import com.jspxcms.core.support.Commentable;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.Siteable;
import com.jspxcms.core.support.TitleText;

/**
 * Info
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_info")
public class Info implements java.io.Serializable, Anchor, Siteable,
		Commentable, PageUrlResolver {
	private static final long serialVersionUID = 1L;
	/**
	 * 评论类型
	 */
	public static final String COMMENT_TYPE = "Info";
	/**
	 * 评分标记
	 */
	public static final String SCORE_MARK = "InfoScore";
	/**
	 * Digg标记
	 */
	public static final String DIGG_MARK = "InfoDigg";
	/**
	 * 模型类型
	 */
	public static final String MODEL_TYPE = "info";
	/**
	 * 工作流类型
	 */
	public static final int WORKFLOW_TYPE = 1;
	/**
	 * 审核中
	 */
	public static final String AUDITING = "1";
	/**
	 * 正常
	 */
	public static final String NORMAL = "A";
	/**
	 * 草稿
	 */
	public static final String DRAFT = "B";
	/**
	 * 投稿
	 */
	public static final String CONTRIBUTION = "C";
	/**
	 * 退稿
	 */
	public static final String REJECTION = "D";
	/**
	 * 采集
	 */
	public static final String COLLECTED = "E";
	/**
	 * 删除
	 */
	public static final String DELETED = "X";
	/**
	 * 归档
	 */
	public static final String ARCHIVE = "Z";
	
	/**
	 * 商品属性值属性个数
	 */
	public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;
	
	/**
	 * 商品属性值属性名称前缀
	 */
	public static final String ATTR_VALUE_PROPERTY_NAME_PREFIX = "a";
	/**
	 * 替换标识:栏目ID
	 */
	public static String PATH_NODE_ID = "{node_id}";
	/**
	 * 替换标识:栏目编码
	 */
	public static String PATH_NODE_NUMBER = "{node_number}";
	/**
	 * 替换标识:内容ID
	 */
	public static String PATH_INFO_ID = "{info_id}";
	/**
	 * 替换标识:站点编码
	 */
	public static String PATH_SITE_NUMBER = "{site_number}";
	/**
	 * 替换标识:年
	 */
	public static String PATH_YEAR = "{year}";
	/**
	 * 替换标识:月
	 */
	public static String PATH_MONTH = "{month}";
	/**
	 * 替换标识:日
	 */
	public static String PATH_DAY = "{day}";

	/**
	 * 信息正文KEY
	 */
	public static String INFO_TEXT = "text";
	/**
	 * 分页标签打开
	 */
	public static String PAGEBREAK_OPEN = "<div>[PageBreak]";
	/**
	 * 分页标签关闭
	 */
	public static String PAGEBREAK_CLOSE = "[/PageBreak]</div>";

	/**
	 * 获取没有分页信息的正文
	 * 
	 * @return
	 */
	@Transient
	public static String getTextWithoutPageBreak(String text) {
		if (StringUtils.isBlank(text)) {
			return text;
		}
		StringBuilder sb = new StringBuilder();
		int start = 0, end;
		while (true) {
			end = text.indexOf(PAGEBREAK_OPEN, start);
			if (end != -1) {
				sb.append(text, start, end);
				start = end + PAGEBREAK_OPEN.length();
			} else {
				sb.append(text, start, text.length());
				break;
			}
			end = text.indexOf(PAGEBREAK_CLOSE, start);
			if (end != -1) {
				start = end + PAGEBREAK_CLOSE.length();
			} else {
				break;
			}
		}
		return sb.toString();
	}

	@Transient
	public static String getDescription(Map<String, String> clobs, String title) {
		String html = clobs != null ? clobs.get(INFO_TEXT) : null;
		String text = getTextWithoutPageBreak(html);
		String desciption = Strings.getTextFromHtml(text, 150);
		return StringUtils.isNotBlank(desciption) ? desciption : title;
	}

	/**
	 * 获得分页的InfoText(标题、正文)列表
	 * 
	 * @return
	 */
	@Transient
	public List<TitleText> getTextList() {
		List<TitleText> list = new ArrayList<TitleText>();
		String t = getText();
		String ftt = getFullTitleOrTitle();
		String title = ftt, text;
		if (t != null) {
			int start = 0, end;
			while (true) {
				end = t.indexOf(PAGEBREAK_OPEN, start);
				if (end != -1) {
					text = t.substring(start, end);
					start = end + PAGEBREAK_OPEN.length();
				} else {
					text = t.substring(start);
				}
				list.add(new TitleText(title, text));
				if (end == -1) {
					break;
				}
				end = t.indexOf(PAGEBREAK_CLOSE, start);
				if (end != -1) {
					title = t.substring(start, end);
					if (StringUtils.isBlank(title)) {
						title = ftt;
					}
					start = end + PAGEBREAK_CLOSE.length();
				} else {
					title = t.substring(start);
					if (StringUtils.isBlank(title)) {
						title = ftt;
					}
					break;
				}
			}
		} else {
			list.add(new TitleText(title, ""));
		}
		return list;
	}

	/**
	 * 获取没有分页信息的正文
	 * 
	 * @return
	 */
	@Transient
	public String getTextWithoutPageBreak() {
		String text = getText();
		return getTextWithoutPageBreak(text);
	}

	/**
	 * 获取没有html的正文
	 * 
	 * @return
	 */
	@Transient
	public String getPlainText() {
		String text = getTextWithoutPageBreak();
		return Strings.getTextFromHtml(text);
	}

	@Transient
	public String getDescription() {
		String desciption = getMetaDescription();
		if (StringUtils.isBlank(desciption)) {
			String text = getTextWithoutPageBreak();
			desciption = Strings.getTextFromHtml(text, 150);
			return desciption != null ? desciption : getTitle();
		} else {
			return desciption;
		}
	}

	@Transient
	public boolean isNormal() {
		return getStatus() != null ? getStatus().equals(NORMAL) : false;
	}

	@Transient
	public boolean isDraft() {
		return getStatus() != null ? getStatus().equals(DRAFT) : false;
	}

	@Transient
	public boolean isAuditing() {
		return getStatus() != null ? getStatus().equals(AUDITING) : false;
	}

	@Transient
	public boolean isContribute() {
		return getStatus() != null ? getStatus().equals(CONTRIBUTION) : false;
	}

	@Transient
	public boolean isCollected() {
		return getStatus() != null ? getStatus().equals(COLLECTED) : false;
	}

	@Transient
	public boolean isRejection() {
		return getStatus() != null ? getStatus().equals(REJECTION) : false;
	}

	@Transient
	public boolean isArchive() {
		return getStatus() != null ? getStatus().equals(ARCHIVE) : false;
	}

	@Transient
	public boolean isDeleted() {
		return getStatus() != null ? getStatus().equals(DELETED) : false;
	}

	@Transient
	public boolean isDataPerm(User user) {
		Integer siteId = getSite().getId();
		Node node = getNode();
		if (user.getAllInfoPerm(siteId)
				|| Reflections.contains(user.getInfoPerms(siteId),
						node.getId(), "id")) {
			Integer permType = user.getInfoPermType(siteId);
			if (permType == Role.INFO_PERM_SELF) {
				Integer creatorId = getCreator().getId();
				Integer userId = user.getId();
				if (creatorId.equals(userId)) {
					return true;
				}
			} else if (permType == Role.INFO_PERM_ORG) {
				Integer orgId = getOrg().getId();
				if (user.getOrg().getId().equals(orgId)) {
					return true;
				}
			} else if (permType == Role.INFO_PERM_ALL) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断已终审、审核中的信息是审核权限
	 * 
	 * @param user
	 * @return
	 */
	@Transient
	public boolean isAuditPerm(User user) {
		if (!isNormal() && !isAuditing()) {
			// 状态不属于已终审、审核中的，都有权限
			return true;
		}
		if (user.getInfoFinalPerm(getSite().getId())) {
			// 拥有终审权
			return true;
		}
		Workflow workflow = getNode().getWorkflow();
		WorkflowProcess process = getProcess();
		if (process != null && !process.getEnd()) {
			// 如果流程存在且未结束，以流程的工作流为准。
			workflow = process.getWorkflow();
		}
		if (workflow == null) {
			// 工作流不存在，有权限。
			return true;
		}
		List<WorkflowStep> steps = workflow.getSteps();
		int size = steps.size();
		if (size == 0) {
			// 流程步骤为空，有权限。
			return true;
		}
		Collection<Role> userRoles = user.getRoles();
		Collection<Role> stepRoles;
		WorkflowStep step;
		if (isNormal()) {
			step = steps.get(size - 1);
			stepRoles = step.getRoles();
			return Reflections.containsAny(stepRoles, userRoles, "id");
		} else if (isAuditing()) {
			Integer currStepId = null;
			if (process != null && !process.getEnd()
					&& process.getStep() != null) {
				currStepId = process.getStep().getId();
			}
			for (int i = size - 1; i >= 0; i--) {
				step = steps.get(size - 1);
				stepRoles = step.getRoles();
				if (Reflections.containsAny(stepRoles, userRoles, "id")) {
					return true;
				}
				if (step.getId().equals(currStepId)) {
					return false;
				}
			}
			return false;
		} else {
			// never
			throw new IllegalStateException("not normal or auditing!");
		}
	}

	/**
	 * 判断已终审、审核中的信息是审核权限。从线程变量中获取user
	 * 
	 * @return
	 */
	@Transient
	public boolean isAuditPerm() {
		User user = Context.getCurrentUser();
		if (user != null) {
			return isAuditPerm(user);
		} else {
			return false;
		}
	}

	@Transient
	public boolean isViewPerm(Collection<MemberGroup> groups,
			Collection<Org> orgs) {
		if (getNode().isViewPerm(groups, orgs)) {
			return true;
		}
		if (Reflections.containsAny(getViewGroups(), groups, "id")) {
			return true;
		}
		if (CollectionUtils.isNotEmpty(orgs)
				&& Reflections.containsAny(getViewOrgs(), orgs, "id")) {
			return true;
		}
		return false;
	}

	/**
	 * 获得标题列表
	 * 
	 * @return
	 */
	@Transient
	public List<String> getTitleList() {
		List<TitleText> textList = getTextList();
		List<String> titleList = new ArrayList<String>(textList.size());
		for (TitleText it : textList) {
			titleList.add(it.getTitle());
		}
		return titleList;
	}

	@Transient
	public String getUrl() {
		return getUrl(1);
	}

	@Transient
	public String getUrl(Integer page) {
		return getGenerate() ? getUrlStatic(page) : getUrlDynamic(page);
	}

	@Transient
	public String getUrlFull() {
		return getUrlFull(1);
	}

	@Transient
	public String getUrlFull(Integer page) {
		return getGenerate() ? getUrlStaticFull(page) : getUrlDynamicFull(page);
	}

	@Transient
	public String getUrlDynamic() {
		return getUrlDynamic(1);
	}

	@Transient
	public String getUrlDynamic(Integer page) {
		boolean isFull = getSite().getWithDomain();
		return getUrlDynamic(page, isFull);
	}

	@Transient
	public String getUrlDynamicFull() {
		return getUrlDynamicFull(1);
	}

	@Transient
	public String getUrlDynamicFull(Integer page) {
		return getUrlDynamic(page, true);
	}

	@Transient
	public String getUrlDynamic(Integer page, boolean isFull) {
		if (isLinked()) {
			return getLinkUrl();
		}
		Site site = getSite();
		StringBuilder sb = new StringBuilder();
		String domainByNode = getNode().getDomainByNode();
		if (isFull) {
			sb.append("http://");
			if (StringUtils.isNotBlank(domainByNode)) {
				sb.append(domainByNode);
			} else {
				sb.append(site.getDomain());
			}
			if (site.getPort() != null) {
				sb.append(":").append(site.getPort());
			}
		}
		String ctx = site.getContextPath();
		if (StringUtils.isNotBlank(ctx)) {
			sb.append(ctx);
		}
		boolean sitePrefix = StringUtils.isBlank(domainByNode)
				&& !site.getIdentifyDomain() && !site.getDef();
		if (sitePrefix) {
			sb.append(SITE_PREFIX).append(site.getNumber());
		}
		sb.append("/").append(Constants.INFO_PATH);
		sb.append("/").append(getId());
		if (page != null && page > 1) {
			sb.append("_").append(page);
		}
		sb.append(DYNAMIC_SUFFIX);
		return sb.toString();
	}

	@Transient
	public String getUrlStatic() {
		return getUrlStatic(1);
	}

	@Transient
	public String getUrlStatic(Integer page) {
		boolean isFull = getSite().getWithDomain();
		boolean isAllStatic = getSite().getAllStatic();
		return getUrlStatic(page, isFull, isAllStatic, false);
	}

	@Transient
	public String getUrlStaticFull() {
		return getUrlStaticFull(1);
	}

	@Transient
	public String getUrlStaticFull(Integer page) {
		return getUrlStatic(page, true, false, false);
	}

	@Transient
	public String getUrlStatic(Integer page, boolean isFull,
			boolean isAllStatic, boolean forRealPath) {
		// Site site = getSite();
		if (isLinked()) {
			return getLinkUrl();
		}
		Node node = getNode();
		String path = node.getInfoPathOrDef();
		Calendar now = Calendar.getInstance();
		now.setTime(getPublishDate());
		String year = String.valueOf(now.get(Calendar.YEAR));
		int m = now.get(Calendar.MONTH) + 1;
		int d = now.get(Calendar.DAY_OF_MONTH);
		String month = String.valueOf(m);
		String day = String.valueOf(d);
		if (m < 10) {
			month = "0" + month;
		}
		if (d < 10) {
			day = "0" + day;
		}
		path = StringUtils.replace(path, PATH_NODE_ID, node.getId().toString());
		String nodeNumber = node.getNumber();
		if (StringUtils.isBlank(nodeNumber)) {
			nodeNumber = node.getId().toString();
		}
		path = StringUtils.replace(path, PATH_NODE_NUMBER, nodeNumber);
		// 替换站点编码
		path = StringUtils.replace(path, PATH_SITE_NUMBER, site.getNumber());
		path = StringUtils.replace(path, PATH_INFO_ID, getId().toString());
		path = StringUtils.replace(path, PATH_YEAR, year);
		path = StringUtils.replace(path, PATH_MONTH, month);
		path = StringUtils.replace(path, PATH_DAY, day);
		if (page != null && page > 1) {
			path += "_" + page;
		}
		String extension = node.getInfoExtensionOrDef();
		if (StringUtils.isNotBlank(extension)) {
			path += extension;
		}

		//全站静态化path处理
		if (isAllStatic) {
			path = path.substring(path.lastIndexOf("/"),path.length());
			path = "."+path;
			return path;
		}
		StringBuilder sb = new StringBuilder();
		Site site = getSite();
		
		if (isFull && !forRealPath) {
			sb.append("http://");
			String domain = getNode().getDomainByNode();
			if (StringUtils.isBlank(domain)) {
				domain = site.getDomain();
			}
			sb.append(domain);
			if (site.getPort() != null) {
				sb.append(":").append(site.getPort());
			}
		}
		String ctx = site.getContextPath();
		String htmlPath = getNode().getDomainPathOrParent();
		boolean isPathInUrl = false;
		if (StringUtils.isBlank(htmlPath)) {
			htmlPath = site.getHtmlPath();
			isPathInUrl = !site.getUrlRewrite();
		}
		if (StringUtils.isNotBlank(htmlPath)
				&& (forRealPath || (!forRealPath && isPathInUrl))) {
			sb.append(htmlPath);
		}
		if (!forRealPath && StringUtils.isNotBlank(ctx)) {
			sb.append(ctx);
		}
		sb.append(path);
		return sb.toString();
	}

	@Transient
	public String getPageUrl(Integer page) {
		return getUrl(page);
	}

	@Transient
	public void addComments(int comments) {
		Set<InfoBuffer> buffers = getBuffers();
		if (buffers == null) {
			buffers = new HashSet<InfoBuffer>(1);
			setBuffers(buffers);
		}
		InfoBuffer buffer;
		if (buffers.isEmpty()) {
			buffer = new InfoBuffer();
			buffer.applyDefaultValue();
			buffer.setInfo(this);
			buffers.add(buffer);
		} else {
			buffer = buffers.iterator().next();
		}
		// 根据设置处理缓冲。一般网站评论数量不会很多，暂时不设置缓冲。
		// buffer.setComments(buffer.getComments() + comments);
		setComments(getComments() + comments);
	}

	/**
	 * 获得评论状态
	 */
	@Transient
	public int getCommentStatus(User user, Collection<MemberGroup> groups) {
		SiteComment conf = getSite().getConf(SiteComment.class);
		int mode = conf.getMode();
		if (mode == SiteComment.MODE_OFF || !isNormal() || isLinked()) {
			return SiteComment.STATUS_OFF;
		}
		Boolean allow = getAllowComment();
		if (allow != null && !allow) {
			return SiteComment.STATUS_OFF;
		}
		if (user == null && mode == SiteComment.MODE_USER) {
			return SiteComment.STATUS_LOGIN;
		}
		if (allow != null && allow) {
			return SiteComment.STATUS_ALLOWED;
		}
		Set<MemberGroup> commentGroups = getNode().getCommentGroups();
		if (Reflections.containsAny(commentGroups, groups, "id")) {
			return SiteComment.STATUS_ALLOWED;
		} else {
			if (user != null) {
				return SiteComment.STATUS_DENIED;
			} else {
				return SiteComment.STATUS_LOGIN;
			}
		}
	}

	@Transient
	public boolean allowComment(Collection<MemberGroup> groups) {
		// 非已审核状态或者是转向链接，不允许评论。
		if (!isNormal() || isLinked()) {
			return false;
		}
		Boolean allow = getAllowComment();
		// 信息单独设置是否可以评论
		if (allow != null) {
			return allow;
		}
		Set<MemberGroup> commentGroups = getNode().getCommentGroups();
		return Reflections.containsAny(commentGroups, groups, "id");
	}

	@Transient
	public Boolean getGenerate() {
		if (isLinked()) {
			// 转向链接不需要生成
			return false;
		}
		Node node = getNode();
		return node != null ? node.getGenerateInfoOrDef() : null;
	}

	@Transient
	public String getTemplate() {
		String infoTemplate = getInfoTemplate();
		if (infoTemplate != null) {
			infoTemplate = getSite().getTemplate(infoTemplate);
		} else {
			Node node = getNode();
			if (node != null) {
				infoTemplate = node.getInfoTemplateOrDef();
			}
		}
		return infoTemplate;
	}

	@Transient
	public String getTagKeywords() {
		List<InfoTag> infoTags = getInfoTags();
		if (infoTags != null) {
			StringBuilder keywordsBuff = new StringBuilder();
			for (InfoTag infoTag : infoTags) {
				keywordsBuff.append(infoTag.getTag().getName()).append(',');
			}
			if (keywordsBuff.length() > 0) {
				keywordsBuff.setLength(keywordsBuff.length() - 1);
			}
			return keywordsBuff.toString();
		} else {
			return null;
		}
	}

	@Transient
	public String getKeywords() {
		String keywords = getTagKeywords();
		if (StringUtils.isBlank(keywords)) {
			return getTitle();
		} else {
			return keywords;
		}
	}

	@Transient
	public List<Node> getNodes() {
		List<InfoNode> infoNodes = getInfoNodes();
		int len = CollectionUtils.size(infoNodes);
		List<Node> nodes = new ArrayList<Node>(len);
		if (len > 0) {
			for (InfoNode infoNode : infoNodes) {
				nodes.add(infoNode.getNode());
			}
		}
		return nodes;
	}

	@Transient
	public List<Tag> getTags() {
		List<InfoTag> infoTags = getInfoTags();
		if (infoTags == null) {
			return null;
		}
		List<Tag> tags = new ArrayList<Tag>(infoTags.size());
		for (InfoTag infoTag : infoTags) {
			tags.add(infoTag.getTag());
		}
		return tags;
	}

	@Transient
	public List<Special> getSpecials() {
		List<InfoSpecial> infoSpecials = getInfoSpecials();
		if (infoSpecials == null) {
			return null;
		}
		List<Special> specials = new ArrayList<Special>(infoSpecials.size());
		for (InfoSpecial infoSpecial : infoSpecials) {
			specials.add(infoSpecial.getSpecial());
		}
		return specials;
	}

	@Transient
	public Set<MemberGroup> getViewGroups() {
		Set<InfoMemberGroup> infoGroups = getInfoGroups();
		if (infoGroups == null) {
			return null;
		}
		Set<MemberGroup> groups = new HashSet<MemberGroup>();
		for (InfoMemberGroup ig : infoGroups) {
			if (ig.getViewPerm()) {
				groups.add(ig.getGroup());
			}
		}
		return groups;
	}

	@Transient
	public List<Org> getViewOrgs() {
		Set<InfoOrg> ios = getInfoOrgs();
		if (ios == null) {
			return null;
		}
		List<Org> orgs = new ArrayList<Org>(ios.size());
		for (InfoOrg io : ios) {
			if (io.getViewPerm()) {
				orgs.add(io.getOrg());
			}
		}
		return orgs;
	}

	@Transient
	public Boolean hasAttr(String attrNumber) {
		List<InfoAttribute> infoAttrs = getInfoAttrs();
		if (infoAttrs == null) {
			return null;
		}
		for (InfoAttribute ia : infoAttrs) {
			if (ia.getAttribute().getNumber().equals(attrNumber)) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public Attribute getAttr(String attrNumber) {
		List<InfoAttribute> infoAttrs = getInfoAttrs();
		if (infoAttrs == null) {
			return null;
		}
		for (InfoAttribute ia : infoAttrs) {
			if (ia.getAttribute().getNumber().equals(attrNumber)) {
				return ia.getAttribute();
			}
		}
		return null;
	}

	@Transient
	public List<Attribute> getAttrs() {
		List<InfoAttribute> infoAttrs = getInfoAttrs();
		if (infoAttrs == null) {
			return null;
		}
		List<Attribute> attrs = new ArrayList<Attribute>(infoAttrs.size());
		for (InfoAttribute infoAttr : infoAttrs) {
			attrs.add(infoAttr.getAttribute());
		}
		return attrs;
	}
	
	@Transient
	public List<Attr> getAttrList() {
		List<InfoAttr> infoAttrss = getInfoAttrList();
		if (infoAttrss == null) {
			return null;
		}
		List<Attr> attrss = new ArrayList<Attr>(infoAttrss.size());
		for (InfoAttr infoAttr : infoAttrss) {
			attrss.add(infoAttr.getAttr());
		}
		return attrss;
	}
	@Transient
	public List<Parameter> getParameterList() {
		List<InfoParameter> infoParameters = getInfoParameterList();
		if (infoParameters == null) {
			return null;
		}
		List<Parameter> ps = new ArrayList<Parameter>(infoParameters.size());
		for (InfoParameter infoParameter  : infoParameters) {
			ps.add(infoParameter.getParameter());
		}
		return ps;
	}
	
	@Transient
	public InfoAttribute getInfoAttr(Attribute attr) {
		Collection<InfoAttribute> infoAttrs = getInfoAttrs();
		if (!CollectionUtils.isEmpty(infoAttrs)) {
			for (InfoAttribute infoAttr : infoAttrs) {
				if (infoAttr.getAttribute().equals(attr)) {
					return infoAttr;
				}
			}
		}
		return null;
	}

	@Transient
	public InfoAttribute getInfoAttr(Integer attrId) {
		Collection<InfoAttribute> infoAttrs = getInfoAttrs();
		if (!CollectionUtils.isEmpty(infoAttrs)) {
			for (InfoAttribute infoAttr : infoAttrs) {
				if (infoAttr.getAttribute().getId().equals(attrId)) {
					return infoAttr;
				}
			}
		}
		return null;
	}

	@Transient
	public String getAttrImage(String attr) {
		Collection<InfoAttribute> infoAttrs = getInfoAttrs();
		for (InfoAttribute infoAttr : infoAttrs) {
			if (infoAttr.getAttribute().getNumber().equals(attr)) {
				return infoAttr.getImage();
			}
		}
		return null;
	}

	@Transient
	public String getAttrImageUrl(String attr) {
		String url = getAttrImage(attr);
		return StringUtils.isBlank(url) ? getSite().getNoPictureUrl() : url;
	}

	@Transient
	public String getAttrImage(Integer attrId) {
		Collection<InfoAttribute> infoAttrs = getInfoAttrs();
		Attribute attr;
		for (InfoAttribute infoAttr : infoAttrs) {
			attr = infoAttr.getAttribute();
			if ((attrId == null && attr.getWithImage())
					|| attr.getId().equals(attrId)) {
				return infoAttr.getImage();
			}
		}
		return null;
	}

	@Transient
	public String getAttrImageUrl(Integer attrId) {
		String url = getAttrImage(attrId);
		return StringUtils.isBlank(url) ? getSite().getNoPictureUrl() : url;
	}

	@Transient
	public String getAttrImage() {
		return getAttrImage(getAttrId());
	}

	@Transient
	public String getAttrImageUrl() {
		String url = getAttrImage();
		return StringUtils.isBlank(url) ? getSite().getNoPictureUrl() : url;
	}

	@Transient
	public ImageAnchor getAttrImageBean() {
		Integer attrId = getAttrId();
		InfoAttribute infoAttr = getInfoAttr(attrId);
		ImageAnchorBean bean = new ImageAnchorBean();
		bean.setTitle(getTitle());
		bean.setUrl(getUrl());
		bean.setSrc(getAttrImageUrl(attrId));
		if (infoAttr != null) {
			Attribute attr = infoAttr.getAttribute();
			bean.setWidth(attr.getImageWidth());
			bean.setHeight(attr.getImageHeight());
		}
		return bean;
	}

	@Transient
	public String getText() {
		Map<String, String> clobs = getClobs();
		return clobs != null ? clobs.get(INFO_TEXT) : null;
	}

	@Transient
	public void setText(String text) {
		Map<String, String> clobs = getClobs();
		if (clobs == null) {
			clobs = new HashMap<String, String>();
			setClobs(clobs);
		}
		clobs.put(INFO_TEXT, text);
	}

	@Transient
	public Model getModel() {
		return getNode() != null ? getNode().getInfoModel() : null;
	}

	@Transient
	public Workflow getWorkflow() {
		return getNode() != null ? getNode().getWorkflow() : null;
	}

	@Transient
	public String getTitle() {
		return getDetail() != null ? getDetail().getTitle() : null;
	}

	@Transient
	public String getSubtitle() {
		return getDetail() != null ? getDetail().getSubtitle() : null;
	}

	@Transient
	public String getFullTitle() {
		return getDetail() != null ? getDetail().getFullTitle() : null;
	}

	@Transient
	public String getFullTitleOrTitle() {
		String fullTitle = getFullTitle();
		return StringUtils.isNotBlank(fullTitle) ? fullTitle : getTitle();
	}

	@Transient
	public String getLink() {
		return getDetail() != null ? getDetail().getLink() : null;
	}

	@Transient
	public String getLinkUrl() {
		String link = getLink();
		if (StringUtils.isBlank(link)) {
			return link;
		}
		if (link.startsWith("/") && !link.startsWith("//")) {
			StringBuilder sb = new StringBuilder();
			Site site = getSite();
			if (site.getWithDomain()) {
				sb.append("//").append(site.getDomain());
				if (site.getPort() != null) {
					sb.append(":").append(site.getPort());
				}
			}
			String ctx = site.getContextPath();
			if (StringUtils.isNotBlank(ctx)) {
				sb.append(ctx);
			}
			sb.append(link);
			link = sb.toString();
		}
		return link;
	}

	@Transient
	public boolean isLinked() {
		return StringUtils.isNotBlank(getLink());
	}

	@Transient
	public Boolean getNewWindow() {
		return getDetail() != null ? getDetail().getNewWindow() : null;
	}

	@Transient
	public String getColor() {
		return getDetail() != null ? getDetail().getColor() : null;
	}

	@Transient
	public Boolean getStrong() {
		return getDetail() != null ? getDetail().getStrong() : null;
	}

	@Transient
	public Boolean getEm() {
		return getDetail() != null ? getDetail().getEm() : null;
	}

	@Transient
	public String getMetaDescription() {
		return getDetail() != null ? getDetail().getMetaDescription() : null;
	}

	@Transient
	public String getInfoPath() {
		return getDetail() != null ? getDetail().getInfoPath() : null;
	}

	@Transient
	public String getInfoTemplate() {
		return getDetail() != null ? getDetail().getInfoTemplate() : null;
	}

	/**
	 * 来源
	 * 
	 * @return
	 */
	@Transient
	public String getSource() {
		return getDetail() != null ? getDetail().getSource() : null;
	}

	/**
	 * 来源url
	 * 
	 * @return
	 */
	@Transient
	public String getSourceUrl() {
		return getDetail() != null ? getDetail().getSourceUrl() : null;
	}

	/**
	 * 作者
	 * 
	 * @return
	 */
	@Transient
	public String getAuthor() {
		return getDetail() != null ? getDetail().getAuthor() : null;
	}

	@Transient
	public String getSmallImage() {
		return getDetail() != null ? getDetail().getSmallImage() : null;
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
		Model model = getModel();
		if (model == null) {
			return bean;
		}
		ModelField mf = model.getField("smallImage");
		if (mf == null) {
			return bean;
		}
		Map<String, String> map = mf.getCustoms();
		String width = map.get(ModelField.IMAGE_WIDTH);
		if (StringUtils.isNotBlank(width)) {
			bean.setWidth(NumberUtils.createInteger(width));
		}
		String height = map.get(ModelField.IMAGE_HEIGHT);
		if (StringUtils.isNotBlank(height)) {
			bean.setHeight(NumberUtils.createInteger(height));
		}
		return bean;
	}

	@Transient
	public String getLargeImage() {
		return getDetail() != null ? getDetail().getLargeImage() : null;
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
		Model model = getModel();
		if (model == null) {
			return bean;
		}
		ModelField mf = model.getField("largeImage");
		if (mf == null) {
			return bean;
		}
		Map<String, String> map = mf.getCustoms();
		String width = map.get(ModelField.IMAGE_WIDTH);
		if (StringUtils.isNotBlank(width)) {
			bean.setWidth(NumberUtils.createInteger(width));
		}
		String height = map.get(ModelField.IMAGE_HEIGHT);
		if (StringUtils.isNotBlank(height)) {
			bean.setHeight(NumberUtils.createInteger(height));
		}
		return bean;
	}

	@Transient
	public String getVideo() {
		return getDetail() != null ? getDetail().getVideo() : null;
	}

	@Transient
	public String getVideoName() {
		return getDetail() != null ? getDetail().getVideoName() : null;
	}

	@Transient
	public String getFile() {
		return getDetail() != null ? getDetail().getFile() : null;
	}

	@Transient
	public String getFileName() {
		return getDetail() != null ? getDetail().getFileName() : null;
	}

	@Transient
	public Long getFileLength() {
		return getDetail() != null ? getDetail().getFileLength() : null;
	}

	@Transient
	public Boolean getAllowComment() {
		return getDetail() != null ? getDetail().getAllowComment() : null;
	}

	@Transient
	public String getStepName() {
		return getDetail() != null ? getDetail().getStepName() : null;
	}

	@Transient
	public String getFileExtension() {
		return FilenameUtils.getExtension(getFile());
	}

	@Transient
	public String getFileSize() {
		Long length = getFileLength();
		return Files.getSize(length);
	}

	@Transient
	public List<Node> getNodesExcludeMain() {
		List<Node> nodes = getNodes();
		List<Node> list = new ArrayList<Node>();
		for (int i = 1, len = nodes.size(); i < len; i++) {
			list.add(nodes.get(i));
		}
		return list;
	}

	@Transient
	public InfoProcess getProcess() {
		Set<InfoProcess> processes = getProcesses();
		if (processes != null && !processes.isEmpty()) {
			return processes.iterator().next();
		} else {
			return null;
		}
	}

	@Transient
	public Integer getBufferViews() {
		InfoBuffer buffer = getBuffer();
		if (buffer != null) {
			return getViews() + buffer.getViews();
		} else {
			return getViews();
		}
	}

	@Transient
	public Integer getBufferDownloads() {
		InfoBuffer buffer = getBuffer();
		if (buffer != null) {
			return getDownloads() + buffer.getDownloads();
		} else {
			return getDownloads();
		}
	}

	@Transient
	public Integer getBufferComments() {
		InfoBuffer buffer = getBuffer();
		if (buffer != null) {
			return getComments() + buffer.getComments();
		} else {
			return getComments();
		}
	}

	@Transient
	public Integer getBufferInvolveds() {
		InfoBuffer buffer = getBuffer();
		if (buffer != null) {
			return buffer.getInvolveds();
		} else {
			return 0;
		}
	}

	@Transient
	public Integer getBufferDiggs() {
		InfoBuffer buffer = getBuffer();
		if (buffer != null) {
			return getDiggs() + buffer.getDiggs();
		} else {
			return getDiggs();
		}
	}

	@Transient
	public Integer getBufferBurys() {
		InfoBuffer buffer = getBuffer();
		if (buffer != null) {
			return buffer.getBurys();
		} else {
			return 0;
		}
	}

	@Transient
	public InfoBuffer getBuffer() {
		Set<InfoBuffer> set = getBuffers();
		if (!CollectionUtils.isEmpty(set)) {
			return set.iterator().next();
		} else {
			return null;
		}
	}

	@Transient
	public void setBuffer(InfoBuffer buffer) {
		Set<InfoBuffer> set = getBuffers();
		if (set == null) {
			set = new HashSet<InfoBuffer>(1);
			setBuffers(set);
		} else {
			set.clear();
		}
		set.add(buffer);
	}

	/**
	 * 页数线程变量
	 */
	private static ThreadLocal<Integer> attrIdHolder = new ThreadLocal<Integer>();

	@Transient
	public static void setAttrId(Integer attrId) {
		attrIdHolder.set(attrId);
	}

	@Transient
	public static Integer getAttrId() {
		return attrIdHolder.get();
	}

	@Transient
	public static void resetAttrId() {
		attrIdHolder.remove();
	}

	@Transient
	public void applyDefaultValue() {
		if (getOrg() == null) {
			setOrg(getCreator().getOrg());
		}
		if (getViews() == null) {
			setViews(0);
		}
		if (getDownloads() == null) {
			setDownloads(0);
		}
		if (getComments() == null) {
			setComments(0);
		}
		if (getDiggs() == null) {
			setDiggs(0);
		}
		if (getScore() == null) {
			setScore(0);
		}
		if (getWithImage() == null) {
			setWithImage(false);
		}
		if (getPriority() == null) {
			setPriority(0);
		}
		if (getPublishDate() == null) {
			setPublishDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getStatus() == null) {
			setStatus(NORMAL);
		}
	}

	private Integer id;
	private List<InfoNode> infoNodes = new ArrayList<InfoNode>(0);
	private List<InfoTag> infoTags = new ArrayList<InfoTag>(0);
	private List<InfoSpecial> infoSpecials = new ArrayList<InfoSpecial>(0);
	private List<InfoAttribute> infoAttrs = new ArrayList<InfoAttribute>(0);
	
	private List<InfoAttr> infoAttrList = new ArrayList<InfoAttr>(0);
	private List<InfoParameter> infoParameterList = new ArrayList<InfoParameter>(0);
	
	private List<InfoImage> images = new ArrayList<InfoImage>(0);
	private List<InfoFile> files = new ArrayList<InfoFile>(0);
	private Map<String, String> customs = new HashMap<String, String>(0);
	private Map<String, String> clobs = new HashMap<String, String>(0);
	private Set<InfoBuffer> buffers = new HashSet<InfoBuffer>(0);
	private Set<InfoMemberGroup> infoGroups = new HashSet<InfoMemberGroup>(0);
	private SortedSet<InfoOrg> infoOrgs = new TreeSet<InfoOrg>(
			new InfoOrgComparator());
	private Set<InfoProcess> processes = new HashSet<InfoProcess>(0);

	private Node node;
	private Org org;
	private Brand brand;
	private User creator;
	private Site site;
	private InfoDetail detail;

	private Date publishDate;
	private Integer priority;
	private Boolean withImage;
	private Integer views;
	private Integer downloads;
	private Integer comments;
	private Integer diggs;
	private Integer score;
	private String status;
	private Byte p1;
	private Byte p2;
	private Byte p3;
	private Byte p4;
	private Byte p5;
	private Byte p6;
	/** 高级属性值 */
	private String a0;
	private String a1;
	private String a2;
	private String a3;
	private String a4;
	private String a5;
	private String a6;
	private String a7;
	private String a8;
	private String a9;
	private String a10;
	private String a11;
	private String a12;
	private String a13;
	private String a14;
	private String a15;
	private String a16;
	private String a17;
	private String a18;
	private String a19;

	private String highlightTitle;
	private String highlightText;

	public Info() {
	}

	public Info(Node node, User creator, Site site) {
		this.node = node;
		this.creator = creator;
		this.site = site;
	}

	@Id
	@Column(name = "f_info_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_info", pkColumnValue = "cms_info", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_info")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "info")
	@OrderBy("nodeIndex")
	public List<InfoNode> getInfoNodes() {
		return infoNodes;
	}

	public void setInfoNodes(List<InfoNode> infoNodes) {
		this.infoNodes = infoNodes;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "info")
	@OrderBy("tagIndex")
	public List<InfoTag> getInfoTags() {
		return infoTags;
	}

	public void setInfoTags(List<InfoTag> infoTags) {
		this.infoTags = infoTags;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "info")
	@OrderBy("specialIndex")
	public List<InfoSpecial> getInfoSpecials() {
		return infoSpecials;
	}

	public void setInfoSpecials(List<InfoSpecial> infoSpecials) {
		this.infoSpecials = infoSpecials;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "info")
	@OrderBy("attribute asc")
	public List<InfoAttribute> getInfoAttrs() {
		return infoAttrs;
	}
	
	public void setInfoAttrs(List<InfoAttribute> infoAttrs) {
		this.infoAttrs = infoAttrs;
	}
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "info")
	@OrderBy("attr asc")
	public List<InfoAttr> getInfoAttrList() {
		return infoAttrList;
	}
	
	public void setInfoAttrList(List<InfoAttr> infoAttrList) {
		this.infoAttrList = infoAttrList;
	}
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "info")
	@OrderBy("parameter asc")
	public List<InfoParameter> getInfoParameterList() {
		return infoParameterList;
	}

	public void setInfoParameterList(List<InfoParameter> infoParameterList) {
		this.infoParameterList = infoParameterList;
	}

	@ElementCollection
	@CollectionTable(name = "cms_info_image", joinColumns = @JoinColumn(name = "f_info_id"))
	@OrderColumn(name = "f_index")
	public List<InfoImage> getImages() {
		return images;
	}

	public void setImages(List<InfoImage> images) {
		this.images = images;
	}

	@ElementCollection
	@CollectionTable(name = "cms_info_file", joinColumns = @JoinColumn(name = "f_info_id"))
	@OrderColumn(name = "f_index")
	public List<InfoFile> getFiles() {
		return files;
	}

	public void setFiles(List<InfoFile> files) {
		this.files = files;
	}

	@ElementCollection
	@CollectionTable(name = "cms_info_custom", joinColumns = @JoinColumn(name = "f_info_id"))
	@MapKeyColumn(name = "f_key", length = 50)
	@Column(name = "f_value", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	@ElementCollection
	@CollectionTable(name = "cms_info_clob", joinColumns = @JoinColumn(name = "f_info_id"))
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

	// 为了addComments的处理，级联加上PERSIST。
	@OneToMany(fetch = FetchType.LAZY, cascade = { PERSIST, REMOVE }, mappedBy = "info")
	public Set<InfoBuffer> getBuffers() {
		return buffers;
	}

	public void setBuffers(Set<InfoBuffer> buffers) {
		this.buffers = buffers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "info")
	public Set<InfoMemberGroup> getInfoGroups() {
		return infoGroups;
	}

	public void setInfoGroups(Set<InfoMemberGroup> infoGroups) {
		this.infoGroups = infoGroups;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "info")
	@Sort(type = SortType.COMPARATOR, comparator = InfoOrgComparator.class)
	public SortedSet<InfoOrg> getInfoOrgs() {
		return infoOrgs;
	}

	public void setInfoOrgs(SortedSet<InfoOrg> infoOrgs) {
		this.infoOrgs = infoOrgs;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { REMOVE }, mappedBy = "info")
	public Set<InfoProcess> getProcesses() {
		return processes;
	}

	public void setProcesses(Set<InfoProcess> processes) {
		this.processes = processes;
	}

	@OneToOne(cascade = { REMOVE }, mappedBy = "info")
	public InfoDetail getDetail() {
		return detail;
	}

	public void setDetail(InfoDetail detail) {
		this.detail = detail;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_node_id", nullable = false)
	public Node getNode() {
		return this.node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_org_id", nullable = false)
	public Org getOrg() {
		return org;
	}
	
	public void setOrg(Org org) {
		this.org = org;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_brand_id", nullable = false)
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_creator_id", nullable = false)
	public User getCreator() {
		return this.creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_site_id", nullable = false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_publish_date", nullable = false, length = 19)
	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	@Column(name = "f_priority", nullable = false)
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "f_is_with_image", nullable = false, length = 1)
	public Boolean getWithImage() {
		return this.withImage;
	}

	public void setWithImage(Boolean withImage) {
		this.withImage = withImage;
	}

	@Column(name = "f_views", nullable = false)
	public Integer getViews() {
		return this.views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	@Column(name = "f_downloads", nullable = false)
	public Integer getDownloads() {
		return this.downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	@Column(name = "f_comments", nullable = false)
	public Integer getComments() {
		return this.comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	@Column(name = "f_diggs", nullable = false)
	public Integer getDiggs() {
		return diggs;
	}

	public void setDiggs(Integer diggs) {
		this.diggs = diggs;
	}

	@Column(name = "f_score", nullable = false)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "f_status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "f_p1")
	public Byte getP1() {
		return this.p1;
	}

	public void setP1(Byte p1) {
		this.p1 = p1;
	}

	@Column(name = "f_p2")
	public Byte getP2() {
		return this.p2;
	}

	public void setP2(Byte p2) {
		this.p2 = p2;
	}

	@Column(name = "f_p3")
	public Byte getP3() {
		return this.p3;
	}

	public void setP3(Byte p3) {
		this.p3 = p3;
	}

	@Column(name = "f_p4")
	public Byte getP4() {
		return this.p4;
	}

	public void setP4(Byte p4) {
		this.p4 = p4;
	}

	@Column(name = "f_p5")
	public Byte getP5() {
		return this.p5;
	}

	public void setP5(Byte p5) {
		this.p5 = p5;
	}

	@Column(name = "f_p6")
	public Byte getP6() {
		return this.p6;
	}

	public void setP6(Byte p6) {
		this.p6 = p6;
	}

	@Transient
	public String getHighlightTitle() {
		if (StringUtils.isBlank(highlightTitle)) {
			return getFullTitleOrTitle();
		} else {
			return highlightTitle;
		}
	}

	public void setHighlightTitle(String highlightTitle) {
		this.highlightTitle = highlightTitle;
	}

	@Transient
	public String getHighlightText() {
		if (StringUtils.isBlank(highlightText)) {
			return getDescription();
		} else {
			return highlightText;
		}
	}

	public void setHighlightText(String highlightText) {
		this.highlightText = highlightText;
	}

	/**
	 * @return the a0
	 */
	@Column(name = "f_a0")
	public String getA0() {
		return a0;
	}

	/**
	 * @param a0 the a0 to set
	 */
	public void setA0(String a0) {
		this.a0 = a0;
	}

	/**
	 * @return the a1
	 */
	@Column(name = "f_a1")
	public String getA1() {
		return a1;
	}

	/**
	 * @param a1 the a1 to set
	 */
	public void setA1(String a1) {
		this.a1 = a1;
	}

	/**
	 * @return the a2
	 */
	@Column(name = "f_a2")
	public String getA2() {
		return a2;
	}

	/**
	 * @param a2 the a2 to set
	 */
	public void setA2(String a2) {
		this.a2 = a2;
	}

	/**
	 * @return the a3
	 */
	@Column(name = "f_a3")
	public String getA3() {
		return a3;
	}

	/**
	 * @param a3 the a3 to set
	 */
	public void setA3(String a3) {
		this.a3 = a3;
	}

	/**
	 * @return the a4
	 */
	@Column(name = "f_a4")
	public String getA4() {
		return a4;
	}

	/**
	 * @param a4 the a4 to set
	 */
	public void setA4(String a4) {
		this.a4 = a4;
	}

	/**
	 * @return the a5
	 */
	@Column(name = "f_a5")
	public String getA5() {
		return a5;
	}

	/**
	 * @param a5 the a5 to set
	 */
	public void setA5(String a5) {
		this.a5 = a5;
	}

	/**
	 * @return the a6
	 */
	@Column(name = "f_a6")
	public String getA6() {
		return a6;
	}

	/**
	 * @param a6 the a6 to set
	 */
	public void setA6(String a6) {
		this.a6 = a6;
	}

	/**
	 * @return the a7
	 */
	@Column(name = "f_a7")
	public String getA7() {
		return a7;
	}

	/**
	 * @param a7 the a7 to set
	 */
	public void setA7(String a7) {
		this.a7 = a7;
	}

	/**
	 * @return the a8
	 */
	@Column(name = "f_a8")
	public String getA8() {
		return a8;
	}

	/**
	 * @param a8 the a8 to set
	 */
	public void setA8(String a8) {
		this.a8 = a8;
	}

	/**
	 * @return the a9
	 */
	@Column(name = "f_a9")
	public String getA9() {
		return a9;
	}

	/**
	 * @param a9 the a9 to set
	 */
	public void setA9(String a9) {
		this.a9 = a9;
	}

	/**
	 * @return the a10
	 */
	@Column(name = "f_a10")
	public String getA10() {
		return a10;
	}

	/**
	 * @param a10 the a10 to set
	 */
	public void setA10(String a10) {
		this.a10 = a10;
	}

	/**
	 * @return the a11
	 */
	@Column(name = "f_a11")
	public String getA11() {
		return a11;
	}

	/**
	 * @param a11 the a11 to set
	 */
	public void setA11(String a11) {
		this.a11 = a11;
	}

	/**
	 * @return the a12
	 */
	@Column(name = "f_a12")
	public String getA12() {
		return a12;
	}

	/**
	 * @param a12 the a12 to set
	 */
	public void setA12(String a12) {
		this.a12 = a12;
	}

	/**
	 * @return the a13
	 */
	@Column(name = "f_a13")
	public String getA13() {
		return a13;
	}

	/**
	 * @param a13 the a13 to set
	 */
	public void setA13(String a13) {
		this.a13 = a13;
	}

	/**
	 * @return the a14
	 */
	@Column(name = "f_a14")
	public String getA14() {
		return a14;
	}

	/**
	 * @param a14 the a14 to set
	 */
	public void setA14(String a14) {
		this.a14 = a14;
	}

	/**
	 * @return the a15
	 */
	@Column(name = "f_a15")
	public String getA15() {
		return a15;
	}

	/**
	 * @param a15 the a15 to set
	 */
	public void setA15(String a15) {
		this.a15 = a15;
	}

	/**
	 * @return the a16
	 */
	@Column(name = "f_a16")
	public String getA16() {
		return a16;
	}

	/**
	 * @param a16 the a16 to set
	 */
	public void setA16(String a16) {
		this.a16 = a16;
	}

	/**
	 * @return the a17
	 */
	@Column(name = "f_a17")
	public String getA17() {
		return a17;
	}

	/**
	 * @param a17 the a17 to set
	 */
	public void setA17(String a17) {
		this.a17 = a17;
	}

	/**
	 * @return the a18
	 */
	@Column(name = "f_a18")
	public String getA18() {
		return a18;
	}

	/**
	 * @param a18 the a18 to set
	 */
	public void setA18(String a18) {
		this.a18 = a18;
	}

	/**
	 * @return the a19
	 */
	@Column(name = "f_a19")
	public String getA19() {
		return a19;
	}

	/**
	 * @param a19 the a19 to set
	 */
	public void setA19(String a19) {
		this.a19 = a19;
	}
	
	@Transient
	public String getAttrValue(Attr attr) {
		if (attr != null && attr.getPropertyIndex() != null) {
			try {
				String propertyName = ATTR_VALUE_PROPERTY_NAME_PREFIX + attr.getPropertyIndex();
				return (String) PropertyUtils.getProperty(this, propertyName);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Transient
	public void setAttrValue(Attr attr, String value) {
		if (attr != null) {
			if (StringUtils.isEmpty(value)) {
				value = null;
			}
			if (value == null || attr.getItems() != null ) {
				try {
					String propertyName = ATTR_VALUE_PROPERTY_NAME_PREFIX + attr.getPropertyIndex();
					PropertyUtils.setProperty(this, propertyName, value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
