package com.jspxcms.core.web.back;

import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.SAVE_SUCCESS;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.service.AttrService;
import com.jspxcms.core.support.Context;

/**
 * AttrController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/attr")
public class AttrController {
	private static final Logger logger = LoggerFactory
			.getLogger(AttrController.class);

	@RequestMapping("list.do")
	public String list(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		List<Attr> list = service.findList(siteId);
		modelMap.addAttribute("list", list);
		return "core/attr/attr_list";
	}

	@RequestMapping("save.do")
	public String save(Attr bean, HttpServletRequest request,
			RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		service.save(bean, siteId);
		logger.info("save Attr, name={}.", bean.getName());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		return "redirect:list.do";
	}

	@RequestMapping("batch_update.do")
	public String batchUpdate(Integer[] id, String[] name, String[] number,
			Integer[] imageWidth, Integer[] imageHeight, RedirectAttributes ra) {
		if (ArrayUtils.isNotEmpty(id)) {
			Attr[] beans = service.batchUpdate(id, name, number,
					imageWidth, imageHeight);
			for (Attr bean : beans) {
				logger.info("update Attr, name={}.", bean.getName());
			}
		}
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		return "redirect:list.do";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "edit.do", method = RequestMethod.GET)
	public String edit(Integer id, ModelMap model) {
//		model.addAttribute("productCategoryTree", productCategoryService.findTree());
//		model.addAttribute("attributeValuePropertyCount", Product.ATTRIBUTE_VALUE_PROPERTY_COUNT);
		model.addAttribute("attr", service.get(id));
		return "core/attr/attr_form";
	}
	
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, RedirectAttributes ra) {
		Attr[] beans = service.delete(ids);
		for (Attr bean : beans) {
			logger.info("delete Attr, name={}.", bean.getName());
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
	private AttrService service;
}
