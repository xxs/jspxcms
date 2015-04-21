package com.jspxcms.core.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeDetail;

/**
 * NodeService
 * 
 * @author liufang
 * 
 */
public interface NodeService {
	public Node save(Node bean, NodeDetail detail, Map<String, String> customs,
			Map<String, String> clobs, Integer[] infoPermIds,
			Integer[] nodePermIds, Integer[] viewGroupIds,
			Integer[] contriGroupIds, Integer[] commentGroupIds,
			Integer[] viewOrgIds, Integer parentId, Integer nodeModelId,
			Integer infoModelId, Integer workflowId, Integer creatorId,
			Integer siteId);

	public Node update(Node bean, NodeDetail detail,
			Map<String, String> customs, Map<String, String> clobs,
			Integer[] infoPermIds, Integer[] nodePermIds,
			Integer[] viewGroupIds, Integer[] contriGroupIds,
			Integer[] commentGroupIds, Integer[] viewOrgIds,
			Integer nodeModelId, Integer infoModelId, Integer workflowId);

	public Node[] batchUpdate(Integer[] id, String[] name, String[] number,
			Integer[] views, Boolean[] hidden, Integer siteId,
			boolean isUpdateTree);

	public int move(Integer[] ids, Integer id, Integer siteId);

	public Node delete(Integer id);

	public Node[] delete(Integer[] ids);

	/**
	 * 引用节点。节点信息数加一。
	 * 
	 * @param nodeId
	 * @return
	 */
	public Node refer(Integer nodeId);

	public List<Node> refer(Integer[] nodeIds);

	public void derefer(Node node);

	public void derefer(Collection<Node> nodes);
}
