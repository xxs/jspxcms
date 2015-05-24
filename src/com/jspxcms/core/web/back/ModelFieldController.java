package com.jspxcms.core.web.back;

import static com.jspxcms.core.support.Constants.CREATE;
import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.EDIT;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPERATION_SUCCESS;
import static com.jspxcms.core.support.Constants.OPRT;
import static com.jspxcms.core.support.Constants.SAVE_SUCCESS;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Model;
import com.jspxcms.core.domain.ModelField;
import com.jspxcms.core.service.ModelFieldService;
import com.jspxcms.core.service.ModelService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.ModelTypeHolder;

/**
 * ModelFieldController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/model_field")
public class ModelFieldController {
	private static final Logger logger = LoggerFactory
			.getLogger(ModelFieldController.class);

	@RequiresPermissions("core:model_field:list")
	@RequestMapping("list.do")
	public String list(Integer modelId, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Model model = modelService.get(modelId);
		modelMap.addAttribute("model", model);
		List<ModelField> list = service.findList(modelId);
		modelMap.addAttribute("list", list);

		List<String> types = modelTypeHolder.getTypes();
		modelMap.addAttribute("types", types);
		modelMap.addAttribute("queryType", model.getType());
		return "core/model_field/model_field_list";
	}

	@RequiresPermissions("core:model_field:predefined_list")
	@RequestMapping("predefined_list.do")
	public String predefinedList(Integer modelId,
			org.springframework.ui.Model modelMap) {
		Model model = modelService.get(modelId);
		modelMap.addAttribute("model", model);
		String path = modelTypeHolder.getPaths().get(model.getType());
		
		System.out.println("00000000000"+path);
		
		return path + "model_predefined_list";
	}

	@RequiresPermissions("core:model_field:create")
	@RequestMapping("create.do")
	public String create(Integer modelId, Integer id,
			org.springframework.ui.Model modelMap) {
		Model model = modelService.get(modelId);
		modelMap.addAttribute("model", model);
		if (id != null) {
			ModelField bean = service.get(id);
			modelMap.addAttribute("bean", bean);
		}
		modelMap.addAttribute(OPRT, CREATE);
		return "core/model_field/model_field_form";
	}

	@RequiresPermissions("core:model_field:edit")
	@RequestMapping("edit.do")
	public String edit(Integer id, Integer position,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		ModelField bean = service.get(id);
		Model model = bean.getModel();
		RowSide<ModelField> side = service.findSide(model.getId(), bean,
				position);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		modelMap.addAttribute(OPRT, EDIT);
		return "core/model_field/model_field_form";
	}

	@RequiresPermissions("core:model_field:save")
	@RequestMapping("save.do")
	public String save(ModelField bean, Integer modelId, Boolean clob,
			String redirect, HttpServletRequest request, RedirectAttributes ra) {
		Map<String, String> customs = Servlets.getParameterMap(request,
				"customs_");
		service.save(bean, modelId, customs, clob);
		logger.info("save ModelField, name={}.", bean.getName());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			ra.addAttribute("modelId", modelId);
			return "redirect:list.do";
		} else if (Constants.REDIRECT_CREATE.equals(redirect)) {
			ra.addAttribute("modelId", modelId);
			return "redirect:create.do";
		} else {
			ra.addAttribute("id", bean.getId());
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:model_field:batch_save")
	@RequestMapping("/batch_save.do")
	public String batchSave(Integer modelId, String[] name, String[] label,
			Boolean[] dblColumn, String[] property, String[] custom,
			RedirectAttributes ra) {
		service.batchSave(modelId, name, label, dblColumn, property, custom);
		ra.addAttribute("modelId", modelId);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:model_field:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") ModelField bean, Boolean clob,
			Integer position, String redirect, HttpServletRequest request,
			RedirectAttributes ra) {
		Map<String, String> customs = Servlets.getParameterMap(request,
				"customs_");
		service.update(bean, customs, clob);
		logger.info("update ModelField, name={}.", bean.getName());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			ra.addAttribute("modelId", bean.getModel().getId());
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:model_field:batch_update")
	@RequestMapping("batch_update.do")
	public String batchUpdate(Integer modelId, Integer[] id, String[] name,
			String[] label, Boolean[] dblColumn, RedirectAttributes redirect) {
		ModelField[] beans = service.batchUpdate(id, name, label, dblColumn);
		for (ModelField bean : beans) {
			logger.info("update ModelField, name={}.", bean.getName());
		}
		redirect.addAttribute("modelId", modelId);
		redirect.addFlashAttribute("message", "success");
		return "redirect:list.do";
	}

	@RequiresPermissions("core:model_field:disable")
	@RequestMapping("disable.do")
	public String disable(Integer[] ids, Integer modelId, RedirectAttributes ra) {
		ModelField[] beans = service.disable(ids);
		for (ModelField bean : beans) {
			logger.info("disable ModelField, name={}.", bean.getName());
		}
		ra.addAttribute("modelId", modelId);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:model_field:enable")
	@RequestMapping("enable.do")
	public String enable(Integer[] ids, Integer modelId, RedirectAttributes ra) {
		ModelField[] beans = service.enable(ids);
		for (ModelField bean : beans) {
			logger.info("enable ModelField, name={}.", bean.getName());
		}
		ra.addAttribute("modelId", modelId);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		return "redirect:list.do";
	}

	@RequiresPermissions("core:model_field:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, Integer modelId, RedirectAttributes ra) {
		ModelField[] beans = service.delete(ids);
		for (ModelField bean : beans) {
			logger.info("delete ModelField, name={}.", bean.getName());
		}
		ra.addAttribute("modelId", modelId);
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	@ModelAttribute("bean")
	public ModelField preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}

	@Autowired
	private ModelTypeHolder modelTypeHolder;
	@Autowired
	private ModelService modelService;
	@Autowired
	private ModelFieldService service;
}
