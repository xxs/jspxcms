package com.jspxcms.common.web;

import java.beans.PropertyEditorSupport;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;

/**
 * 分页参数获取类
 * 
 * @author liufang
 * 
 */
public class PageableArgumentResolver implements HandlerMethodArgumentResolver {

	private static final Pageable DEFAULT_PAGE_REQUEST = new PageRequest(0, 15);
	private static final String DEFAULT_PREFIX = "page";
	private static final String DEFAULT_SEPARATOR = "_";
	private static final String DEFAULT_COOKIE_NAME = "page_size";

	private Pageable fallbackPagable = DEFAULT_PAGE_REQUEST;
	private String prefix = DEFAULT_PREFIX;
	private String separator = DEFAULT_SEPARATOR;
	private String cookieName = DEFAULT_COOKIE_NAME;

	/**
	 * Setter to configure a fallback instance of {@link Pageable} that is being
	 * used to back missing parameters. Defaults to
	 * {@value #DEFAULT_PAGE_REQUEST}.
	 * 
	 * @param fallbackPagable
	 *            the fallbackPagable to set
	 */
	public void setFallbackPagable(Pageable fallbackPagable) {
		this.fallbackPagable = null == fallbackPagable ? DEFAULT_PAGE_REQUEST
				: fallbackPagable;
	}

	/**
	 * Setter to configure the prefix of request parameters to be used to
	 * retrieve paging information. Defaults to {@link #DEFAULT_PREFIX}.
	 * 
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = null == prefix ? DEFAULT_PREFIX : prefix;
	}

	/**
	 * Setter to configure the separator between prefix and actual property
	 * value. Defaults to {@link #DEFAULT_SEPARATOR}.
	 * 
	 * @param separator
	 *            the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = null == separator ? DEFAULT_SEPARATOR : separator;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(Pageable.class);
	}

	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {

		assertPageableUniqueness(parameter);

		HttpServletRequest servletRequest = (HttpServletRequest) webRequest
				.getNativeRequest();

		Pageable request = getDefaultFromCookieOrAnnotationOrFallback(
				parameter, servletRequest);

		String prefix = getPrefix(parameter);
		Map<String, Object> map = WebUtils.getParametersStartingWith(
				servletRequest, prefix + separator);
		String pageStr = servletRequest.getParameter(prefix);
		if (StringUtils.isNotBlank(pageStr)) {
			int page = NumberUtils.toInt(pageStr, 0);
			if (page < 0) {
				page = 0;
			}
			map.put("page", page);
		}
		PropertyValues propertyValues = new MutablePropertyValues(map);
		// PropertyValues propertyValues = new
		// ServletRequestParameterPropertyValues(
		// servletRequest, prefix, separator);

		DataBinder binder = new ServletRequestDataBinder(request);

		binder.initDirectFieldAccess();
		binder.registerCustomEditor(Sort.class, new SortPropertyEditor(
				"sort_dir", propertyValues));
		binder.bind(propertyValues);

		if (request.getPageNumber() > 0) {
			request = new PageRequest(request.getPageNumber() - 1,
					request.getPageSize(), request.getSort());
		}

		return request;
	}

	private Pageable getDefaultFromCookieOrAnnotationOrFallback(
			MethodParameter methodParameter, HttpServletRequest servletRequest) {
		Cookie cookie = WebUtils.getCookie(servletRequest, cookieName);
		Integer pageSize = null;
		if (cookie != null) {
			String value = cookie.getValue();
			int ps = NumberUtils.toInt(value, 0);
			if (ps > 0) {
				pageSize = ps;
			}
		}
		// search for PageableDefaults annotation
		for (Annotation annotation : methodParameter.getParameterAnnotations()) {
			if (annotation instanceof PageableDefaults) {
				return getDefaultPageRequestFrom((PageableDefaults) annotation,
						pageSize);
			}
		}

		// Construct request with fallback request to ensure sensible
		// default values. Create fresh copy as Spring will manipulate the
		// instance under the covers
		if (pageSize == null) {
			pageSize = fallbackPagable.getPageSize();
		}
		return new PageRequest(fallbackPagable.getPageNumber(), pageSize,
				fallbackPagable.getSort());
	}

	private static Pageable getDefaultPageRequestFrom(
			PageableDefaults defaults, Integer pageSize) {

		// +1 is because we substract 1 later
		int defaultPageNumber = defaults.pageNumber() + 1;
		if (pageSize == null) {
			pageSize = defaults.value();
		}

		if (defaults.sort().length == 0) {
			return new PageRequest(defaultPageNumber, pageSize);
		}

		return new PageRequest(defaultPageNumber, pageSize, defaults.sortDir(),
				defaults.sort());
	}

	/**
	 * Resolves the prefix to use to bind properties from. Will prepend a
	 * possible {@link Qualifier} if available or return the configured prefix
	 * otherwise.
	 * 
	 * @param parameter
	 * @return
	 */
	private String getPrefix(MethodParameter parameter) {

		for (Annotation annotation : parameter.getParameterAnnotations()) {
			if (annotation instanceof Qualifier) {
				return new StringBuilder(((Qualifier) annotation).value())
						.append("_").append(prefix).toString();
			}
		}

		return prefix;
	}

