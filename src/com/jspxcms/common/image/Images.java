package com.jspxcms.common.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

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
