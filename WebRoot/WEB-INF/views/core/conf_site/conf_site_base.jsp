<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
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
  <span class="c-position"><s:message code="site.configuration"/> - <s:message code="edit"/></span>
</div>
<div class="ls-bc-opt margin-top5">
	<div id="radio">
		<jsp:include page="types.jsp"/>
	</div>
</div>
<form id="validForm" action="base_update.do" method="post">
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.name"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="name" value="${bean.name}" class="required" maxlength="100" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><s:message code="site.fullName"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="fullName" value="${bean.fullName}" class="" maxlength="100" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.domain"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="domain" value="${bean.domain}" class="required" maxlength="100" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.number"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="number" value="${bean.number}" class="" maxlength="100" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.templateTheme"/>:</td>
    <td class="in-ctt" width="35%">
    	<select name="templateTheme">
    		<f:options items="${themeList}" selected="${bean.templateTheme}"/>
    	</select>
    </td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.noPicture"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="noPicture" value="${bean.noPicture}" class="required" maxlength="255" style="width:180px;"/></td>
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