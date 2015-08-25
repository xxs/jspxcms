package com.jspxcms.core.service;

import com.jspxcms.core.domain.Brand;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeBrand;

public interface NodeBrandService {
	
	
	public NodeBrand save(Node node, Brand brand);
	
	public NodeBrand save(Node node, Integer[] brandIds);

	public void update(Brand brand, Integer[] nodePermIds);

	public void update(Node node, Integer[] brandIds);
}
