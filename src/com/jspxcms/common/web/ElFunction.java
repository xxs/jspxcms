package com.jspxcms.common.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * el调用方法
 * 
 * @author liufang
 * 
 */
public abstract class ElFunction {
	/**
	 * 当前时间
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	public static boolean contains(Collection<?> container, Object element) {
		if (container == null || element == null) {
			return false;
		}
		return container.contains(element);
	}

	public static Object invoke(Object obj, String method)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (obj == null || StringUtils.isBlank(method)) {
			return null;
		}
		Class<?> clazz = obj.getClass();
		return clazz.getMethod(method).invoke(obj);
	}

	public static Object invoke(Object obj, String methodName, Object arg0)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (obj == null || StringUtils.isBlank(methodName)) {
			return null;
		}
		Class<?> clazz = obj.getClass();
		Method method;
		if (arg0 != null) {
			method = clazz.getMethod(methodName, arg0.getClass());
		} else {
			for (Method m : clazz.getDeclaredMethods()) {
				if (m.getName().equals(methodName)
						&& m.getParameterTypes().length == 1) {
					method = m;
				}
			}
			throw new NoSuchMethodException(clazz.getName() + "." + methodName
					+ "(Object arg0)");
		}
		return method.invoke(obj, arg0);
	}

	public static Object invoke(Object obj, String methodName, Object arg0,
			Object arg1) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (obj == null || StringUtils.isBlank(methodName)) {
			return null;
		}
		Class<?> clazz = obj.getClass();
		Method method;
		if (arg0 != null && arg1 != null) {
			method = clazz.getMethod(methodName, arg0.getClass(),
					arg1.getClass());
		} else {
			for (Method m : clazz.getDeclaredMethods()) {
				if (m.getName().equals(methodName)
						&& m.getParameterTypes().length == 2) {
					method = m;
				}
			}
			throw new NoSuchMethodException(clazz.getName() + "." + methodName
					+ "(Object arg0,Object arg1)");
		}
		return method.invoke(obj, arg0, arg1);
	}

	public static Object invoke(Object obj, String methodName, Object arg0,
			Object arg1, Object arg2) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (obj == null || StringUtils.isBlank(methodName)) {
			return null;
		}
		Class<?> clazz = obj.getClass();
		Method method;
		if (arg0 != null && arg1 != null && arg2 != null) {
			method = clazz.getMethod(methodName, arg0.getClass(),
					arg1.getClass(), arg2.getClass());
		} else {
			for (Method m : clazz.getDeclaredMethods()) {
				if (m.getName().equals(methodName)
						&& m.getParameterTypes().length == 3) {
					method = m;
				}
			}
			throw new NoSuchMethodException(clazz.getName() + "." + methodName
					+ "(Object arg0,Object arg1,Object arg2)");
		}
		return method.invoke(obj, arg0, arg1, arg2);
	}

	public static Object invoke(Object obj, String methodName, Object arg0,
			Object arg1, Object arg2, Object arg3) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (obj == null || StringUtils.isBlank(methodName)) {
			return null;
		}
		Class<?> clazz = obj.getClass();
		Method method;
		if (arg0 != null && arg1 != null && arg2 != null) {
			method = clazz.getMethod(methodName, arg0.getClass(),
					arg1.getClass(), arg2.getClass(), arg3.getClass());
		} else {
			for (Method m : clazz.getDeclaredMethods()) {
				if (m.getName().equals(methodName)
						&& m.getParameterTypes().length == 4) {
					method = m;
				}
			}
			throw new NoSuchMethodException(clazz.getName() + "." + methodName
					+ "(Object arg0,Object arg1,Object arg2,Object arg3)");
		}
		return method.invoke(obj, arg0, arg1, arg2, arg3);
	}
}
