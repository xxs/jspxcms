package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoDetail;
import com.jspxcms.core.domain.InfoFile;
import com.jspxcms.core.domain.InfoImage;
import com.jspxcms.core.domain.User;

/**
 * InfoService
 * 
 * @author liufang
 * 
 */
public interface InfoService {
	public Info save(Info bean, InfoDetail detail, Integer[] nodeIds,
			Integer[] specialIds, Integer[] viewGroupIds, Integer[] viewOrgIds,
			Map<String, String> customs, Map<String, String> clobs,
			List<InfoImage> images, List<InfoFile> files, Integer[] attrIds,
			Map<String, String> attrImages, String[] tagNames, Integer nodeId,
			Integer creatorId, String status, Integer siteId);

	public Info update(Info bean, InfoDetail detail, Integer[] nodeIds,
			Integer[] specialIds, Integer[] viewGroupIds, Integer[] viewOrgIds,
			Map<String, String> customs, Map<String, String> clobs,
			List<InfoImage> images, List<InfoFile> files, Integer[] attrIds,
			Map<String, String> attrImages, String[] tagNames, Integer nodeId,
			User operator, boolean pass);

	public List<Info> pass(Integer[] ids, Integer userId, String opinion);

	public List<Info> reject(Integer[] ids, Integer userId, String opinion,
			boolean rejectEnd);

	public Info delete(Integer id);

	public List<Info> delete(Integer[] ids);
}