	/**
	 * Asserts uniqueness of all {@link Pageable} parameters of the method of
	 * the given {@link MethodParameter}.
	 * 
	 * @param parameter
	 */
	private void assertPageableUniqueness(MethodParameter parameter) {

		Method method = parameter.getMethod();

		if (containsMoreThanOnePageableParameter(method)) {
			Annotation[][] annotations = method.getParameterAnnotations();
			assertQualifiersFor(method.getParameterTypes(), annotations);
		}
	}

	/**
	 * Returns whether the given {@link Method} has more than one
	 * {@link Pageable} parameter.
	 * 
	 * @param method
	 * @return
	 */
	private boolean containsMoreThanOnePageableParameter(Method method) {

		boolean pageableFound = false;

		for (Class<?> type : method.getParameterTypes()) {

			if (pageableFound && type.equals(Pageable.class)) {
				return true;
			}

			if (type.equals(Pageable.class)) {
				pageableFound = true;
			}
		}

		return false;
	}

	/**
	 * Asserts that every {@link Pageable} parameter of the given parameters
	 * carries an {@link Qualifier} annotation to distinguish them from each
	 * other.
	 * 
	 * @param parameterTypes
	 * @param annotations
	 */
	private void assertQualifiersFor(Class<?>[] parameterTypes,
			Annotation[][] annotations) {

		Set<String> values = new HashSet<String>();

		for (int i = 0; i < annotations.length; i++) {

			if (Pageable.class.equals(parameterTypes[i])) {

				Qualifier qualifier = findAnnotation(annotations[i]);

				if (null == qualifier) {
					throw new IllegalStateException(
							"Ambiguous Pageable arguments in handler method. If you use multiple parameters of type Pageable you need to qualify them with @Qualifier");
				}

				if (values.contains(qualifier.value())) {
					throw new IllegalStateException(
							"Values of the user Qualifiers must be unique!");
				}

				values.add(qualifier.value());
			}
		}
	}

	/**
	 * Returns a {@link Qualifier} annotation from the given array of
	 * {@link Annotation}s. Returns {@literal null} if the array does not
	 * contain a {@link Qualifier} annotation.
	 * 
	 * @param annotations
	 * @return
	 */
	private Qualifier findAnnotation(Annotation[] annotations) {

		for (Annotation annotation : annotations) {
			if (annotation instanceof Qualifier) {
				return (Qualifier) annotation;
			}
		}

		return null;
	}

	/**
	 * {@link java.beans.PropertyEditor} to create {@link Sort} instances from
	 * textual representations. The implementation interprets the string as a
	 * comma separated list where the first entry is the sort direction (
	 * {@code asc}, {@code desc}) followed by the properties to sort by.
	 * 
	 * @author Oliver Gierke
	 */
	private static class SortPropertyEditor extends PropertyEditorSupport {

		private final String orderProperty;
		private final PropertyValues values;

		/**
		 * Creates a new {@link SortPropertyEditor}.
		 * 
		 * @param orderProperty
		 * @param values
		 */
		public SortPropertyEditor(String orderProperty, PropertyValues values) {

			this.orderProperty = orderProperty;
			this.values = values;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
		 */
		@Override
		public void setAsText(String text) {

			PropertyValue rawOrder = values.getPropertyValue(orderProperty);
			Direction order = null == rawOrder ? Direction.ASC : Direction
					.fromString(rawOrder.getValue().toString());

			setValue(new Sort(order, text));
		}
	}

}
