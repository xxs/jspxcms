package com.jspxcms.core.web.back;

import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.SAVE_SUCCESS;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.core.domain.Attribute;
import com.jspxcms.core.service.AttributeService;
import com.jspxcms.core.support.Context;

/**
 * AttributeController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/attribute")
public class AttributeController {
	private static final Logger logger = LoggerFactory
			.getLogger(AttributeController.class);

	@RequiresPermissions("core:attribute:list")
	@RequestMapping("list.do")
	public String list(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		List<Attribute> list = service.findList(siteId);
		modelMap.addAttribute("list", list);
		return "core/attribute/attribute_list";
	}

	@RequiresPermissions("core:attribute:save")
	@RequestMapping("save.do")
	public String save(Attribute bean, HttpServletRequest request,
			RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		service.save(bean, siteId);
		logger.info("save Attribute, name={}.", bean.getName());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:attribute:batch_update")
	@RequestMapping("batch_update.do")
	public String batchUpdate(Integer[] id, String[] name, String[] number,
			Integer[] imageWidth, Integer[] imageHeight, RedirectAttributes ra) {
		if (ArrayUtils.isNotEmpty(id)) {
			Attribute[] beans = service.batchUpdate(id, name, number,
					imageWidth, imageHeight);
			for (Attribute bean : beans) {
				logger.info("update Attribute, name={}.", bean.getName());
			}
		}
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:attribute:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, RedirectAttributes ra) {
		Attribute[] beans = service.delete(ids);
		for (Attribute bean : beans) {
			logger.info("delete Attribute, name={}.", bean.getName());
		}
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	/**
	 * 检查编号是否存在
	 * 
	 * @return
	 */
	@RequestMapping("check_number.do")
	@ResponseBody
	public String checkNumber(String number, String original,
			HttpServletRequest request) {
		if (StringUtils.isBlank(number) || StringUtils.equals(number, original)) {
			return "true";
		}
		// 检查数据库是否重名
		Integer siteId = Context.getCurrentSiteId(request);
		String result = service.numberExist(number, siteId) ? "false" : "true";
		return result;
	}

	@Autowired
	private AttributeService service;
}
