package com.jspxcms.core.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.CollectionUtils;

import com.jspxcms.common.util.Encodes;
import com.jspxcms.core.support.Context;

/**
 * User
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_user", uniqueConstraints = @UniqueConstraint(columnNames = "f_username"))
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员
	 */
	public static final int MEMBER = 0;
	/**
	 * 管理员
	 */
	public static final int ADMIN = 1;
	/**
	 * 正常
	 */
	public static final int NORMAL = 0;
	/**
	 * 锁定
	 */
	public static final int LOCKED = 1;
	/**
	 * 未验证
	 */
	public static final int UNVERIFIED = 2;
	/**
	 * 男
	 */
	public static final String MALE = "M";
	/**
	 * 女
	 */
	public static final String FEMALE = "F";

	@Transient
	public boolean isCaptchaRequired() {
		return getDetail().isCaptchaRequired();
	}

	@Transient
	public boolean isNormal() {
		return getStatus() == NORMAL;
	}

	@Transient
	public boolean isAdmin() {
		return getType() == ADMIN;
	}

	/**
	 * 级别<=1的为超级管理员，拥有全局功能权限。如系统设置、站点管理、全局用户管理、全局组织管理。
	 * 
	 * @return
	 */
	@Transient
	public boolean isSuper() {
		return getRank() <= 1;
	}

	/**
	 * 是否为创始人。创始人拥有所有权限。
	 * 
	 * @return
	 */
	@Transient
	public boolean isRoot() {
		return getId() == 1;
	}

	@Transient
	public Boolean getAllPerm(Integer siteId) {
		if (isRoot()) {
			return true;
		}
		List<Role> roles = getRoles(siteId);
		if (roles == null) {
			return null;
		}
		for (Role role : roles) {
			if (role.getAllPerm()) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public Set<String> getPerms(Integer siteId) {
		List<Role> roles = getRoles(siteId);
		if (roles == null) {
			return null;
		}
		Set<String> set = new HashSet<String>();
		if (isRoot()) {
			// root用户，拥有所有权限，直接返回
			set.add("*");
			return set;
		}
		for (Role role : roles) {
			if (role.getAllPerm()) {
				// 拥有所有权限，直接返回
				set.add("*");
				return set;
			}
		}
		for (Role role : roles) {
			String perms = role.getPerms();
			if (StringUtils.isNotBlank(perms)) {
				for (String perm : StringUtils.split(perms, ',')) {
					set.add(perm);
				}
			}
		}
		return set;
	}

	@Transient
	public Boolean getAllInfoPerm(Integer siteId) {
		List<Role> roles = getRoles(siteId);
		if (roles == null) {
			return null;
		}
		for (Role role : roles) {
			if (role.getAllInfoPerm()) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public Boolean getAllNodePerm(Integer siteId) {
		List<Role> roles = getRoles(siteId);
		if (roles == null) {
			return null;
		}
		for (Role role : roles) {
			if (role.getAllNodePerm()) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public Boolean getInfoFinalPerm(Integer siteId) {
		List<Role> roles = getRoles(siteId);
		if (roles == null) {
			return null;
		}
		for (Role role : roles) {
			if (role.getInfoFinalPerm()) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public Set<Node> getNodePerms(Integer siteId) {
		List<Role> roles = getRoles(siteId);
		if (roles == null) {
			return null;
		}
		Set<Node> nodes = new HashSet<Node>();
		for (Role role : roles) {
			nodes.addAll(role.getNodePerms());
		}
		return nodes;
	}

	@Transient
	public Set<Node> getInfoPerms(Integer siteId) {
		List<Role> roles = getRoles(siteId);
		if (roles == null) {
			return null;
		}
		Set<Node> nodes = new HashSet<Node>();
		for (Role role : roles) {
			nodes.addAll(role.getInfoPerms());
		}
		return nodes;
	}

	@Transient
	public Integer getInfoPermType(Integer siteId) {
		List<Role> roles = getRoles(siteId);
		if (roles == null) {
			return null;
		}
		// 值越小，权限越大
		Integer infoPermType = Role.INFO_PERM_SELF;
		for (Role role : roles) {
			if (role.getInfoPermType() < infoPermType) {
				infoPermType = role.getInfoPermType();
			}
		}
		return infoPermType;
	}

	@Transient
	public List<Node> getInfoPermList(Integer siteId, List<Node> nodeList) {
		boolean isAllPerm = getAllInfoPerm(siteId);
		if (isAllPerm) {
			return new ArrayList<Node>(nodeList);
		}
		List<Node> permList = new ArrayList<Node>();
		Set<Node> perms = getInfoPerms(siteId);
		String treeNumber;
		for (Node node : nodeList) {
			treeNumber = node.getTreeNumber();
			for (Node n : perms) {
				if (n.getTreeNumber().startsWith(treeNumber)) {
					permList.add(node);
					break;
				}
			}
		}
		return permList;
	}

	@Transient
	public List<Node> getNodePermList(Integer siteId, List<Node> nodeList) {
		boolean isAllPerm = getAllNodePerm(siteId);
		if (isAllPerm) {
			return new ArrayList<Node>(nodeList);
		}
		List<Node> permList = new ArrayList<Node>();
		Set<Node> perms = getNodePerms(siteId);
		String treeNumber;
		for (Node node : nodeList) {
			treeNumber = node.getTreeNumber();
			for (Node n : perms) {
				if (n.getTreeNumber().startsWith(treeNumber)) {
					permList.add(node);
					break;
				}
			}
		}
		return permList;
	}

	@Transient
	public List<Role> getRoles() {
		List<UserRole> userRoles = getUserRoles();
		if (userRoles == null) {
			return null;
		}
		List<Role> roles = new ArrayList<Role>(userRoles.size());
		for (UserRole userRole : userRoles) {
			roles.add(userRole.getRole());
		}
		return roles;
	}

	@Transient
	public List<Role> getRoles(Integer siteId) {
		List<UserRole> userRoles = getUserRoles();
		if (userRoles == null) {
			return null;
		}
		List<Role> roles = new ArrayList<Role>(userRoles.size());
		Role role;
		for (UserRole userRole : userRoles) {
			role = userRole.getRole();
			if (role.getSite().getId().equals(siteId)) {
				roles.add(role);
			}
		}
		return roles;
	}

	@Transient
	public List<Role> getCurrRoles() {
		Site site = Context.getCurrentSite();
		if (site != null) {
			return getRoles(site.getId());
		} else {
			return getRoles();
		}
	}

	@Transient
	public List<Org> getOrgsExcludeMain() {
		List<UserOrg> uos = getUserOrgs();
		List<Org> orgs = new ArrayList<Org>();
		for (int i = 1, len = uos.size(); i < len; i++) {
			orgs.add(uos.get(i).getOrg());
		}
		return orgs;
	}

	@Transient
	public List<Org> getOrgs() {
		List<UserOrg> uos = getUserOrgs();
		List<Org> orgs = new ArrayList<Org>(uos.size());
		for (UserOrg uo : uos) {
			orgs.add(uo.getOrg());
		}
		return orgs;
	}

	@Transient
	public List<MemberGroup> getGroupsExcludeMain() {
		List<UserMemberGroup> ugs = getUserGroups();
		List<MemberGroup> groups = new ArrayList<MemberGroup>(ugs.size());
		for (int i = 1, len = ugs.size(); i < len; i++) {
			groups.add(ugs.get(i).getGroup());
		}
		return groups;
	}

	@Transient
	public List<MemberGroup> getGroups() {
		List<UserMemberGroup> ugs = getUserGroups();
		List<MemberGroup> groups = new ArrayList<MemberGroup>(ugs.size());
		for (UserMemberGroup ug : ugs) {
			groups.add(ug.getGroup());
		}
		return groups;
	}

	@Transient
	public UserDetail getDetail() {
		Set<UserDetail> set = getDetails();
		if (!CollectionUtils.isEmpty(set)) {
			return set.iterator().next();
		} else {
			return null;
		}
	}

	@Transient
	public void setDetail(UserDetail detail) {
		Set<UserDetail> set = getDetails();
		if (set == null) {
			set = new HashSet<UserDetail>();
			setDetails(set);
		}
		set.clear();
		set.add(detail);
	}

	@Transient
	public byte[] getSaltBytes() {
		String salt = getSalt();
		if (StringUtils.isNotBlank(salt)) {
			return Encodes.decodeHex(salt);
		} else {
			return null;
		}
	}

	@Transient
	public String getValidationValue() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getValidationValue() : null;
	}

	@Transient
	public Date getValidationDate() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getValidationDate() : null;
	}

	@Transient
	public Date getLoginErrorDate() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getLoginErrorDate() : null;
	}

	@Transient
	public Integer getLoginErrorCount() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getLoginErrorCount() : null;
	}

	@Transient
	public Date getPrevLoginDate() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getPrevLoginDate() : null;
	}

	@Transient
	public String getPrevLoginIp() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getPrevLoginIp() : null;
	}

	@Transient
	public Date getLastLoginDate() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getLastLoginDate() : null;
	}

	@Transient
	public String getLastLoginIp() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getLastLoginIp() : null;
	}

	@Transient
	public Date getCreationDate() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getCreationDate() : null;
	}

	@Transient
	public String getCreationIp() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getCreationIp() : null;
	}

	@Transient
	public Integer getLogins() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getLogins() : null;
	}

	@Transient
	public String getAvatar() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getAvatar() : null;
	}

	@Transient
	public String getBio() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getBio() : null;
	}

	@Transient
	public String getComeFrom() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getComeFrom() : null;
	}

	@Transient
	public String getQq() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getQq() : null;
	}

	@Transient
	public String getMsn() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getMsn() : null;
	}

	@Transient
	public String getWeixin() {
		UserDetail detail = getDetail();
		return detail != null ? detail.getWeixin() : null;
	}

	@Transient
	public void applyDefaultValue() {
		if (getRank() == null) {
			setRank(999);
		}
		if (getType() == null) {
			setType(MEMBER);
		}
		if (getStatus() == null) {
			setStatus(NORMAL);
		}
	}

	private Integer id;
	private List<UserRole> userRoles = new ArrayList<UserRole>(0);
	private List<UserOrg> userOrgs = new ArrayList<UserOrg>(0);
	private List<UserMemberGroup> userGroups = new ArrayList<UserMemberGroup>(0);
	private Set<UserDetail> details = new HashSet<UserDetail>(0);

	private Org org;
	private MemberGroup group;

	private String username;
	private String password;
	private String salt;
	private String email;
	private String realName;
	private Date birthDate;
	private String gender;
	private String mobile;
	private String validationType;
	private String validationKey;
	private Integer rank;
	private Integer type;
	private Integer status;

	private String rawPassword;

	@Id
	@Column(name = "f_user_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_user", pkColumnValue = "cms_user", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_user")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "user")
	@OrderBy("roleIndex")
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "user")
	@OrderBy("orgIndex")
	public List<UserOrg> getUserOrgs() {
		return userOrgs;
	}

	public void setUserOrgs(List<UserOrg> userOrgs) {
		this.userOrgs = userOrgs;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "user")
	@OrderBy("groupIndex")
	public List<UserMemberGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserMemberGroup> userGroups) {
		this.userGroups = userGroups;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "user")
	public Set<UserDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<UserDetail> details) {
		this.details = details;
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
	@JoinColumn(name = "f_membergroup_id", nullable = false)
	public MemberGroup getGroup() {
		return group;
	}

	public void setGroup(MemberGroup group) {
		this.group = group;
	}

	@NotNull
	@Length(min = 1, max = 50)
	@Column(name = "f_username", unique = true, nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "f_password", length = 128)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "f_salt", length = 32)
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Email
	@Length(max = 100)
	@Column(name = "f_email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(max = 100)
	@Column(name = "f_real_name", length = 100)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_birth_date", length = 19)
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Pattern(regexp = "[F,M]")
	@Length(max = 1)
	@Column(name = "f_gender", length = 1)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Length(max = 100)
	@Column(name = "f_mobile", length = 100)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Length(max = 100)
	@Column(name = "f_validation_key", length = 100)
	public String getValidationKey() {
		return validationKey;
	}

	public void setValidationKey(String validationKey) {
		this.validationKey = validationKey;
	}

	@Length(max = 50)
	@Column(name = "f_validation_type", length = 50)
	public String getValidationType() {
		return validationType;
	}

	public void setValidationType(String validationType) {
		this.validationType = validationType;
	}

	@Column(name = "f_rank", nullable = false)
	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(name = "f_type", nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "f_status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Length(max = 255)
	@Transient
	public String getRawPassword() {
		return rawPassword;
	}

	public void setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
	}
}
