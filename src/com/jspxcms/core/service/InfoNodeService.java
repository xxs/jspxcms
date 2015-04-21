package com.jspxcms.core.service;

import java.util.List;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoNode;

public interface InfoNodeService {
	public List<InfoNode> save(Info info, Integer[] nodeIds, Integer nodeId);

	public List<InfoNode> update(Info info, Integer[] nodeIds, Integer nodeId);

	public int deleteByNodeId(Integer nodeId);

	public int deleteByInfoId(Integer infoId);
}
