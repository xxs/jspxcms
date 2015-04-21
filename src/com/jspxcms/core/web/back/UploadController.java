package com.jspxcms.core.web.back;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jspxcms.common.image.Images;
import com.jspxcms.common.util.Files;
import com.jspxcms.common.util.JsonMapper;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.GlobalUpload;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.SiteWatermark;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.UploadUtils;

/**
 * UploadController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("/core")
public class UploadController {
	private static final Logger logger = LoggerFactory
			.getLogger(UploadController.class);

	@RequestMapping(value = "upload_image.do", method = RequestMethod.POST)
	public void uploadImage(Boolean scale, Integer width, Integer height,
			Boolean thumbnail, Integer thumbnailWidth, Integer thumbnailHeight,
			Boolean watermark, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		upload(request, response, UploadUtils.IMAGE, scale, width, height,
				thumbnail, thumbnailWidth, thumbnailHeight, watermark);
	}

	@RequestMapping(value = "upload_flash.do", method = RequestMethod.POST)
	public void uploadFlash(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		upload(request, response, UploadUtils.FLASH);
	}

	@RequestMapping(value = "upload_file.do", method = RequestMethod.POST)
	public void uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		upload(request, response, UploadUtils.FILE);
	}

	@RequestMapping(value = "upload_video.do", method = RequestMethod.POST)
	public void uploadVideo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		upload(request, response, UploadUtils.VIDEO);
	}

	private void upload(HttpServletRequest request,
			HttpServletResponse response, String type) throws IOException {
		upload(request, response, type, null, null, null, null, null, null,
				null);
	}

	private void upload(HttpServletRequest request,
			HttpServletResponse response, String type, Boolean scale,
			Integer width, Integer height, Boolean thumbnail,
			Integer thumbnailWidth, Integer thumbnailHeight, Boolean watermark)
			throws IOException {
		Site site = Context.getCurrentSite(request);
		String contextPath = request.getContextPath();
		MultipartFile file = getUploadFile(request);
		Map<String, Object> resultMap = doUpload(file, type, site, contextPath,
				scale, width, height, thumbnail, thumbnailWidth,
				thumbnailHeight, watermark);
		String ckeditor = request.getParameter("CKEditor");
		if (ckeditor != null) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			String error = resultMap.get("error").toString();
			String callback = request.getParameter("CKEditorFuncNum");
			out.println("<script type=\"text/javascript\">");
			out.println("(function(){var d=document.domain;while (true){try{var A=window.parent.document.domain;break;}catch(e) {};d=d.replace(/.*?(?:\\.|$)/,'');if (d.length==0) break;try{document.domain=d;}catch (e){break;}}})();\n");
			if (StringUtils.isBlank(error)) {
				String fileUrl = resultMap.get("fileUrl").toString();
				out.println("window.parent.CKEDITOR.tools.callFunction("
						+ callback + ",'" + fileUrl + "',''" + ");");
			} else {
				out.println("alert('" + error + "');");
			}
			out.print("</script>");
			out.flush();
			out.close();
		}
		JsonMapper mapper = new JsonMapper();
		String json = mapper.toJson(resultMap);
		logger.debug(json);
		Servlets.writeHtml(response, json);
	}

	private Map<String, Object> doUpload(MultipartFile file, String type,
			Site site, String contextPath, Boolean scale, Integer width,
			Integer height, Boolean thumbnail, Integer thumbnailWidth,
			Integer thumbnailHeight, Boolean watermark) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (file == null || file.isEmpty()) {
			logger.debug("file is empty");
			map.put("error", "no file upload!");
			return map;
		}
		SiteWatermark sw = site.getWatermark();
		String extension = FilenameUtils.getExtension(
				file.getOriginalFilename()).toLowerCase();
		GlobalUpload gu = site.getGlobal().getUpload();
		if (UploadUtils.IMAGE.equals(type)
				&& !gu.isImageExtensionValid(extension)) {
			logger.debug("image extension not allowed: " + extension);
			map.put("error", "image extension not allowed: " + extension);
			return map;
		} else if (UploadUtils.FILE.equals(type)
				&& !gu.isFileExtensionValid(extension)) {
			logger.debug("file extension not allowed: " + extension);
			map.put("error", "file extension not allowed: " + extension);
			return map;
		} else if (UploadUtils.VIDEO.equals(type)
				&& !gu.isVideoExtensionValid(extension)) {
			logger.debug("video extension not allowed: " + extension);
			map.put("error", "video extension not allowed: " + extension);
			return map;
		} else if (UploadUtils.FLASH.equals(type)
				&& !gu.isFlashExtensionValid(extension)) {
			logger.debug("flash extension not allowed: " + extension);
			map.put("error", "flash extension not allowed: " + extension);
			return map;
		}

		if (!UploadUtils.IMAGE.equals(type) || scale == null || width == null
				|| height == null) {
			scale = false;
		}
		if (!UploadUtils.IMAGE.equals(type) || thumbnail == null
				|| thumbnailWidth == null || thumbnailHeight == null) {
			thumbnail = false;
		}
		if (watermark == null) {
			watermark = sw.getMode() == SiteWatermark.MODE_ON;
		}
		if (!UploadUtils.IMAGE.equals(type)
				|| sw.getMode() == SiteWatermark.MODE_OFF
				|| StringUtils.isBlank(sw.getImageUrl())) {
			watermark = false;
		}
		if (watermark || scale || thumbnail) {
			try {
				if (!Images.isImageExtension(extension)
						|| !Images.isImage(file.getInputStream())) {
					watermark = false;
					scale = false;
					thumbnail = false;
				}
			} catch (IOException e) {
				logger.error("", e);
				watermark = false;
				scale = false;
				thumbnail = false;
			}
		}
		String url = UploadUtils.getUrl(site.getId(), type, extension);
		File dest = getDestFile(url);

		try {
			dest.mkdirs();
			if (scale || watermark || thumbnail) {
				if (scale) {
					BufferedImage buff = ImageIO.read(file.getInputStream());
					buff = Scalr.resize(buff, Scalr.Method.QUALITY, width,
							height);
				}
				storeImg(file, scale, width, height, thumbnail, thumbnailWidth,
						thumbnailHeight, watermark, sw, dest, extension);
			} else {
				file.transferTo(dest);
			}
			String fileName = file.getOriginalFilename();
			long fileLength = file.getSize();
			String fileUrl;
			if (StringUtils.isNotBlank(contextPath)) {
				fileUrl = contextPath + url;
			} else {
				fileUrl = url;
			}
			map.put("error", "");
			map.put("fileUrl", fileUrl);
			map.put("fileName", fileName);
			map.put("fileLength", fileLength);
			map.put("fileExtension", extension);
		} catch (IllegalStateException e) {
			logger.error("", e);
			map.put("error", e.getMessage());
		} catch (IOException e) {
			logger.error("", e);
			map.put("error", e.getMessage());
		}
		return map;
	}

	private MultipartFile getUploadFile(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		if (CollectionUtils.isEmpty(fileMap)) {
			throw new IllegalStateException("No upload file found!");
		}
		return fileMap.entrySet().iterator().next().getValue();
	}

	private File getDestFile(String url) {
		String real = pathResolver.getPath(url);
		logger.debug("file path: " + real);
		File dest = new File(real);
		dest = Files.getUniqueFile(dest);
		return dest;
	}

	private void storeImg(MultipartFile file, boolean scale, Integer width,
			Integer height, boolean thumbnail, Integer thumbnailWidth,
			Integer thumbnailHeight, boolean watermark, SiteWatermark sw,
			File dest, String extension) throws IOException {
		BufferedImage buff = ImageIO.read(file.getInputStream());
		if (scale) {
			buff = Scalr.resize(buff, Scalr.Method.QUALITY, width, height);
		}
		if (thumbnail) {
			BufferedImage thumbnailBuff = Scalr.resize(buff,
					Scalr.Method.QUALITY, thumbnailWidth, thumbnailHeight);
			File thumbnailFile = new File(UploadUtils.getThumbnailPath(dest
					.getAbsolutePath()));
			ImageIO.write(thumbnailBuff, extension, thumbnailFile);
		}
		if (watermark) {
			String watermarkPath = pathResolver.getPath(sw.getImagePath());
			File watermarkFile = new File(watermarkPath);
			BufferedImage watermarkBuff = ImageIO.read(watermarkFile);
			Images.watermark(buff, watermarkBuff, sw.getPosition(),
					sw.getPaddingX(), sw.getPaddingY(), sw.getAlphaFloat(),
					sw.getMinWidth(), sw.getMinHeight());
		}
		ImageIO.write(buff, extension, dest);
	}

	private PathResolver pathResolver;

	@Autowired
	public void setPathResolver(PathResolver pathResolver) {
		this.pathResolver = pathResolver;
	}
}
