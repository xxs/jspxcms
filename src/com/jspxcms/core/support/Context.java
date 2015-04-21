package com.jspxcms.core.support;

import java.util.Collection;

import javax.servlet.ServletRequest;

import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Org;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;

/**
 * CMS上下文
 * 
 * @author liufang
 * 
 */
public abstract class Context {
	public static Integer getCurrentSiteId(ServletRequest request) {
		Site site = getCurrentSite(request);
		return site != null ? site.getId() : null;
	}

	public static Site getCurrentSite(ServletRequest request) {
		Site site = (Site) request.getAttribute(SITE_REQUEST_NAME);
		return site;
	}

	public static void setCurrentSite(ServletRequest request, Site site) {
		request.setAttribute(SITE_REQUEST_NAME, site);
	}

	public static void resetCurrentSite(ServletRequest request) {
		request.removeAttribute(SITE_REQUEST_NAME);
	}

	public static Integer getCurrentUserId(ServletRequest request) {
		User user = getCurrentUser(request);
		return user != null ? user.getId() : null;
	}

	public static User getCurrentUser(ServletRequest request) {
		User user = (User) request.getAttribute(USER_REQUEST_NAME);
		return user;
	}

	public static void setCurrentUser(ServletRequest request, User user) {
		request.setAttribute(USER_REQUEST_NAME, user);
	}

	public static void resetCurrentUser(ServletRequest request) {
		request.removeAttribute(USER_REQUEST_NAME);
	}

	public static MemberGroup getCurrentGroup(ServletRequest request) {
		MemberGroup group = (MemberGroup) request
				.getAttribute(GROUP_REQUEST_NAME);
		return group;
	}

	public static void setCurrentGroup(ServletRequest request, MemberGroup group) {
		request.setAttribute(GROUP_REQUEST_NAME, group);
	}

	public static void resetCurrentGroup(ServletRequest request) {
		request.removeAttribute(GROUP_REQUEST_NAME);
	}

	public static Org getCurrentOrg(ServletRequest request) {
		Org org = (Org) request.getAttribute(ORG_REQUEST_NAME);
		return org;
	}

	public static void setCurrentOrg(ServletRequest request, Org org) {
		request.setAttribute(ORG_REQUEST_NAME, org);
	}

	public static void resetCurrentOrg(ServletRequest request) {
		request.removeAttribute(ORG_REQUEST_NAME);
	}

	public static Collection<MemberGroup> getCurrentGroups(
			ServletRequest request) {
		@SuppressWarnings("unchecked")
		Collection<MemberGroup> groups = (Collection<MemberGroup>) request
				.getAttribute(GROUPS_REQUEST_NAME);
		return groups;
	}

	public static void setCurrentGroups(ServletRequest request,
			Collection<MemberGroup> groups) {
		request.setAttribute(GROUPS_REQUEST_NAME, groups);
	}

	public static void resetCurrentGroups(ServletRequest request) {
		request.removeAttribute(GROUPS_REQUEST_NAME);
	}

	public static Collection<Org> getCurrentOrgs(ServletRequest request) {
		@SuppressWarnings("unchecked")
		Collection<Org> orgs = (Collection<Org>) request
				.getAttribute(ORGS_REQUEST_NAME);
		return orgs;
	}

	public static void setCurrentOrgs(ServletRequest request,
			Collection<Org> orgs) {
		request.setAttribute(ORGS_REQUEST_NAME, orgs);
	}

	public static void resetCurrentOrgs(ServletRequest request) {
		request.removeAttribute(ORGS_REQUEST_NAME);
	}

	/**
	 * 站点线程变量（页数）
	 */
	private static ThreadLocal<Site> siteHolder = new ThreadLocal<Site>();

	public static void setCurrentSite(Site site) {
		siteHolder.set(site);
	}

	public static Site getCurrentSite() {
		return siteHolder.get();
	}

	public static void resetCurrentSite() {
		siteHolder.remove();
	}

	/**
	 * 用户线程变量
	 */
	private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

	public static void setCurrentUser(User user) {
		userHolder.set(user);
	}

	public static User getCurrentUser() {
		return userHolder.get();
	}

	public static void resetCurrentUser() {
		userHolder.remove();
	}

	private static final String SITE_REQUEST_NAME = "_CMS_SITE_REQUEST";
	private static final String USER_REQUEST_NAME = "_CMS_USER_REQUEST";
	private static final String GROUP_REQUEST_NAME = "_CMS_GROUP_REQUEST";
	private static final String GROUPS_REQUEST_NAME = "_CMS_GROUPS_REQUEST";
	private static final String ORG_REQUEST_NAME = "_CMS_ORG_REQUEST";
	private static final String ORGS_REQUEST_NAME = "_CMS_ORGS_REQUEST";
}
