package com.jspxcms.core.support;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.Subject;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.security.ShiroUser;
import com.jspxcms.core.service.SiteShiroService;

/**
 * SiteFilter
 * 
 * @author liufang
 * 
 */
public class BackSiteFilter implements Filter {
	public static final String SITE_KEY = "_site";

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		Site site = resolveSite(request, response);
		Context.setCurrentSite(request, site);
		Context.setCurrentSite(site);
		chain.doFilter(request, response);
		Context.resetCurrentSite();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	private Site resolveSite(HttpServletRequest request,
			HttpServletResponse response) {
		Site site = null;
		Integer siteId;
		// 从parameter中获取
		String siteIdText = request.getParameter(SITE_KEY);
		if (StringUtils.isNotBlank(siteIdText)) {
			try {
				siteId = Integer.parseInt(siteIdText);
				site = siteShiroService.get(siteId);
			} catch (Exception e) {
				// continue
			}
		}
		// 从cookie中获取
		if (site == null) {
			siteIdText = Servlets.getCookie(request, SITE_KEY);
			if (StringUtils.isNotBlank(siteIdText)) {
				try {
					siteId = Integer.parseInt(siteIdText);
					site = siteShiroService.get(siteId);
				} catch (Exception e) {
					// continue
				}
			}
		}
		// 从域名中获取
		if (site == null) {
			String domain = request.getServerName();
			site = siteShiroService.findByDomain(domain);
		}
		// 从数据库中获得主站
		if (site == null) {
			site = siteShiroService.findDefault();
		}
		// 获取第一个有权限的站点
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		if (shiroUser != null) {
			List<Site> siteList = siteShiroService.findByUserId(shiroUser.id);
			if (!siteList.isEmpty()) {
				boolean contains = false;
				if (site != null) {
					for (Site s : siteList) {
						if (s.getId().equals(site.getId())) {
							contains = true;
							break;
						}
					}
				}
				if (!contains) {
					site = siteList.iterator().next();
				}
			}
		}
		if (site != null) {
			clearPermCache(request, site.getId());
			addSiteCookie(site.getId().toString(), request, response);
			return site;
		} else {
			// 站点必须存在
			throw new CmsException("site.error.siteNotFound");
		}
	}

	private void addSiteCookie(String siteIdText, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookie = new Cookie(SITE_KEY, siteIdText);
		String path = request.getContextPath();
		if (StringUtils.isBlank(path)) {
			path = "/";
		}
		cookie.setPath(path);
		response.addCookie(cookie);
	}

	private void clearPermCache(HttpServletRequest request, Integer currSiteId) {
		HttpSession session = request.getSession();
		Integer prevSiteId = (Integer) session.getAttribute(SITE_KEY);
		if (prevSiteId == null || !currSiteId.equals(prevSiteId)) {
			cacheManager.getCacheManager().clearAll();
		}
		session.setAttribute(SITE_KEY, currSiteId);
	}

	private EhCacheManager cacheManager;
	private SiteShiroService siteShiroService;

	public void setSiteShiroService(SiteShiroService siteShiroService) {
		this.siteShiroService = siteShiroService;
	}

	public void setCacheManager(EhCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}
