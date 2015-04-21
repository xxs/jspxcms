package com.jspxcms.common.web;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class DropOversizeFilesMultipartResolver extends
		CommonsMultipartResolver {
	public static final String EXCEPTION_KEY = "SizeLimitExceededException";

	@SuppressWarnings("unchecked")
	@Override
	protected MultipartParsingResult parseRequest(
			final HttpServletRequest request) {

		String encoding = determineEncoding(request);
		FileUpload fileUpload = prepareFileUpload(encoding);

		List<FileItem> fileItems;
		try {
			fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			request.setAttribute(EXCEPTION_KEY, ex);
			fileItems = Collections.EMPTY_LIST;
		} catch (FileUploadException ex) {
			throw new MultipartException(
					"Could not parse multipart servlet request", ex);
		}

		return parseFileItems(fileItems, encoding);
	}
}