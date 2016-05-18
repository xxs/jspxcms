package com.jspxcms.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.common.util.RowSide;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.core.domain.Global;
import com.jspxcms.core.domain.Model;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Role;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.listener.OrgDeleteListener;
import com.jspxcms.core.listener.SiteDeleteListener;
import com.jspxcms.core.repository.SiteDao;
import com.jspxcms.core.service.GlobalService;
import com.jspxcms.core.service.ModelService;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.service.NodeService;
import com.jspxcms.core.service.OrgService;
import com.jspxcms.core.service.RoleService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.UserRoleService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.Configurable;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.DeleteException;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Theme;

/**
 * SiteServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class SiteServiceImpl implements SiteService, OrgDeleteListener {
	private static final Logger logger = LoggerFactory
			.getLogger(SiteServiceImpl.class);

	public List<Site> findList(Map<String, String[]> params, Sort sort) {
		return dao.findAll(spec(params), sort);
	}

	public RowSide<Site> findSide(Map<String, String[]> params, Site bean,
			Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<Site>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<Site> list = dao.findAll(spec(params), limit);
		return RowSide.create(list, bean);
	}

	private Specification<Site> spec(Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		Specification<Site> sp = SearchFilter.spec(filters, Site.class);
		return sp;
	}

	public List<Site> findList() {
		return dao.findByStatus(null);
	}

	public List<Site> findList(Integer[] status) {
		return dao.findByStatus(status);
	}

	public List<Site> findByUserId(Integer userId) {
		return dao.findByUserId(userId);
	}

	public Site findDefault() {
		Site site = dao.findDefault();
		return site;
	}

	public Site findByDomain(String domain) {
		List<Site> list = dao.findByDomain(domain);
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public Site findByNumber(String number) {
		List<Site> list = dao.findByNumber(number);
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public boolean numberExist(String number) {
		return dao.countByNumber(number) > 0;
	}

	public Site get(Integer id) {
		Site entity = dao.findOne(id);
		return entity;
	}

	@Transactional
	public Site save(Site bean, Integer parentId, Integer orgId,
			Integer userId, Site srcSite) {
		Site parent = null;
		if (parentId != null) {
			parent = dao.findOne(parentId);
			bean.setParent(parent);
			parent.addChild(bean);
		}
		bean.setOrg(orgService.get(orgId));
		bean.setGlobal(globalService.findUnique());
		bean.applyDefaultValue();
		treeSave(bean, parent);
		bean = dao.save(bean);

		// 必要的数据
		Role role = new Role();
		role.setName("administrators");
		role.setAllPerm(true);
		role.setAllInfoPerm(true);
		role.setAllNodePerm(true);
		role.setInfoFinalPerm(true);
		roleService.save(role, null, null, bean.getId());

		User user = userService.get(userId);
		userRoleService.save(user, role, 0);

		Integer srcSiteId = srcSite.getId();
//		// 复制站点模型
//		Model siteModel = modelService.findDefault(srcSiteId, Site.MODEL_TYPE);
//		if (siteModel != null) {
//			modelService.clone(siteModel, bean.getId());
//		}
//		// 复制首页模型
//		Model homeModel = modelService.findDefault(srcSiteId,
//				Node.HOME_MODEL_TYPE);
//		if (homeModel != null) {
//			modelService.clone(homeModel, bean.getId());
//		}
//		// 复制节点模型
//		Model nodeModel = modelService.findDefault(srcSiteId,
//				Node.NODE_MODEL_TYPE);
//		if (nodeModel != null) {
//			modelService.clone(nodeModel, bean.getId());
//		}
//		// 复制信息模型
//		Model infoModel = modelService.findDefault(srcSiteId, Info.MODEL_TYPE);
//		if (infoModel != null) {
//			modelService.clone(infoModel, bean.getId());
//		}
		//此处修改为复制整站模型
		List<Model> siteModels = modelService.findAllModelBySiteId(srcSiteId);
		if(siteModels!=null && siteModels.size()>0){
			modelService.clone(siteModels, bean.getId());
		}
		//复制root（首页）栏目
		Node node = nodeQuery.findRoot(srcSiteId);
		Node parentNode = null;
		if (node != null) {
			parentNode = nodeService.clone(node, bean.getId(),userId);
		}
//		if(parentNode !=null){
//			List<Node> nodes = nodeQuery.findList(srcSiteId,null);
//			if(nodes!=null && nodes.size()>0){
//				nodeService.clone(nodes, bean.getId(),userId,parentNode.getId());
//			}
//		}
		

		// 复制模版
		String srcTemplate = Theme.THEME_TEMPLATE_PATH + "/" + bean.getTemplateTheme();
		String destTemplate = ForeContext.FILES_PATH + bean.getTemplate(null);
		File srcDir = new File(pathResolver.getPath(srcTemplate));
		File destDir = new File(pathResolver.getPath(destTemplate));
		try {
			destDir.mkdirs();
			FileUtils.copyDirectory(srcDir, destDir);
		} catch (IOException e) {
			logger.error("copy template error!", e);
		}
		return bean;
	}

	@Transactional
	private void treeSave(Site bean, Site parent) {
		bean.setTreeMax(Site.long2hex(0));
		if (parent == null) {
			String treeMax = dao.findMaxRootTreeNumber();
			long maxLong = Site.hex2long(treeMax);
			treeMax = Site.long2hex(maxLong + 1);
			bean.setTreeLevel(0);
			bean.setTreeNumber(treeMax);
			bean.setTreeMax(Site.long2hex(0));
		} else {
			bean.setTreeLevel(parent.getTreeLevel() + 1);
			String treeMax = parent.getTreeMax();
			bean.setTreeNumber(parent.getTreeNumber() + "-" + treeMax);
			long big = parent.getTreeMaxLong() + 1;
			parent.setTreeMax(Site.long2hex(big));
			dao.save(parent);
		}
	}

	@Transactional
	public Site update(Site bean) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Site update(Site bean, Integer parentId, Integer orgId) {
		bean.setOrg(orgService.get(orgId));
		bean.applyDefaultValue();
		bean = dao.save(bean);

		Site parent = bean.getParent();
		if ((parent != null && !parent.getId().equals(parentId))
				|| (parent == null && parentId != null)) {
			move(new Integer[] { bean.getId() }, parentId);
		}

		return bean;
	}

	@Transactional
	public int move(Integer[] ids, Integer id) {
		int count = 0;
		String modifiedTreeNumber, treeNumber;
		if (id == null) {
			long treeMax = Site.hex2long(dao.findMaxRootTreeNumber()) + 1;
			for (int i = 0, len = ids.length; i < len; i++) {
				treeNumber = dao.findTreeNumber(ids[i]);
				modifiedTreeNumber = Site.long2hex(treeMax++);
				count += dao.updateTreeNumber(treeNumber + "%",
						modifiedTreeNumber, treeNumber.length() + 1);
				dao.updateParentId(ids[i], id);
			}
		} else {
			Site parent = dao.findOne(id);
			String parentTreeNumber = parent.getTreeNumber();
			long treeMax = parent.getTreeMaxLong();
			for (int i = 0, len = ids.length; i < len; i++) {
				dao.updateTreeMax(id, Site.long2hex(treeMax + 1));
				treeNumber = dao.findTreeNumber(ids[i]);
				modifiedTreeNumber = parentTreeNumber + "-"
						+ Site.long2hex(treeMax++);
				count += dao.updateTreeNumber(treeNumber + "%",
						modifiedTreeNumber, treeNumber.length() + 1);
				dao.updateParentId(ids[i], id);
			}
		}
		return count;
	}

	@Transactional
	public void updateConf(Site site, Configurable conf) {
		Map<String, String> customs = site.getCustoms();
		Global.removeAttr(customs, conf.getPrefix());
		customs.putAll(conf.getCustoms());
	}

	@Transactional
	public void updateCustoms(Site site, String prefix, Map<String, String> map) {
		Map<String, String> customs = site.getCustoms();
		Global.removeAttr(customs, prefix);
		customs.putAll(map);
	}

	private Site doDelete(Integer id) {
		Site entity = dao.findOne(id);
		if (entity != null) {
			dao.delete(entity);
		}
		return entity;
	}

	@Transactional
	public Site delete(Integer id) {
		firePreDelete(new Integer[] { id });
		return doDelete(id);
	}

	@Transactional
	public Site[] delete(Integer[] ids) {
		firePreDelete(ids);
		Site[] beans = new Site[ids.length];
		for (int i = 0, len = beans.length; i < len; i++) {
			beans[i] = doDelete(ids[i]);
		}
		return beans;
	}

	public void preOrgDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			if (dao.countByOrgId(Arrays.asList(ids)) > 0) {
				throw new DeleteException("site.management");
			}
		}
	}

	private void firePreDelete(Integer[] ids) {
		if (!CollectionUtils.isEmpty(deleteListeners)) {
			for (SiteDeleteListener listener : deleteListeners) {
				listener.preSiteDelete(ids);
			}
		}
	}

	private List<SiteDeleteListener> deleteListeners;

	@Autowired(required = false)
	public void setDeleteListeners(List<SiteDeleteListener> deleteListeners) {
		this.deleteListeners = deleteListeners;
	}

	private PathResolver pathResolver;
	private ModelService modelService;
	private NodeService nodeService;
	private NodeQueryService nodeQuery;
	private UserRoleService userRoleService;
	private RoleService roleService;
	private UserService userService;
	private GlobalService globalService;
	private OrgService orgService;

	@Autowired
	public void setPathResolver(PathResolver pathResolver) {
		this.pathResolver = pathResolver;
	}

	@Autowired
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	 @Autowired
	 public void setNodeService(NodeService nodeService) {
	 this.nodeService = nodeService;
	 }
	
	 @Autowired
	 public void setNodeQuery(NodeQueryService nodeQuery) {
	 this.nodeQuery = nodeQuery;
	 }

	@Autowired
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setGlobalService(GlobalService globalService) {
		this.globalService = globalService;
	}

	@Autowired
	public void setService(OrgService orgService) {
		this.orgService = orgService;
	}

	private SiteDao dao;

	@Autowired
	public void setSiteDao(SiteDao dao) {
		this.dao = dao;
	}
}
