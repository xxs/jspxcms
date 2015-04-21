package com.jspxcms.core.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Menu implements Comparable<Menu> {
	private String id;
	private String name;
	private String url;
	private String perms;
	private List<Function> functions;

	private int seq = Integer.MAX_VALUE;
	private String parentId;

	private String leftUrl;
	private String centerUrl;
	private String perm;

	private Menu parent;
	private Set<Menu> children;

	public static Set<Menu> assemble(Map<String, String> map) {
		Set<Menu> menus = new HashSet<Menu>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			menus.add(parse(entry.getKey(), entry.getValue()));
		}
		return sort(menus);
	}

	public static Set<Menu> sort(Set<Menu> menus) {
		Set<Menu> set = new TreeSet<Menu>();
		for (Menu menu : menus) {
			String parentId = menu.getParentId();
			if (StringUtils.isNotBlank(parentId)) {
				for (Menu parent : menus) {
					if (parentId.equals(parent.getId())) {
						parent.addChild(menu);
						menu.setParent(parent);
						String centerUrl = menu.getCenterUrl();
						if (StringUtils.isBlank(centerUrl)) {
							menu.setCenterUrl(menu.getLeftUrl());
							String leftUrl = parent.getLeftUrl();
							if (leftUrl.indexOf("?") != -1) {
								leftUrl += "&subId=";
							} else {
								leftUrl += "?subId=";
							}
							menu.setLeftUrl(leftUrl + menu.getId());
						}
						break;
					}
				}
			} else {
				set.add(menu);
			}
		}
		// 设置导航栏的centerUrl。
		for (Menu menu : set) {
			if (StringUtils.isBlank(menu.getCenterUrl())) {
				Set<Menu> children = menu.getChildren();
				if (CollectionUtils.isNotEmpty(children)) {
					menu.setCenterUrl(children.iterator().next().getUrl());
				}
			}
		}
		return set;
	}

	public static Menu parse(String id, String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		String[] attrs = StringUtils.split(str, ';');
		String name = null, url = null, perms = null;
		String[] funcArr = null;
		List<Function> functions = null;
		int len = attrs.length;
		if (len > 0) {
			name = attrs[0];
		}
		if (len > 1) {
			url = attrs[1];
		}
		if (len > 2) {
			perms = attrs[2];
		}
		if (len > 3) {
			funcArr = ArrayUtils.subarray(attrs, 3, len);
			functions = new ArrayList<Function>(funcArr.length);
			for (String func : funcArr) {
				String[] pair = StringUtils.split(func, '@');
				String n = pair[0];
				String p = null;
				if (pair.length > 1) {
					p = pair[1];
				}
				functions.add(new Function(n, p));
			}
		}
		return new Menu(id, name, url, perms, functions);
	}

	public Menu(String id, String name, String url, String perms,
			List<Function> functions) {
		this.id = id;
		String[] seqArr = StringUtils.split(id, '.');
		this.seq = NumberUtils.toInt(seqArr[seqArr.length - 1],
				Integer.MAX_VALUE);
		if (id.indexOf('.') != -1) {
			this.parentId = id.substring(0, id.lastIndexOf('.'));
		}
		this.name = name;
		this.url = url;
		this.perms = perms;
		if (StringUtils.isNotBlank(url)) {
			String[] urlArr = StringUtils.split(url, ',');
			leftUrl = urlArr[0];
			if (urlArr.length > 1) {
				centerUrl = urlArr[1];
			}
		}
		if (StringUtils.isNotBlank(perms)) {
			String[] permArr = StringUtils.split(perms, ',');
			perm = permArr[0];
		}
		this.functions = functions;
	}

	public int compareTo(Menu o) {
		return this.getSeq() - o.getSeq();
	}

	public void addChild(Menu menu) {
		if (children == null) {
			children = new TreeSet<Menu>();
		}
		children.add(menu);
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Set<Menu> getChildren() {
		if (children == null) {
			children = new TreeSet<Menu>();
		}
		return children;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getPerms() {
		return perms;
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public String getParentId() {
		return parentId;
	}

	public String getLeftUrl() {
		return leftUrl;
	}

	public void setLeftUrl(String leftUrl) {
		this.leftUrl = leftUrl;
	}

	public String getCenterUrl() {
		return centerUrl;
	}

	public void setCenterUrl(String centerUrl) {
		this.centerUrl = centerUrl;
	}

	public String getPerm() {
		return perm;
	}

	public static class Function {
		private String name;
		private String perms;

		private String perm;

		public Function(String name, String perms) {
			this.name = name;
			this.perms = perms;
			if (StringUtils.isNotBlank(perms)) {
				this.perm = StringUtils.split(perms, ',')[0];
			}
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPerms() {
			return perms;
		}

		public void setPerms(String perms) {
			this.perms = perms;
		}

		public String getPerm() {
			return perm;
		}

		public void setPerm(String perm) {
			this.perm = perm;
		}
	}

}
