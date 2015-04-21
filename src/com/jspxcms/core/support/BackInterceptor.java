package com.jspxcms.core.support;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.security.ShiroUser;
import com.jspxcms.core.service.UserService;

/**
 * BackInterceptor
 * 
 * @author liufang
 * 
 */
public class BackInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(BackInterceptor.class);

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 用户登录信息
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
			User user = userService.get(shiroUser.id);
			Context.setCurrentUser(request, user);
			Context.setCurrentUser(user);
		}
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView == null || !modelAndView.hasView()) {
			return;
		}
		boolean isRedirect = false;
		if (modelAndView.isReference()) {
			String viewName = modelAndView.getViewName();
			isRedirect = StringUtils.startsWith(viewName, "redirect:");
		} else {
			View view = modelAndView.getView();
			if (view instanceof SmartView) {
				isRedirect = ((SmartView) view).isRedirectView();
			}
		}
		ModelMap modelMap = modelAndView.getModelMap();
		// 重定向不需要加ctx参数
		if (!isRedirect) {
			modelMap.addAttribute(Constants.CTX, request.getContextPath());
		}
		// 增加search_string
		Map<String, String[]> searchMap = Servlets.getParameterValuesMap(
				request, Constants.SEARCH_PREFIX, true);
		String page = request.getParameter("page");
		if (page != null) {
			searchMap.put("page", new String[] { page });
		}
		Map<String, String[]> pageMap = Servlets.getParameterValuesMap(request,
				"page_", true);
		searchMap.putAll(pageMap);
		for (Entry<String, String[]> entry : searchMap.entrySet()) {
			String[] value = entry.getValue();
			// 避免jsp的el表达式出现 0 eq ''的情况
			if (value != null && value.length > 0
					&& (!"".equals(value[0]) || value.length > 1)) {
				modelMap.addAllAttributes(searchMap);
			}
		}
		if (!isRedirect) {
			StringBuilder searchString = new StringBuilder();
			StringBuilder searchStringNoSort = new StringBuilder();
			for (Entry<String, String[]> entry : searchMap.entrySet()) {
				String key = entry.getKey();
				String[] values = entry.getValue();
				for (String value : values) {
					try {
						value = URLEncoder.encode(value, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						logger.error("never", e);
					}
					searchString.append(key);
					searchString.append('=');
					searchString.append(value);
					searchString.append('&');
					if (!"page".equals(key)
							&& !StringUtils.startsWith(key, "page_sort")) {
						searchStringNoSort.append(key);
						searchStringNoSort.append('=');
						searchStringNoSort.append(value);
						searchStringNoSort.append('&');
					}
				}
			}
			int len = searchString.length();
			if (len > 1) {
				searchString.setLength(len - 1);
				modelMap.addAttribute(Constants.SEARCH_STRING,
						searchString.toString());
			}
			len = searchStringNoSort.length();
			if (len > 1) {
				searchStringNoSort.setLength(len - 1);
				modelMap.addAttribute(Constants.SEARCH_STRING_NO_SORT,
						searchStringNoSort.toString());
			}
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Context.resetCurrentUser();
	}

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
