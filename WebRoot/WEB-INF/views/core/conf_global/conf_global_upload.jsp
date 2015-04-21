<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
	$("#validForm").validate();
});
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="global.configuration"/> - <s:message code="edit"/></span>
</div>
<div class="ls-bc-opt margin-top5">
	 <div id="radio">
		<jsp:include page="types.jsp"/>
	</div>
</div>
<form id="validForm" action="upload_update.do" method="post">
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.upload.fileAllowedExtensions"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="fileAllowedExtensions" value="${bean.upload.fileAllowedExtensions}" style="width:500px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.upload.fileDeniedExtensions"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="fileDeniedExtensions" value="${bean.upload.fileDeniedExtensions}" style="width:500px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.upload.imageAllowedExtensions"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="imageAllowedExtensions" value="${bean.upload.imageAllowedExtensions}" style="width:500px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.upload.imageDeniedExtensions"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="imageDeniedExtensions" value="${bean.upload.imageDeniedExtensions}" style="width:500px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.upload.flashAllowedExtensions"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="flashAllowedExtensions" value="${bean.upload.flashAllowedExtensions}" style="width:500px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.upload.flashDeniedExtensions"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="flashDeniedExtensions" value="${bean.upload.flashDeniedExtensions}" style="width:500px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.upload.videoAllowedExtensions"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="videoAllowedExtensions" value="${bean.upload.videoAllowedExtensions}" style="width:500px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.upload.videoDeniedExtensions"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="videoDeniedExtensions" value="${bean.upload.videoDeniedExtensions}" style="width:500px;"/></td>
  </tr>
  <tr>
    <td colspan="4" class="in-opt">
      <div class="in-btn"><input type="submit" value="<s:message code="save"/>"/></div>
    </td>
  </tr>
</table>
</form>
</body>
</html>