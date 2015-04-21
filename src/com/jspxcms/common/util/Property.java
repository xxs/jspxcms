package com.jspxcms.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Spring properties工具类
 * 
 * @author liufang
 * 
 */
public class Property implements BeanFactoryAware {
	private BeanFactory beanFactory;
	private Properties properties;

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * 获得property list
	 * 
	 * @param prefix
	 *            前缀
	 * @return
	 */
	public List<String> getList(String prefix) {
		if (properties == null || prefix == null) {
			return Collections.emptyList();
		}
		List<String> list = new ArrayList<String>();
		Enumeration<?> en = properties.propertyNames();
		String key;
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			if (key.startsWith(prefix)) {
				list.add(properties.getProperty(key));
			}
		}
		return list;
	}

	/**
	 * 获取有序的列表
	 * 
	 * eg:List<String> types = getSortedList("modelType.");
	 * 
	 * <ul>
	 * <li>modelType.100=info
	 * <li>modelType.200=node
	 * <li>modelType.300=node_home
	 * </ul>
	 * 
	 * @param prefix
	 * @return
	 */
	public List<String> getSortedList(String prefix) {
		if (properties == null || prefix == null) {
			return Collections.emptyList();
		}
		Map<Integer, String> map = new TreeMap<Integer, String>();
		Enumeration<?> en = properties.propertyNames();
		String key;
		int len = prefix.length();
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			if (key.startsWith(prefix)) {
				map.put(Integer.parseInt(key.substring(len)),
						properties.getProperty(key));
			}
		}
		List<String> list = new ArrayList<String>(map.values());
		return list;
	}

	public Map<String, String> getMap(String prefix) {
		if (properties == null || prefix == null) {
			return Collections.emptyMap();
		}
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<?> en = properties.propertyNames();
		String key;
		int len = prefix.length();
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			if (key.startsWith(prefix)) {
				map.put(key.substring(len), properties.getProperty(key));
			}
		}
		return map;
	}

	public Properties getProperties(String prefix) {
		Properties props = new Properties();
		if (properties == null || prefix == null) {
			return props;
		}
		Enumeration<?> en = properties.propertyNames();
		String key;
		int len = prefix.length();
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			if (key.startsWith(prefix)) {
				props.put(key.substring(len), properties.getProperty(key));
			}
		}
		return props;
	}

	public <T> Map<String, T> getBeanMap(String prefix, Class<T> requiredType) {
		Map<String, String> nameMap = getMap(prefix);
		if (nameMap.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, T> objectMap = new HashMap<String, T>(nameMap.size());
		String key, value;
		for (Map.Entry<String, String> entry : nameMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			objectMap.put(key, beanFactory.getBean(value, requiredType));
		}
		return objectMap;
	}

	public Map<String, Object> getBeanMap(String prefix) {
		return getBeanMap(prefix, Object.class);
	}

	public <T> Map<String, T> getBeanMap(String prefix, String className)
			throws ClassNotFoundException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) Class.forName(className);
		return getBeanMap(prefix, clazz);
	}

	public <T> List<T> getBeanList(String prefix, Class<T> requiredType) {
		List<String> nameList = getList(prefix);
		if (nameList.isEmpty()) {
			return Collections.emptyList();
		}
		List<T> objectList = new ArrayList<T>(nameList.size());
		for (String name : nameList) {
			objectList.add(beanFactory.getBean(name, requiredType));
		}
		return objectList;
	}

	public List<Object> getBeanList(String prefix) {
		return getBeanList(prefix, Object.class);
	}

	public <T> List<T> getBeanList(String prefix, String className)
			throws ClassNotFoundException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) Class.forName(className);
		return getBeanList(prefix, clazz);
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
