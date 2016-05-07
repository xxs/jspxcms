/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.jspxcms.core.service;

import java.util.List;

import com.jspxcms.core.support.Theme;

import org.springframework.web.multipart.MultipartFile;

public interface ThemeService {

	List<Theme> getAll();

	Theme get(String id);

	boolean upload(MultipartFile multipartFile);

}