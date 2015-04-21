package com.jspxcms.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 上传工具类
 * 
 * @author liufang
 * 
 */
public class Uploads {
	public static final DateFormat DEF_FORMAT = new SimpleDateFormat(
			"/yyyyMM/yyyyMMddHHmmss_");

	public static String randomName(String extension) {
		StringBuilder filename = new StringBuilder();
		filename.append(DEF_FORMAT.format(new Date()));
		filename.append(RandomStringUtils.random(6, '0', 'Z', true, true)
				.toLowerCase());
		if (StringUtils.isNotBlank(extension)) {
			filename.append(".").append(extension.toLowerCase());
		}
		return filename.toString();
	}
}
