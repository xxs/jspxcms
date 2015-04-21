package com.jspxcms.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoBuffer;
import com.jspxcms.core.domain.InfoDetail;
import com.jspxcms.core.domain.InfoFile;
import com.jspxcms.core.domain.InfoImage;
import com.jspxcms.core.domain.InfoProcess;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.Workflow;
import com.jspxcms.core.listener.InfoDeleteListener;
import com.jspxcms.core.listener.InfoListener;
import com.jspxcms.core.listener.NodeDeleteListener;
import com.jspxcms.core.listener.OrgDeleteListener;
import com.jspxcms.core.listener.SiteDeleteListener;
import com.jspxcms.core.listener.UserDeleteListener;
import com.jspxcms.core.repository.InfoDao;
import com.jspxcms.core.service.CommentService;
import com.jspxcms.core.service.InfoAttributeService;
import com.jspxcms.core.service.InfoBufferService;
import com.jspxcms.core.service.InfoDetailService;
import com.jspxcms.core.service.InfoMemberGroupService;
import com.jspxcms.core.service.InfoNodeService;
import com.jspxcms.core.service.InfoOrgService;
import com.jspxcms.core.service.InfoService;
import com.jspxcms.core.service.InfoSpecialService;
import com.jspxcms.core.service.InfoTagService;
import com.jspxcms.core.service.NodeService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.service.WorkflowService;
import com.jspxcms.core.support.DeleteException;

/**
 * 信息Service实现
 * 
 * @author liufang
 * 
 */
