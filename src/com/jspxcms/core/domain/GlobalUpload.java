package com.jspxcms.core.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jspxcms.core.support.Configurable;

public class GlobalUpload implements Configurable {
	public static final String PREFIX = "upload_";
	// 7z,aiff,asf,avi,bmp,csv,doc,fla,flv,gif,gz,gzip,jpeg,jpg,mid,mov,mp3,mp4,mpc,mpeg,mpg,ods,odt,pdf,png,ppt,pxd,qt,ram,rar,rm,rmi,rmvb,rtf,sdc,sitd,swf,sxc,sxw,tar,tgz,tif,tiff,txt,vsd,wav,wma,wmv,xls,xml,zip
	public static final String FILE_ALLOWED_EXTENSIONS = "upload_fileAllowedExtensions";
	public static final String FILE_DENIED_EXTENSIONS = "upload_fileDeniedExtensions";
	public static final String IMAGE_ALLOWED_EXTENSIONS = "upload_imageAllowedExtensions";
	public static final String IMAGE_DENIED_EXTENSIONS = "upload_imageDeniedExtensions";
	public static final String FLASH_ALLOWED_EXTENSIONS = "upload_flashAllowedExtensions";
	public static final String FLASH_DENIED_EXTENSIONS = "upload_flashDeniedExtensions";
	public static final String VIDEO_ALLOWED_EXTENSIONS = "upload_videoAllowedExtensions";
	public static final String VIDEO_DENIED_EXTENSIONS = "upload_videoDeniedExtensions";

	private Map<String, String> customs;

	public GlobalUpload() {
	}

	public GlobalUpload(Map<String, String> customs) {
		this.customs = customs;
	}

	public boolean isFileExtensionValid(String extension) {
		return isValid(extension, getFileDeniedExtensions(),
				getFileAllowedExtensions());
	}

	public boolean isImageExtensionValid(String extension) {
		return isValid(extension, getImageDeniedExtensions(),
				getImageAllowedExtensions());

	}

	public boolean isFlashExtensionValid(String extension) {
		return isValid(extension, getFlashDeniedExtensions(),
				getFlashAllowedExtensions());

	}

	public boolean isVideoExtensionValid(String extension) {
		return isValid(extension, getVideoDeniedExtensions(),
				getVideoAllowedExtensions());
	}

	private boolean isValid(String extension, String denied, String allowed) {
		if (StringUtils.isNotBlank(denied)) {
			for (String d : StringUtils.split(denied, ',')) {
				if (d.equalsIgnoreCase(extension)) {
					return false;
				}
			}
		}
		if (StringUtils.isNotBlank(allowed)) {
			for (String a : StringUtils.split(allowed, ',')) {
				if (a.equalsIgnoreCase(extension)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	public String getFileAllowedExtensions() {
		return getCustoms().get(FILE_ALLOWED_EXTENSIONS);
	}

	public void setFileAllowedExtensions(String fileAllowedExtensions) {
		if (StringUtils.isNotBlank(fileAllowedExtensions)) {
			getCustoms().put(FILE_ALLOWED_EXTENSIONS, fileAllowedExtensions);
		} else {
			getCustoms().remove(FILE_ALLOWED_EXTENSIONS);
		}
	}

	public String getFileDeniedExtensions() {
		return getCustoms().get(FILE_DENIED_EXTENSIONS);
	}

	public void setFileDeniedExtensions(String fileDeniedExtensions) {
		if (StringUtils.isNotBlank(fileDeniedExtensions)) {
			getCustoms().put(FILE_DENIED_EXTENSIONS, fileDeniedExtensions);
		} else {
			getCustoms().remove(FILE_DENIED_EXTENSIONS);
		}
	}

	public String getImageAllowedExtensions() {
		return getCustoms().get(IMAGE_ALLOWED_EXTENSIONS);
	}

	public void setImageAllowedExtensions(String imageAllowedExtensions) {
		if (StringUtils.isNotBlank(imageAllowedExtensions)) {
			getCustoms().put(IMAGE_ALLOWED_EXTENSIONS, imageAllowedExtensions);
		} else {
			getCustoms().remove(IMAGE_ALLOWED_EXTENSIONS);
		}
	}

	public String getImageDeniedExtensions() {
		return getCustoms().get(IMAGE_DENIED_EXTENSIONS);
	}

	public void setImageDeniedExtensions(String imageDeniedExtensions) {
		if (StringUtils.isNotBlank(imageDeniedExtensions)) {
			getCustoms().put(IMAGE_DENIED_EXTENSIONS, imageDeniedExtensions);
		} else {
			getCustoms().remove(IMAGE_DENIED_EXTENSIONS);
		}
	}

	public String getFlashAllowedExtensions() {
		return getCustoms().get(FLASH_ALLOWED_EXTENSIONS);
	}

	public void setFlashAllowedExtensions(String flashAllowedExtensions) {
		if (StringUtils.isNotBlank(flashAllowedExtensions)) {
			getCustoms().put(FLASH_ALLOWED_EXTENSIONS, flashAllowedExtensions);
		} else {
			getCustoms().remove(FLASH_ALLOWED_EXTENSIONS);
		}
	}

	public String getFlashDeniedExtensions() {
		return getCustoms().get(FLASH_DENIED_EXTENSIONS);
	}

	public void setFlashDeniedExtensions(String flashDeniedExtensions) {
		if (StringUtils.isNotBlank(flashDeniedExtensions)) {
			getCustoms().put(FLASH_DENIED_EXTENSIONS, flashDeniedExtensions);
		} else {
			getCustoms().remove(FLASH_DENIED_EXTENSIONS);
		}
	}

	public String getVideoAllowedExtensions() {
		return getCustoms().get(VIDEO_ALLOWED_EXTENSIONS);
	}

	public void setVideoAllowedExtensions(String videoAllowedExtensions) {
		if (StringUtils.isNotBlank(videoAllowedExtensions)) {
			getCustoms().put(VIDEO_ALLOWED_EXTENSIONS, videoAllowedExtensions);
		} else {
			getCustoms().remove(VIDEO_ALLOWED_EXTENSIONS);
		}
	}

	public String getVideoDeniedExtensions() {
		return getCustoms().get(VIDEO_DENIED_EXTENSIONS);
	}

	public void setVideoDeniedExtensions(String videoDeniedExtensions) {
		if (StringUtils.isNotBlank(videoDeniedExtensions)) {
			getCustoms().put(VIDEO_DENIED_EXTENSIONS, videoDeniedExtensions);
		} else {
			getCustoms().remove(VIDEO_DENIED_EXTENSIONS);
		}
	}

	public Map<String, String> getCustoms() {
		if (customs == null) {
			customs = new HashMap<String, String>();
		}
		return customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	public String getPrefix() {
		return PREFIX;
	}
}
