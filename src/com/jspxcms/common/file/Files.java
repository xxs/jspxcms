package com.jspxcms.common.file;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 文件工具类
 * 
 * @author liufang
 * 
 */
public abstract class Files {
	public static String getSize(Long length) {
		if (length == null) {
			return "0 KB";
		}
		long lengthKB = length / 1024;
		if (lengthKB < 1024) {
			if (length % 1024 > 0) {
				lengthKB++;
			}
			if (lengthKB == 1024) {
				return "1 MB";
			} else {
				return lengthKB + " KB";
			}
		}
		DecimalFormat format = new DecimalFormat("0.##");
		BigDecimal lengthMB = new BigDecimal(length).divide(new BigDecimal(
				1024 * 1024), 2, RoundingMode.HALF_DOWN);
		if (lengthMB.compareTo(new BigDecimal(1024)) < 0) {
			return format.format(lengthMB) + " MB";
		}
		BigDecimal lengthGB = lengthMB.divide(new BigDecimal(1024), 2,
				RoundingMode.HALF_DOWN);
		return format.format(lengthGB) + " GB";
	}

	public static String randomName(String extension) {
		StringBuilder name = new StringBuilder();
		name.append(System.currentTimeMillis());
		String random = RandomStringUtils.random(10, '0', 'Z', true, true);
		name.append(random.toLowerCase());
		if (StringUtils.isNotBlank(extension)) {
			name.append(".");
			name.append(extension);
		}
		return name.toString();
	}

	/**
	 * Iterates over a base name and returns the first non-existent file.<br />
	 * This method extracts a file's base name, iterates over it until the first
	 * non-existent appearance with <code>basename(n).ext</code>. Where n is a
	 * positive integer starting from one.
	 * 
	 * @param file
	 *            base file
	 * @return first non-existent file
	 */
	public static File getUniqueFile(final File file) {
		if (!file.exists())
			return file;

		File tmpFile = new File(file.getAbsolutePath());
		File parentDir = tmpFile.getParentFile();
		int count = 1;
		String extension = FilenameUtils.getExtension(tmpFile.getName());
		String baseName = FilenameUtils.getBaseName(tmpFile.getName());
		do {
			tmpFile = new File(parentDir, baseName + "(" + count++ + ")."
					+ extension);
		} while (tmpFile.exists());
		return tmpFile;
	}
}
