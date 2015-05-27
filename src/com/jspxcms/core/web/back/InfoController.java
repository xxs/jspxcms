package com.jspxcms.core.web.back;

import static com.jspxcms.core.support.Constants.CREATE;
import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.EDIT;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPERATION_SUCCESS;
import static com.jspxcms.core.support.Constants.OPRT;
import static com.jspxcms.core.support.Constants.SAVE_SUCCESS;
import static com.jspxcms.core.support.Constants.VIEW;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.jspxcms.common.util.RowSide;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.Attribute;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoAttr;
import com.jspxcms.core.domain.InfoDetail;
import com.jspxcms.core.domain.InfoImage;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Model;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.AttrService;
import com.jspxcms.core.service.AttributeService;
import com.jspxcms.core.service.InfoAttrService;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.InfoService;
import com.jspxcms.core.service.MemberGroupService;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.support.CmsException;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;

/**
 * InfoController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core/info")
public class InfoController {
	private static final Logger logger = LoggerFactory
			.getLogger(InfoController.class);

	public static final int INCLUDE_CHILDREN = 0;
	public static final int INCLUDE_MULTI = 1;
	public static final int MAIN_NODE = 2;

	@RequiresPermissions("core:info:left")
	@RequestMapping("left.do")
	public String left(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		User user = Context.getCurrentUser(request);
		Integer siteId = Context.getCurrentSiteId(request);
		List<Node> nodeList = nodeQuery.findList(siteId, null, true, null);
		nodeList = user.getInfoPermList(siteId, nodeList);
		modelMap.addAttribute("nodeList", nodeList);
		return "core/info/info_left";
	}

	@RequiresPermissions("core:info:list")
	@RequestMapping("list.do")
	public String list(
			Integer queryNodeId,
			Integer queryNodeType,
			Integer queryInfoPermType,
			String queryStatus,
			@PageableDefaults(sort = "publishDate", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		queryNodeType = queryNodeType == null ? 0 : queryNodeType;
		Integer siteId = Context.getCurrentSiteId(request);
		User user = Context.getCurrentUser(request);
		Integer nodeId = null;
		Integer mainNodeId = null;
		String treeNumber = null;
		Node queryNode = null;
		if (queryNodeId != null) {
			queryNode = nodeQuery.get(queryNodeId);
		}
		if (queryNode != null) {
			if (queryNodeType == INCLUDE_MULTI) {
				nodeId = queryNodeId;
			} else if (queryNodeType == MAIN_NODE) {
				mainNodeId = queryNodeId;
			} else {
				treeNumber = queryNode.getTreeNumber();
			}
		}
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		int infoPermType = user.getInfoPermType(siteId);
		if (queryInfoPermType != null && queryInfoPermType > infoPermType) {
			infoPermType = queryInfoPermType;
		}
		boolean allInfoPerm = user.getAllInfoPerm(siteId);
		Page<Info> pagedList = query.findAll(siteId, mainNodeId, nodeId,
				treeNumber, user.getId(), allInfoPerm, infoPermType,
				queryStatus, params, pageable);
		List<Attribute> attributeList = attributeService.findList(siteId);
		modelMap.addAttribute("pagedList", pagedList);
		modelMap.addAttribute("attributeList", attributeList);
		modelMap.addAttribute("queryNodeId", queryNodeId);
		modelMap.addAttribute("queryNodeType", queryNodeType);
		modelMap.addAttribute("queryInfoPermType", queryInfoPermType);
		modelMap.addAttribute("queryStatus", queryStatus);
		return "core/info/info_list";
	}

	@RequiresPermissions("core:info:create")
	@RequestMapping("create.do")
	public String create(Integer id, Integer queryNodeId,
			Integer queryNodeType, Integer queryInfoPermType,
			String queryStatus, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		if (id != null) {
			Info bean = query.get(id);
			modelMap.addAttribute("bean", bean);
		}
		Node node;
		if (queryNodeId == null) {
			node = nodeQuery.findRoot(site.getId());
			if (node == null) {
				throw new CmsException("info.error.nodeNotFound");
			}
		} else {
			node = nodeQuery.get(queryNodeId);
		}
		Model model = node.getInfoModel();
		List<Attribute> attrList = attributeService.findList(site.getId());
		List<Attr> attrssList = attrService.findList(site.getId());
		List<MemberGroup> groupList = memberGroupService.findList();
		String orgTreeNumber = site.getOrg().getTreeNumber();
		
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("attrList", attrList);
		modelMap.addAttribute("attrssList", attrssList);
		modelMap.addAttribute("groupList", groupList);
		modelMap.addAttribute("orgTreeNumber", orgTreeNumber);
		modelMap.addAttribute("queryNodeId", queryNodeId);
		modelMap.addAttribute("queryNodeType", queryNodeType);
		modelMap.addAttribute("queryInfoPermType", queryInfoPermType);
		modelMap.addAttribute("queryStatus", queryStatus);
		modelMap.addAttribute(OPRT, CREATE);
		return "core/info/info_form";
	}

	@RequiresPermissions("core:info:edit")
	@RequestMapping("edit.do")
	public String edit(
			Integer id,
			Integer position,
			Integer queryNodeId,
			@RequestParam(defaultValue = "0") int queryNodeType,
			Integer queryInfoPermType,
			String queryStatus,
			@PageableDefaults(sort = "publishDate", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		User user = Context.getCurrentUser(request);
		Info bean = query.get(id);
		if (!bean.isDataPerm(user)) {
			throw new CmsException("accessDenied");
		}
		Integer nodeId = null;
		Integer mainNodeId = null;
		String treeNumber = null;
		Node queryNode = null;
		if (queryNodeId != null) {
			queryNode = nodeQuery.get(queryNodeId);
		}
		if (queryNode != null) {
			if (queryNodeType == INCLUDE_MULTI) {
				nodeId = queryNodeId;
			} else if (queryNodeType == MAIN_NODE) {
				mainNodeId = queryNodeId;
			} else {
				treeNumber = queryNode.getTreeNumber();
			}
		}
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		Integer infoPermType = user.getInfoPermType(site.getId());
		if (queryInfoPermType != null && queryInfoPermType > infoPermType) {
			infoPermType = queryInfoPermType;
		}
		boolean allInfoPerm = user.getAllInfoPerm(site.getId());
		RowSide<Info> side = query.findSide(site.getId(), mainNodeId, nodeId,
				treeNumber, user.getId(), allInfoPerm, infoPermType,
				queryStatus, params, bean, position, pageable.getSort());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);

		Node node = bean.getNode();
		Model model = bean.getModel();
		List<Attribute> attrList = attributeService.findList(site.getId());
		
		List<MemberGroup> groupList = memberGroupService.findList();
		String orgTreeNumber = site.getOrg().getTreeNumber();

		System.out.println("iddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd:"+bean.getNode().getId());
		
		List<InfoAttr> iaList = infoAttrService.getByInfoId(bean.getNode().getId());
		List<Attr> aList = new ArrayList<Attr>();
		if(iaList != null && iaList.size()>0){
			for (int i = 0; i < iaList.size(); i++) {
				aList.add(iaList.get(i).getAttr());
			}
		}
		modelMap.addAttribute("attrssrrrrrrList", aList);
		
		
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("attrList", attrList);
		modelMap.addAttribute("groupList", groupList);
		modelMap.addAttribute("orgTreeNumber", orgTreeNumber);
		modelMap.addAttribute("queryNodeId", queryNodeId);
		modelMap.addAttribute("queryNodeType", queryNodeType);
		modelMap.addAttribute("queryInfoPermType", queryInfoPermType);
		modelMap.addAttribute("queryStatus", queryStatus);
		modelMap.addAttribute(OPRT, EDIT);
		return "core/info/info_form";
	}

	@RequiresPermissions("core:info:edit")
	@RequestMapping("view.do")
	public String view(
			Integer id,
			Integer position,
			Integer queryNodeId,
			@RequestParam(defaultValue = "0") int queryNodeType,
			Integer queryInfoPermType,
			String queryStatus,
			@PageableDefaults(sort = "publishDate", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		User user = Context.getCurrentUser(request);
		Info bean = query.get(id);
		if (!bean.isDataPerm(user)) {
			throw new CmsException("accessDenied");
		}
		Integer nodeId = null;
		Integer mainNodeId = null;
		String treeNumber = null;
		Node queryNode = null;
		if (queryNodeId != null) {
			queryNode = nodeQuery.get(queryNodeId);
		}
		if (queryNode != null) {
			if (queryNodeType == INCLUDE_MULTI) {
				nodeId = queryNodeId;
			} else if (queryNodeType == MAIN_NODE) {
				mainNodeId = queryNodeId;
			} else {
				treeNumber = queryNode.getTreeNumber();
			}
		}
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		Integer infoPermType = user.getInfoPermType(site.getId());
		if (queryInfoPermType != null && queryInfoPermType > infoPermType) {
			infoPermType = queryInfoPermType;
		}
		boolean allInfoPerm = user.getAllInfoPerm(site.getId());
		RowSide<Info> side = query.findSide(site.getId(), mainNodeId, nodeId,
				treeNumber, user.getId(), allInfoPerm, infoPermType,
				queryStatus, params, bean, position, pageable.getSort());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);

		modelMap.addAttribute("queryNodeId", queryNodeId);
		modelMap.addAttribute("queryNodeType", queryNodeType);
		modelMap.addAttribute("queryInfoPermType", queryInfoPermType);
		modelMap.addAttribute("queryStatus", queryStatus);
		modelMap.addAttribute(OPRT, VIEW);
		return "core/info/info_view";
	}

	@RequiresPermissions("core:info:save")
	@RequestMapping("save.do")
	public String save(Info bean, InfoDetail detail, Integer[] nodeIds,
			Integer[] specialIds, Integer[] viewGroupIds, Integer[] viewOrgIds,
			Integer[] attrIds, Integer nodeId, String tagKeywords,
			@RequestParam(defaultValue = "false") boolean draft,
			String[] imagesName, String[] imagesText, String[] imagesImage,
			String redirect, Integer queryNodeId, Integer queryNodeType,
			Integer queryInfoPermType, String queryStatus,
			HttpServletRequest request, RedirectAttributes ra) {
		Integer siteId = Context.getCurrentSiteId(request);
		Integer userId = Context.getCurrentUserId(request);
		Map<String, String> customs = Servlets.getParameterMap(request,
				"customs_");
		Map<String, String> clobs = Servlets.getParameterMap(request, "clobs_");
		if (StringUtils.isBlank(detail.getMetaDescription())) {
			String title = detail.getTitle();
			detail.setMetaDescription(Info.getDescription(clobs, title));
		}
		String[] tagNames = splitTagKeywords(tagKeywords, request);
		Map<String, String> attrImages = Servlets.getParameterMap(request,
				"attrImages_");
		List<InfoImage> images = new ArrayList<InfoImage>();
		if (imagesName != null) {
			InfoImage infoImage;
			for (int i = 0, len = imagesName.length; i < len; i++) {
				if (StringUtils.isNotBlank(imagesName[i])
						|| StringUtils.isNotBlank(imagesText[i])
						|| StringUtils.isNotBlank(imagesImage[i])) {
					infoImage = new InfoImage(imagesName[i], imagesText[i],
							imagesImage[i]);
					images.add(infoImage);
				}
			}
		}
		String status = draft ? Info.DRAFT : null;
		service.save(bean, detail, nodeIds, specialIds, viewGroupIds,
				viewOrgIds, customs, clobs, images, null, attrIds, attrImages,
				tagNames, nodeId, userId, status, siteId);
		logger.info("save Info, title={}.", bean.getTitle());
		ra.addAttribute("queryNodeId", queryNodeId);
		ra.addAttribute("queryNodeType", queryNodeType);
		ra.addAttribute("queryInfoPermType", queryInfoPermType);
		ra.addAttribute("queryStatus", queryStatus);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else if (Constants.REDIRECT_CREATE.equals(redirect)) {
			return "redirect:create.do";
		} else {
			ra.addAttribute("id", bean.getId());
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:info:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") Info bean,
			@ModelAttribute("detail") InfoDetail detail, Integer[] nodeIds,
			Integer[] specialIds, Integer[] viewGroupIds, Integer[] viewOrgIds,
			Integer[] attrIds, Integer nodeId, String tagKeywords,
			@RequestParam(defaultValue = "false") boolean pass,
			@RequestParam(defaultValue = "false") boolean remainDescription,
			String[] imagesName, String[] imagesText, String[] imagesImage,
			Integer position, Integer queryNodeId, Integer queryNodeType,
			Integer queryInfoPermType, String queryStatus, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		User user = Context.getCurrentUser(request);
		if (!bean.isDataPerm(user) || !bean.isAuditPerm(user)) {
			throw new CmsException("accessDenied");
		}
		Map<String, String> customs = Servlets.getParameterMap(request,
				"customs_");
		Map<String, String> clobs = Servlets.getParameterMap(request, "clobs_");
		if (!remainDescription
				|| StringUtils.isBlank(detail.getMetaDescription())) {
			String title = detail.getTitle();
			detail.setMetaDescription(Info.getDescription(clobs, title));
		}
		String[] tagNames = splitTagKeywords(tagKeywords, request);
		Map<String, String> attrImages = Servlets.getParameterMap(request,
				"attrImages_");
		List<InfoImage> images = new ArrayList<InfoImage>();
		if (imagesName != null) {
			InfoImage infoImage;
			for (int i = 0, len = imagesName.length; i < len; i++) {
				if (StringUtils.isNotBlank(imagesName[i])
						|| StringUtils.isNotBlank(imagesImage[i])) {
					infoImage = new InfoImage(imagesName[i], imagesText[i],
							imagesImage[i]);
					images.add(infoImage);
				}
			}
		}
		if (viewGroupIds == null) {
			viewGroupIds = new Integer[0];
		}
		if (viewOrgIds == null) {
			viewOrgIds = new Integer[0];
		}
		service.update(bean, detail, nodeIds, specialIds, viewGroupIds,
				viewOrgIds, customs, clobs, images, null, attrIds, attrImages,
				tagNames, nodeId, user, pass);
		logger.info("update Info, title={}.", bean.getTitle());
		ra.addAttribute("queryNodeId", queryNodeId);
		ra.addAttribute("queryNodeType", queryNodeType);
		ra.addAttribute("queryInfoPermType", queryInfoPermType);
		ra.addAttribute("queryStatus", queryStatus);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:info:audit_pass")
	@RequestMapping("audit_pass.do")
	public String auditPass(Integer[] ids, String opinion, Integer position,
			Integer queryNodeId, Integer queryNodeType,
			Integer queryInfoPermType, String queryStatus, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		Integer userId = Context.getCurrentUserId(request);
		List<Info> beans = service.pass(ids, userId, opinion);
		for (Info bean : beans) {
			logger.info("audit pass Info, title={}.", bean.getTitle());
		}
		ra.addAttribute("queryNodeId", queryNodeId);
		ra.addAttribute("queryNodeType", queryNodeType);
		ra.addAttribute("queryInfoPermType", queryInfoPermType);
		ra.addAttribute("queryStatus", queryStatus);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		if (Constants.REDIRECT_EDIT.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		} else if (Constants.REDIRECT_VIEW.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:view.do";
		} else {
			return "redirect:list.do";
		}
	}

	@RequiresPermissions("core:info:audit_reject")
	@RequestMapping("audit_reject.do")
	public String auditReject(Integer[] ids, String opinion, Integer position,
			Integer queryNodeId, Integer queryNodeType,
			Integer queryInfoPermType, String queryStatus, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		Integer userId = Context.getCurrentUserId(request);
		List<Info> beans = service.reject(ids, userId, opinion, false);
		for (Info bean : beans) {
			logger.info("audit reject Info, title={}.", bean.getTitle());
		}
		ra.addAttribute("queryNodeId", queryNodeId);
		ra.addAttribute("queryNodeType", queryNodeType);
		ra.addAttribute("queryInfoPermType", queryInfoPermType);
		ra.addAttribute("queryStatus", queryStatus);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		if (Constants.REDIRECT_EDIT.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		} else if (Constants.REDIRECT_VIEW.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:view.do";
		} else {
			return "redirect:list.do";
		}
	}

	@RequiresPermissions("core:info:audit_return")
	@RequestMapping("audit_return.do")
	public String auditReturn(Integer[] ids, String opinion, Integer position,
			Integer queryNodeId, Integer queryNodeType,
			Integer queryInfoPermType, String queryStatus, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		Integer userId = Context.getCurrentUserId(request);
		List<Info> beans = service.reject(ids, userId, opinion, true);
		for (Info bean : beans) {
			logger.info("audit return Info, title={}.", bean.getTitle());
		}
		ra.addAttribute("queryNodeId", queryNodeId);
		ra.addAttribute("queryNodeType", queryNodeType);
		ra.addAttribute("queryInfoPermType", queryInfoPermType);
		ra.addAttribute("queryStatus", queryStatus);
		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		if (Constants.REDIRECT_EDIT.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		} else if (Constants.REDIRECT_VIEW.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:view.do";
		} else {
			return "redirect:list.do";
		}
	}

	@RequiresPermissions("core:info:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, Integer queryNodeId,
			Integer queryNodeType, Integer queryInfoPermType,
			String queryStatus, HttpServletRequest request,
			RedirectAttributes ra) {
		User user = Context.getCurrentUser(request);
		for (Integer id : ids) {
			Info bean = query.get(id);
			if (!bean.isDataPerm(user) || !bean.isAuditPerm(user)) {
				throw new CmsException("accessDenied");
			}
		}
		List<Info> beans = service.delete(ids);
		for (Info bean : beans) {
			logger.info("delete Info, name={}.", bean.getTitle());
		}
		ra.addAttribute("queryNodeId", queryNodeId);
		ra.addAttribute("queryNodeType", queryNodeType);
		ra.addAttribute("queryInfoPermType", queryInfoPermType);
		ra.addAttribute("queryStatus", queryStatus);
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}

	// @RequiresPermissions("core:info:get_keywords")
	// @RequestMapping("get_keywords.do")
	// public void getKeywords(HttpServletRequest request,
	// HttpServletResponse response) {
	// String title = Servlets.getParameter(request, "title");
	// Servlets.writeHtml(response, Strings.getKeywords(title));
	// }

	@ModelAttribute
	public void preloadBean(@RequestParam(required = false) Integer oid,
			org.springframework.ui.Model modelMap) {
		if (oid != null) {
			Info bean = query.get(oid);
			if (bean != null) {
				modelMap.addAttribute("bean", bean);
				InfoDetail detail = bean.getDetail();
				if (detail == null) {
					detail = new InfoDetail();
					detail.setId(oid);
					detail.setInfo(bean);
				}
				modelMap.addAttribute("detail", detail);
			}
		}
	}

	private String[] splitTagKeywords(String tagKeywords,
			HttpServletRequest request) {
		Locale locale = RequestContextUtils.getLocale(request);
		String split = messageSource.getMessage("info.tagKeywordsSplit", null,
				locale);
		if (StringUtils.isNotBlank(split)) {
			tagKeywords = StringUtils.replace(tagKeywords, split, ",");
		}
		return StringUtils.split(tagKeywords, ',');
	}

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private AttrService attrService;
	@Autowired
	private InfoAttrService infoAttrService;
	@Autowired
	private MemberGroupService memberGroupService;
	@Autowired
	private NodeQueryService nodeQuery;
	@Autowired
	private InfoService service;
	@Autowired
	private InfoQueryService query;
}
