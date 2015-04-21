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
<style type="text/css">
.line{margin-top:5px;}
.line .label{width:100px;float:left;text-align:right;}
</style>
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
<form id="validForm" action="mail_update.do" method="post">
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.mail.smtpHost"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="smtpHost" value="${bean.mail.smtpHost}" class="required" maxlength="255" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><s:message code="global.mail.smtpPort"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="smtpPort" value="${bean.mail.smtpPort}" class="digits" maxlength="11" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.mail.smtpAuth"/>:</td>
    <td class="in-ctt" width="35%">
    	<label><f:radio name="smtpAuth" value="true" checked="${bean.mail.smtpAuth}" default="true"/><s:message code="yes"/></label>
    	<label><f:radio name="smtpAuth" value="false" checked="${bean.mail.smtpAuth}"/><s:message code="no"/></label>
    </td>
    <td class="in-lab" width="15%"><s:message code="global.mail.smtpTimeout"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="smtpTimeout" value="${bean.mail.smtpTimeout}" class="digits" maxlength="11" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.mail.smtpUsername"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="smtpUsername" value="${bean.mail.smtpUsername}" class="required" maxlength="255" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.mail.smtpPassword"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="smtpPassword" value="${bean.mail.smtpPassword}" class="required" maxlength="255" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="global.mail.from"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="from" value="${bean.mail.from}" class="required" maxlength="255" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="global.mail.test"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<div class="line"><label class="label" for="testTo"><s:message code="global.mail.testTo"/>:</label><f:text id="testTo" name="testTo" value="${bean.mail.testTo}" maxlength="255" style="width:300px;"/></div>
    	<div class="line"><label class="label" for="testSubject"><s:message code="global.mail.testSubject"/>:</label><f:text id="testSubject" name="testSubject" value="${bean.mail.testSubject}" maxlength="255" style="width:300px;"/></div>
    	<div class="line"><label class="label" for="testText"><s:message code="global.mail.testText"/>:</label><f:textarea id="testText" name="testText" value="${bean.mail.testText}" maxlength="2000" style="width:300px;height:50px;"/></div>
    	<div class="line"><label class="label" for="testText">&nbsp;</label><input type="button" value="<s:message code="global.mail.testSend"/>" onclick="location.href='mail_send.do?to='+encodeURI($('#testTo').val())+'&subject='+encodeURI($('#testSubject').val())+'&text='+encodeURI($('#testText').val())"/></div>
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