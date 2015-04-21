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
<form id="validForm" action="base_update.do" method="post">
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.protocol"/>:</td>
    <td class="in-ctt" width="35%">
    	<select name="protocol" class="required">
    		<option<c:if test="${bean.protocol eq 'http'}"> selected="selected"</c:if>>http</option>
    		<option<c:if test="${bean.protocol eq 'https'}"> selected="selected"</c:if><c:if test=""> selected="selected"</c:if>>https</option>
    	</select>
    </td>
    <td class="in-lab" width="15%"><s:message code="global.port"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="port" value="${bean.port}" class="{'range':[0,65535]}" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.contextPath"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="contextPath" value="${bean.contextPath}" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.withDomain"/>:</td>
    <td class="in-ctt" width="35%">
    	<label><f:radio name="withDomain" value="true" checked="${bean.withDomain}" class="required" /><s:message code="yes"/></label>
    	<label><f:radio name="withDomain" value="false" checked="${bean.withDomain}" default="false" class="required" /><s:message code="no"/></label>
  	</td>
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