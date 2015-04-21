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
<form id="validForm" action="register_update.do" method="post">
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.register.mode"/>:</td>
    <td class="in-ctt" width="35%">
    	<label><f:radio name="mode" value="0" checked="${bean.register.mode}"/><s:message code="global.register.mode.0"/></label> &nbsp;
    	<label><f:radio name="mode" value="1" checked="${bean.register.mode}" default="1"/><s:message code="global.register.mode.1"/></label>
    </td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.register.verifyMode"/>:</td>
    <td class="in-ctt" width="35%">
    	<label><f:radio name="verifyMode" value="0" checked="${bean.register.verifyMode}"/><s:message code="global.register.verifyMode.0"/></label> &nbsp;
    	<label><f:radio name="verifyMode" value="1" checked="${bean.register.verifyMode}" default="1"/><s:message code="global.register.verifyMode.1"/></label> &nbsp;
    	<label><f:radio name="verifyMode" value="2" checked="${bean.register.verifyMode}" default="1"/><s:message code="global.register.verifyMode.2"/></label>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.register.group"/>:</td>
    <td class="in-ctt" width="35%">
    	<select name="groupId">
    		<f:options items="${groupList}" itemLabel="name" itemValue="id" selected="${bean.register.groupId}"/>
    	</select>
    </td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.register.org"/>:</td>
    <td class="in-ctt" width="35%">
    	<select name="orgId">
    		<c:forEach var="org" items="${orgList}">
    		<option value="${org.id}"<c:if test="${org.id==bean.register.orgId}"> selected="selected"</c:if>><c:forEach begin="1" end="${org.treeLevel}">--</c:forEach>${org.name}</option>
    		</c:forEach>
    	</select>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.register.minLength"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="minLength" value="${bean.register.minLength}" class="required digits" maxlength="5" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.register.maxLength"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="maxLength" value="${bean.register.maxLength}" class="required digits" maxlength="5" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.register.validCharacter"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="validCharacter" value="${bean.register.validCharacter}" class="required" maxlength="2000" style="width:500px;"/></td>
  </tr>
  <%-- 
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.register.reservedWords"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:textarea name="reservedWords" value="${bean.register.reservedWords}" maxlength="2000" style="width:500px;height:80px;"/></td>
  </tr>
   --%>
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.register.userAgreement"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:textarea name="userAgreement" value="${bean.register.userAgreement}" maxlength="2000" style="width:500px;height:80px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.register.verifyEmail"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<div><s:message code="global.register.email.subject"/>:</div>
    	<div><f:text name="verifyEmailSubject" value="${bean.register.verifyEmailSubject}" class="required" maxlength="75" style="width:500px;"/></div>
    	<div><s:message code="global.register.email.text"/>:</div>
    	<div><f:textarea name="verifyEmailText" value="${bean.register.verifyEmailText}" class="required" maxlength="2000" style="width:500px;height:80px;"/></div>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.register.passwordEmail"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<div><s:message code="global.register.email.subject"/>:</div>
    	<div><f:text name="passwordEmailSubject" value="${bean.register.passwordEmailSubject}" class="required" maxlength="75" style="width:500px;"/></div>
    	<div><s:message code="global.register.email.text"/>:</div>
    	<div><f:textarea name="passwordEmailText" value="${bean.register.passwordEmailText}" class="required" maxlength="2000" style="width:500px;height:80px;"/></div>
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