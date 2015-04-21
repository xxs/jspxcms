package com.jspxcms.ext.web.back;

import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.SAVE_SUCCESS;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.core.support.Context;
import com.jspxcms.ext.domain.GuestbookType;
import com.jspxcms.ext.service.GuestbookTypeService;
/**
 * GuestbookTypeController
 * 
 * @author yangxing
 * 
 */
@Controller
@RequestMapping("/ext/guestbook_type")
public class GuestbookTypeController {
	private static final Logger logger = LoggerFactory.getLogger(GuestbookTypeController.class);

	@RequiresPermissions("ext:guestbook_type:list")
	@RequestMapping("list.do")
	public String list(HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		List<GuestbookType> list = service.findList(siteId);
		modelMap.addAttribute("list", list);
		return "ext/guestbook_type/guestbook_type_list";
	}

	@RequiresPermissions("ext:guestbook_type:save")
	@RequestMapping("save.do")
	public String save(GuestbookType bean, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		service.save(bean, siteId);
		logger.info("save GuestbookType, name={}.", bean.getName());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		return "redirect:list.do";
	}
	
	@RequiresPermissions("ext:guestbook_type:batch_update")
	@RequestMapping("batch_update.do")
	public String batchUpdate(Integer[] id, String[] name, String[] number, String[] description, RedirectAttributes ra) {
		if (ArrayUtils.isNotEmpty(id)) {
			GuestbookType[] beans = service.batchUpdate(id, name, number, description);
			for (GuestbookType bean : beans) {
				logger.info("update GuestbookType, name={}.", bean.getName());
			}
		}
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("ext:guestbook_type:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, RedirectAttributes ra) {
		GuestbookType[] beans = service.delete(ids);
		for (GuestbookType bean : beans) {
			logger.info("delete GuestbookType, name={}.", bean.getName());
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@Autowired
	private GuestbookTypeService service;
}
