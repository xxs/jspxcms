package com.jspxcms.core.web.back;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.Menu;
import com.jspxcms.core.support.MenuHolder;

/**
 * NavigationController
 * 
 * @author liufang
 * 
 */
@Controller
public class NavigationController {
	@RequestMapping({ "", "/", "/index.do" })
	public String index(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Site site = Context.getCurrentSite(request);
			User user = Context.getCurrentUser(request);
			List<Site> siteList = siteService.findByUserId(user.getId());
			modelMap.addAttribute("menus", menuHolder.getMenus());
			modelMap.addAttribute("user", user);
			modelMap.addAttribute("site", site);
			modelMap.addAttribute("siteList", siteList);
			return "index";
		} else {
			return "login";
		}
	}

	@RequiresPermissions("core:nav:container")
	@RequestMapping("/container.do")
	public String container() {
		return "container";
	}

	@RequiresPermissions("core:nav:nav")
	@RequestMapping("/nav.do")
	public String nav(String menuId, String subId,
			org.springframework.ui.Model modelMap) {
		Set<Menu> menus = menuHolder.getMenus();
		Menu menu = null;
		for (Menu m : menus) {
			if (m.getId().equals(menuId)) {
				if (StringUtils.isNotBlank(m.getPerm())) {
					Subject subject = SecurityUtils.getSubject();
					subject.checkPermission(m.getPerm());
				}
				menu = m;
				break;
			}
		}
		modelMap.addAttribute("menu", menu);
		modelMap.addAttribute("subId", subId);
		return "nav";
	}

	@Autowired
	private MenuHolder menuHolder;
	@Autowired
	private SiteService siteService;

}
