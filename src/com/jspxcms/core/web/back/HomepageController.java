package com.jspxcms.core.web.back;

import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPERATION_FAILURE;
import static com.jspxcms.core.support.Constants.OPERATION_SUCCESS;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.security.CredentialsDigest;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.Context;

/**
 * HomepageController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/homepage")
public class HomepageController {
	@RequiresPermissions("core:homepage:welcome")
	@RequestMapping("welcome.do")
	public String welcome(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		User user = Context.getCurrentUser(request);
		modelMap.addAttribute("site", site);
		modelMap.addAttribute("user", user);
		return "core/homepage/welcome";
	}

	@RequiresPermissions("core:homepage:environment")
	@RequestMapping("environment.do")
	public String environment(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Properties props = System.getProperties();
		Runtime runtime = Runtime.getRuntime();
		long freeMemory = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		long maxMemory = runtime.maxMemory();
		long usedMemory = totalMemory - freeMemory;
		long useableMemory = maxMemory - totalMemory + freeMemory;
		int div = 1000;
		double freeMemoryMB = ((double) freeMemory) / div / div;
		double totalMemoryMB = ((double) totalMemory) / div / div;
		double usedMemoryMB = ((double) usedMemory) / div / div;
		double maxMemoryMB = ((double) maxMemory) / div / div;
		double useableMemoryMB = ((double) useableMemory) / div / div;
		modelMap.addAttribute("props", props);
		modelMap.addAttribute("maxMemoryMB", maxMemoryMB);
		modelMap.addAttribute("usedMemoryMB", usedMemoryMB);
		modelMap.addAttribute("useableMemoryMB", useableMemoryMB);
		modelMap.addAttribute("totalMemoryMB", totalMemoryMB);
		modelMap.addAttribute("freeMemoryMB", freeMemoryMB);
		return "core/homepage/environment";
	}

	@RequiresPermissions("core:homepage:personal:edit")
	@RequestMapping(value = "personal_edit.do")
	public String personalEdit(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		User user = Context.getCurrentUser(request);
		modelMap.addAttribute("user", user);
		return "core/homepage/personal";
	}

	@RequiresPermissions("core:homepage:personal:update")
	@RequestMapping(value = "personal_update.do")
	public String personalUpdate(String origPassword, String rawPassword,
			HttpServletRequest request, RedirectAttributes ra) {
		User user = Context.getCurrentUser(request);
		if (credentialsDigest.matches(user.getPassword(), origPassword,
				user.getSaltBytes())) {
			userService.updatePassword(user.getId(), rawPassword);
			ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		} else {
			ra.addFlashAttribute(MESSAGE, OPERATION_FAILURE);
		}
		return "redirect:personal_edit.do";
	}

	@Autowired
	private CredentialsDigest credentialsDigest;
	@Autowired
	private UserService userService;
}
