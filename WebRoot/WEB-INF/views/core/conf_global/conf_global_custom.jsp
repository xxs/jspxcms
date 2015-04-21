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
  <span class="c-position"><s:message code="global.configuration"/> - <s:message code="edit"/></span>
</div>
<div class="ls-bc-opt margin-top5">
	<div id="radio">
		<jsp:include page="types.jsp"/>
	</div>
</div>
<form id="validForm" action="custom_update.do" method="post">
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">

	<c:set var="colCount" value="${0}"/>
  <c:forEach var="field" items="${model.enabledFields}">
  <c:if test="${colCount%2==0||!field.dblColumn}"><tr></c:if>
  <td class="in-lab" width="15%"><c:if test="${field.required}"><em class="required">*</em></c:if><c:out value="${field.label}"/>:</td>
  <td<c:if test="${field.type!=50}"> class="in-ctt"</c:if><c:choose><c:when test="${field.dblColumn}"> width="35%"</c:when><c:otherwise> width="85%" colspan="3"</c:otherwise></c:choose>>
  <c:choose>
  <c:when test="${field.custom}">
  	<tags:feild_custom bean="${bean}" field="${field}"/>
  </c:when>
  <c:otherwise>
  
  </c:otherwise>
  </c:choose>
  </td><c:if test="${colCount%2==1||!field.dblColumn}"></tr></c:if>
  <c:if test="${field.dblColumn}"><c:set var="colCount" value="${colCount+1}"/></c:if>
  </c:forEach>
  <tr>
    <td colspan="4" class="in-opt">
      <div class="in-btn"><input type="submit" value="<s:message code="save"/>"/></div>
    </td>
  </tr>
</table>
</form>
</body>
</html>