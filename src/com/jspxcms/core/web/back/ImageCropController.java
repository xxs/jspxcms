package com.jspxcms.core.web.back;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.common.web.PathResolver;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.UploadUtils;

/**
 * ImageCropController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/commons")
public class ImageCropController {
	@RequiresPermissions("commons:img_crop:select")
	@RequestMapping(value = "img_area_select.do")
	public String imgAreaSelect(String src, Integer targetWidth,
			Integer targetHeight, String targetFrame, String name,
			org.springframework.ui.Model modelMap) {
		String srcNoCache = src;
		if (StringUtils.isNotBlank(src)) {
			srcNoCache += src.indexOf("?") == -1 ? "?" : "&";
			srcNoCache += "d=" + System.currentTimeMillis();
		}
		modelMap.addAttribute("src", src);
		modelMap.addAttribute("srcNoCache", srcNoCache);
		modelMap.addAttribute("targetWidth", targetWidth);
		modelMap.addAttribute("targetHeight", targetHeight);
		modelMap.addAttribute("targetFrame", targetFrame);
		modelMap.addAttribute("name", name);
		return "commons/img_area_select";
	}

	@RequiresPermissions("commons:img_crop:submit")
	@RequestMapping(value = "img_crop.do")
	public String imgCrop(String src, Float scale, Integer top, Integer left,
			Integer width, Integer height, Integer targetWidth,
			Integer targetHeight, String name, HttpServletRequest request,
			org.springframework.ui.Model modelMap) throws IOException {
		Integer siteId = Context.getCurrentSiteId(request);
		String contextPath = request.getContextPath();
		if (src.startsWith(contextPath)) {
			src = src.substring(contextPath.length());
		}
		String filePath = pathResolver.getPath(src);
		File file = new File(filePath);
		String extension = FilenameUtils.getExtension(filePath);
		BufferedImage buff = ImageIO.read(file);
		buff = Scalr.crop(buff, left, top, width, height);
		if (targetWidth < width || targetHeight < height) {
			buff = Scalr.resize(buff, Scalr.Method.QUALITY, targetWidth,
					targetHeight);
		}
		String value = UploadUtils.getUrl(siteId, UploadUtils.IMAGE, extension);
		File dest = new File(pathResolver.getPath(value));
		ImageIO.write(buff, extension, dest);
		file.delete();
		String url;
		if (StringUtils.isNotBlank(contextPath)) {
			url = contextPath + value;
		} else {
			url = value;
		}
		modelMap.addAttribute("name", name);
		modelMap.addAttribute("url", url);
		return "commons/img_crop";
	}

	private PathResolver pathResolver;

	@Autowired
	public void setPathResolver(PathResolver pathResolver) {
		this.pathResolver = pathResolver;
	}
}
