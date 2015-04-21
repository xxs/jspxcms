package com.jspxcms.ext.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;

/**
 * FriendlinkController
 * 
 * @author yangxing
 * 
 */
@Controller
public class FriendlinkController {
	public static final String TEMPLATE = "sys_friendlink.html";

	@RequestMapping(value = "/friendlink.jspx")
	public String veiw(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(TEMPLATE);
	}
}
