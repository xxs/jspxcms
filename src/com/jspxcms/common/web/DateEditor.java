package com.jspxcms.common.web;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 日期编辑器
 * 
 * @author liufang
 * 
 */
public class DateEditor extends PropertyEditorSupport {
	public static Date parse(String text) {
		if (StringUtils.isBlank(text)) {
			// Treat empty String as null value.
			return null;
		}
		DateTime dt = DateTime.parse(text);
		if (text.length() > 10) {
			return new java.sql.Timestamp(dt.getMillis());
		} else {
			return new java.sql.Date(dt.getMillis());
		}
	}

	/**
	 * 将日期转换成字符串
	 */
	@Override
	public String getAsText() {
		Date date = (Date) getValue();
		DateTime dt = new DateTime(date.getTime());
		String text = "";
		if (date != null) {
			if (date instanceof java.sql.Timestamp) {
				text = ISODateTimeFormat.dateHourMinuteSecond().print(dt);
			} else {
				text = ISODateTimeFormat.date().print(dt);
			}
		}
		return text;
	}

	/**
	 * 将字符串转换成日期
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(parse(text));
	}
}