@Service
@Transactional
public class InfoServiceImpl implements InfoService, SiteDeleteListener,
		OrgDeleteListener, NodeDeleteListener, UserDeleteListener {
	public Info save(Info bean, InfoDetail detail, Integer[] nodeIds,
			Integer[] specialIds, Integer[] viewGroupIds, Integer[] viewOrgIds,
			Map<String, String> customs, Map<String, String> clobs,
			List<InfoImage> images, List<InfoFile> files, Integer[] attrIds,
			Map<String, String> attrImages, String[] tagNames, Integer nodeId,
			Integer creatorId, String status, Integer siteId) {
		bean.setSite(siteService.get(siteId));
		User creator = userService.get(creatorId);
		bean.setCreator(creator);
		bean.setOrg(creator.getOrg());
		Node node = nodeService.refer(nodeId);
		bean.setNode(node);
		bean.setCustoms(customs);
		bean.setClobs(clobs);
		bean.setImages(images);
		bean.setFiles(files);

		if (detail != null && StringUtils.isNotBlank(detail.getSmallImage())) {
			bean.setWithImage(true);
		}
		Workflow workflow = null;
		if (Info.DRAFT.equals(status) || Info.CONTRIBUTION.equals(status)
				|| Info.COLLECTED.equals(status)) {
			// 草稿、投稿、采集
			bean.setStatus(status);
		} else {
			workflow = node.getWorkflow();
			if (workflow != null) {
				bean.setStatus(Info.AUDITING);
			} else {
				bean.setStatus(Info.NORMAL);
			}
		}

		bean.applyDefaultValue();
		bean = dao.save(bean);
		infoDetailService.save(detail, bean);
		// 将InfoBuffer对象一并保存，以免在网页浏览时再保存，导致并发保存报错
		infoBufferService.save(new InfoBuffer(), bean);
		infoAttrService.save(bean, attrIds, attrImages);
		infoNodeService.save(bean, nodeIds, nodeId);
		infoTagService.save(bean, tagNames);
		infoSpecialService.save(bean, specialIds);
		infoMemberGroupService.update(bean, viewGroupIds);
		infoOrgService.update(bean, viewOrgIds);

		if (workflow != null) {
			String stepName = workflowService.pass(workflow, creator, creator,
					new InfoProcess(), Info.WORKFLOW_TYPE, bean.getId(), null);
			if (StringUtils.isNotBlank(stepName)) {
				// 审核中
				bean.setStatus(Info.AUDITING);
				detail.setStepName(stepName);
			} else if ("".equals(stepName)) {
				// 终审通过
				bean.setStatus(Info.NORMAL);
				detail.setStepName(null);
			}
		}
		firePostSave(bean);
		return bean;
	}

	public Info update(Info bean, InfoDetail detail, Integer[] nodeIds,
			Integer[] specialIds, Integer[] viewGroupIds, Integer[] viewOrgIds,
			Map<String, String> customs, Map<String, String> clobs,
			List<InfoImage> images, List<InfoFile> files, Integer[] attrIds,
			Map<String, String> attrImages, String[] tagNames, Integer nodeId,
			User operator, boolean pass) {
		if (detail != null && StringUtils.isNotBlank(detail.getSmallImage())) {
			bean.setWithImage(true);
		}
		// 更新并审核
		if (pass) {
			String status = bean.getStatus();
			// 审核中、草稿、投稿、采集、退稿可审核。
			if (Info.AUDITING.equals(status) || Info.DRAFT.equals(status)
					|| Info.CONTRIBUTION.equals(status)
					|| Info.COLLECTED.equals(status)
					|| Info.REJECTION.equals(status)) {
				Workflow workflow = bean.getNode().getWorkflow();
				User owner = bean.getCreator();
				String stepName = workflowService.pass(workflow, owner,
						operator, new InfoProcess(), Info.WORKFLOW_TYPE,
						bean.getId(), null);
				if (StringUtils.isNotBlank(stepName)) {
					// 审核中
					bean.setStatus(Info.AUDITING);
					detail.setStepName(stepName);
				} else if ("".equals(stepName)) {
					// 终审通过
					bean.setStatus(Info.NORMAL);
					detail.setStepName(null);
				}
			}
		}

		bean.applyDefaultValue();
		bean = dao.save(bean);
		if (nodeId != null) {
			nodeService.derefer(bean.getNode());
			bean.setNode(nodeService.refer(nodeId));
		}
		bean.getCustoms().clear();
		if (!CollectionUtils.isEmpty(customs)) {
			bean.getCustoms().putAll(customs);
		}
		bean.getClobs().clear();
		if (!CollectionUtils.isEmpty(clobs)) {
			bean.getClobs().putAll(clobs);
		}
		bean.getImages().clear();
		if (!CollectionUtils.isEmpty(images)) {
			bean.getImages().addAll(images);
		}
		bean.getFiles().clear();
		if (!CollectionUtils.isEmpty(files)) {
			bean.getFiles().addAll(files);
		}

		infoDetailService.update(detail, bean);
		infoAttrService.update(bean, attrIds, attrImages);
		infoNodeService.update(bean, nodeIds, nodeId);
		infoTagService.update(bean, tagNames);
		infoSpecialService.update(bean, specialIds);
		infoMemberGroupService.update(bean, viewGroupIds);
		infoOrgService.update(bean, viewOrgIds);
		firePostUpdate(bean);
		return bean;
	}

	public List<Info> pass(Integer[] ids, Integer userId, String opinion) {
		Info info;
		InfoDetail detail;
		Workflow workflow;
		User owner;
		User operator = userService.get(userId);
		List<Info> infos = new ArrayList<Info>();
		for (Integer id : ids) {
			info = dao.findOne(id);
			detail = info.getDetail();
			String status = info.getStatus();
			// 审核中、草稿、投稿、采集、退稿可审核。
			if (Info.AUDITING.equals(status) || Info.DRAFT.equals(status)
					|| Info.CONTRIBUTION.equals(status)
					|| Info.COLLECTED.equals(status)
					|| Info.REJECTION.equals(status)) {
				workflow = info.getNode().getWorkflow();
				owner = info.getCreator();
				String stepName = workflowService.pass(workflow, owner,
						operator, new InfoProcess(), Info.WORKFLOW_TYPE,
						info.getId(), null);
				if (StringUtils.isNotBlank(stepName)) {
					// 审核中
					info.setStatus(Info.AUDITING);
					detail.setStepName(stepName);
				} else if ("".equals(stepName)) {
					// 终审通过
					info.setStatus(Info.NORMAL);
					detail.setStepName(null);
				}
			}
			infos.add(info);
		}
		firePostPass(infos);
		return infos;
	}

	public List<Info> reject(Integer[] ids, Integer userId, String opinion,
			boolean rejectEnd) {
		Info info;
		InfoDetail detail;
		Workflow workflow;
		User owner;
		User operator = userService.get(userId);
		List<Info> infos = new ArrayList<Info>();
		for (Integer id : ids) {
			info = dao.findOne(id);
			detail = info.getDetail();
			String status = info.getStatus();
			// 审核中、已终审可审核退回。
			if (Info.AUDITING.equals(status) || Info.NORMAL.equals(status)) {
				workflow = info.getNode().getWorkflow();
				owner = info.getCreator();
				String stepName = workflowService.reject(workflow, owner,
						operator, new InfoProcess(), Info.WORKFLOW_TYPE,
						info.getId(), opinion, rejectEnd);
				if (StringUtils.isNotBlank(stepName)) {
					// 审核中
					info.setStatus(Info.AUDITING);
					detail.setStepName(stepName);
				} else if ("".equals(stepName)) {
					// 退稿
					info.setStatus(Info.REJECTION);
					detail.setStepName(null);
				}
			}
			infos.add(info);
		}
		firePostReject(infos);
		return infos;
	}

	private Info doDelete(Integer id) {
		Info entity = dao.findOne(id);
		if (entity != null) {
			dao.delete(entity);
		}
		return entity;
	}

	public Info delete(Integer id) {
		firePreDelete(new Integer[] { id });
		Info bean = doDelete(id);
		commentService.deleteByFtypeAndFid(Info.COMMENT_TYPE, id);
		if (bean != null) {
			List<Info> beans = new ArrayList<Info>();
			beans.add(bean);
			firePostDelete(beans);
		}
		return bean;
	}

	public List<Info> delete(Integer[] ids) {
		firePreDelete(ids);
		List<Info> list = new ArrayList<Info>(ids.length);
		Info bean;
		for (int i = 0; i < ids.length; i++) {
			bean = delete(ids[i]);
			if (bean != null) {
				list.add(bean);
			}
		}
		firePostDelete(list);
		return list;
	}

	public void preUserDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByUserId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("info.management");
			}
		}
	}

	public void preNodeDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByNodeId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("info.management");
			}
		}
	}

	public void preOrgDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByOrgId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("info.management");
			}
		}
	}

	public void preSiteDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countBySiteId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("info.management");
			}
		}
	}

	private void firePostSave(Info bean) {
		if (!CollectionUtils.isEmpty(listeners)) {
			for (InfoListener listener : listeners) {
				listener.postInfoSave(bean);
			}
		}
	}

	private void firePostUpdate(Info bean) {
		if (!CollectionUtils.isEmpty(listeners)) {
			for (InfoListener listener : listeners) {
				listener.postInfoUpdate(bean);
			}
		}
	}

	private void firePostPass(List<Info> beans) {
		if (!CollectionUtils.isEmpty(listeners)) {
			for (InfoListener listener : listeners) {
				listener.postInfoPass(beans);
			}
		}
	}

	private void firePostReject(List<Info> beans) {
		if (!CollectionUtils.isEmpty(listeners)) {
			for (InfoListener listener : listeners) {
				listener.postInfoReject(beans);
			}
		}
	}

	private void firePostDelete(List<Info> beans) {
		if (!CollectionUtils.isEmpty(listeners)) {
			for (InfoListener listener : listeners) {
				listener.postInfoDelete(beans);
			}
		}
	}

	private void firePreDelete(Integer[] ids) {
		if (!CollectionUtils.isEmpty(deleteListeners)) {
			for (InfoDeleteListener listener : deleteListeners) {
				listener.preInfoDelete(ids);
			}
		}
	}

	private List<InfoListener> listeners;
	private List<InfoDeleteListener> deleteListeners;

	@Autowired(required = false)
	public void setListeners(List<InfoListener> listeners) {
		this.listeners = listeners;
	}

	@Autowired(required = false)
	public void setDeleteListeners(List<InfoDeleteListener> deleteListeners) {
		this.deleteListeners = deleteListeners;
	}

	private InfoOrgService infoOrgService;
	private InfoMemberGroupService infoMemberGroupService;
	private WorkflowService workflowService;
	private InfoAttributeService infoAttrService;
	private InfoTagService infoTagService;
	private InfoSpecialService infoSpecialService;
	private InfoNodeService infoNodeService;
	private InfoDetailService infoDetailService;
	private InfoBufferService infoBufferService;
	private NodeService nodeService;
	private UserService userService;
	private SiteService siteService;

	@Autowired
	public void setInfoOrgService(InfoOrgService infoOrgService) {
		this.infoOrgService = infoOrgService;
	}

	@Autowired
	public void setInfoMemberGroupService(
			InfoMemberGroupService infoMemberGroupService) {
		this.infoMemberGroupService = infoMemberGroupService;
	}

	@Autowired
	public void setWorkflowService(WorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	@Autowired
	public void setInfoAttrService(InfoAttributeService infoAttrService) {
		this.infoAttrService = infoAttrService;
	}

	@Autowired
	public void setInfoTagService(InfoTagService infoTagService) {
		this.infoTagService = infoTagService;
	}

	@Autowired
	public void setInfoSpecialService(InfoSpecialService infoSpecialService) {
		this.infoSpecialService = infoSpecialService;
	}

	@Autowired
	public void setInfoNodeService(InfoNodeService infoNodeService) {
		this.infoNodeService = infoNodeService;
	}

	@Autowired
	public void setInfoDetailService(InfoDetailService infoDetailService) {
		this.infoDetailService = infoDetailService;
	}

	@Autowired
	public void setInfoBufferService(InfoBufferService infoBufferService) {
		this.infoBufferService = infoBufferService;
	}

	@Autowired
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	private CommentService commentService;

	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	private InfoDao dao;

	@Autowired
	public void setDao(InfoDao dao) {
		this.dao = dao;
	}
}
