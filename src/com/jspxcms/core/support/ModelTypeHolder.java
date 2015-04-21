package com.jspxcms.core.support;

import java.util.List;
import java.util.Map;

public class ModelTypeHolder {
	private List<String> types;
	private Map<String, String> paths;

	public Map<String, String> getPaths() {
		return paths;
	}

	public void setPaths(Map<String, String> paths) {
		this.paths = paths;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

}
