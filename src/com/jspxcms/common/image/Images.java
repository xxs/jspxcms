package com.jspxcms.common.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 图片工具类
 * 
 * @author liufang
 * 
 */
public class Images {
	/**
	 * 图片扩展名
	 */
	public static final String[] IMAGE_EXTENSIONS = new String[] { "jpeg",
			"jpg", "png", "gif", "bmp", "pcx", "iff", "ras", "pbm", "pgm",
			"ppm", "psd" };

	/**
	 * 是否是图片扩展名
	 * 
	 * @param extension
	 * @return
	 */
	public static final boolean isImageExtension(String extension) {
		if (StringUtils.isBlank(extension)) {
			return false;
		}
		for (String imageExtension : IMAGE_EXTENSIONS) {
			if (StringUtils.equalsIgnoreCase(imageExtension, extension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the underlying input stream contains an image.
	 * 
	 * @param in
	 *            input stream of an image
	 * @return <code>true</code> if the underlying input stream contains an
	 *         image, else <code>false</code>
	 */
	public static boolean isImage(final InputStream in) {
		ImageInfo ii = new ImageInfo();
		ii.setInput(in);
		return ii.check();
	}

	public static void watermark(BufferedImage buff, BufferedImage watermark,
			int x, int y, float alpha) {
		Graphics2D g = buff.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		g.drawImage(watermark, x, y, null);
		g.dispose();
	}
	
	/**
	 * Checks if the underlying input stream contains an image.
	 * 
	 * @param in
	 *            input stream of an image
	 * @return 图片类型。如果为null，代表不是图片。
	 * @throws IOException
	 */
	// URL获取的InputStream不支持reset，要考虑这个问题。
	// 另外考虑一下gif的问题。
	// png\gif都不是好惹的货，一个是多桢动画、一个是透明图片。
	// png按png格式写入。
	// 不支持多桢的gif，不考虑jdk1.5不支持gif写入问题。
	// 格式判断全部采用file，或者无法判断时，按后缀格式处理。
	// 远程的url就先存为file吧。
	// 这个方法的目的是判断是否可以操作的图片格式，以及真实的图片格式。
	public static String getFormatName(InputStream in) throws IOException {
		ImageInfo ii = new ImageInfo();
		ii.setInput(in);
		if (ii.check()) {
			String formatName = ii.getFormatName().toLowerCase();
			if (ArrayUtils.contains(IMAGE_EXTENSIONS, formatName)) {
				return formatName;
			}
		}
		return null;

	}

	public static void watermark(BufferedImage buff, BufferedImage watermark,
			int postion, int paddingX, int paddingY, float alpha, int minWidth,
			int minHeight) {
		int width = buff.getWidth();
		int height = buff.getHeight();
		int wmWidth = watermark.getWidth();
		int wmHeight = watermark.getHeight();
		if (width < minWidth || height < minHeight
				|| wmWidth + paddingX > width || wmHeight + paddingY > height) {
			return;
		}
		int x, y;
		switch (postion) {
		case 1: {
			x = paddingX;
			y = paddingY;
			break;
		}
		case 2: {
			x = width / 2 - wmWidth / 2;
			y = paddingY;
			break;
		}
		case 3: {
			x = width - wmWidth - paddingX;
			y = paddingY;
			break;
		}
		case 4: {
			x = paddingX;
			y = height / 2 - wmHeight / 2;
			break;
		}
		case 5: {
			x = width / 2 - wmWidth / 2;
			y = height / 2 - wmHeight / 2;
			break;
		}
		case 6: {
			x = width - wmWidth - paddingX;
			y = height / 2 - wmHeight / 2;
			break;
		}
		case 7: {
			x = paddingX;
			y = height - wmHeight - paddingY;
			break;
		}
		case 8: {
			x = width / 2 - wmWidth / 2;
			y = height - wmHeight - paddingY;
			break;
		}
		case 9: {
			x = width - wmWidth - paddingX;
			y = height - wmHeight - paddingY;
			break;
		}
		default: {
			throw new IllegalArgumentException("postion must be 1..9");
		}
		}
		watermark(buff, watermark, x, y, alpha);
	}
}
