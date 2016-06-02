package com.jspxcms.core.support;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jspxcms.common.util.UserAgentUtils;
import com.jspxcms.core.domain.Global;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.security.ShiroUser;
import com.jspxcms.core.service.GlobalService;
import com.jspxcms.core.service.MemberGroupService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.UserService;

/**
 * ForeInterceptor
 * 
 * @author liufang
 * 
 */
public class ForeInterceptor implements HandlerInterceptor {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Global global = globalService.findUnique();
		Site site = null;
		if (global.getWithDomain()) {
			String domain = request.getServerName();
			site = siteService.findByDomain(domain);
		}
		if (site == null) {
			site = siteService.findDefault();
		}
		if (site == null) {
			throw new CmsException("site.error.siteNotFound");
		}
		Context.setCurrentSite(request, site);
		Context.setCurrentSite(site);
		// 用户登录信息，允许记住用户。
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
			User user = userService.get(shiroUser.id);
			Context.setCurrentUser(request, user);
			Context.setCurrentGroup(request, user.getGroup());
			Context.setCurrentGroups(request, user.getGroups());
			Context.setCurrentOrg(request, user.getOrg());
			Context.setCurrentOrgs(request, user.getOrgs());
		} else {
			MemberGroup anon = memberGroupService.getAnonymous();
			Context.setCurrentGroup(request, anon);
			Context.setCurrentGroups(request,
					Arrays.asList(new MemberGroup[] { anon }));
			// 未登录，组织为空
		}
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		System.out.println("执行了浏览器检测");
		if (modelAndView != null){
			// 如果是手机或平板访问的话，则跳转到手机视图页面。
			if(UserAgentUtils.isMobileOrTablet(request) && !org.apache.commons.lang3.StringUtils.startsWithIgnoreCase(modelAndView.getViewName(), "redirect:")){
				System.out.println("移动浏览器");
				modelAndView.setViewName("mobile" + modelAndView.getViewName());
				System.out.println("viewName+"+modelAndView.getViewName());
			}else{
				System.out.println("pc浏览器");
			}
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Context.resetCurrentSite();
	}

	private SiteService siteService;
	private GlobalService globalService;
	private UserService userService;
	private MemberGroupService memberGroupService;

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Autowired
	public void setGlobalService(GlobalService globalService) {
		this.globalService = globalService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setMemberGroupService(MemberGroupService memberGroupService) {
		this.memberGroupService = memberGroupService;
	}
}
