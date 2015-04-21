<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	$("input[name='name']").focus();
});
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="guestbookConf.setting"/> - <s:message code="edit"/></span>
</div>
<form id="validForm" action="update.do" method="post">
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="guestbookConf.mode"/>:</td>
    <td class="in-ctt" width="35%">
    	<select name="mode" class="required">
    		<f:option value="0" selected="${bean.mode}"><s:message code="guestbookConf.mode.0"/></f:option>
    		<f:option value="1" selected="${bean.mode}"><s:message code="guestbookConf.mode.1"/></f:option>
    		<f:option value="2" selected="${bean.mode}"><s:message code="guestbookConf.mode.2"/></f:option>
    	</select>
		</td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="guestbookConf.auditMode"/>:</td>
    <td class="in-ctt" width="35%">
    	<select name="auditMode" class="required">
    		<f:option value="0" selected="${bean.auditMode}"><s:message code="guestbookConf.auditMode.0"/></f:option>
    		<f:option value="1" selected="${bean.auditMode}"><s:message code="guestbookConf.auditMode.1"/></f:option>
    		<f:option value="2" selected="${bean.auditMode}"><s:message code="guestbookConf.auditMode.2"/></f:option>
    		<f:option value="3" selected="${bean.auditMode}"><s:message code="guestbookConf.auditMode.3"/></f:option>
    	</select>
   	</td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="guestbookConf.captchaMode"/>:</td>
    <td class="in-ctt" width="35%">
    	<select name="captchaMode" class="required">
    		<f:option value="0" selected="${bean.captchaMode}"><s:message code="guestbookConf.captchaMode.0"/></f:option>
    		<f:option value="1" selected="${bean.captchaMode}"><s:message code="guestbookConf.captchaMode.1"/></f:option>
    		<f:option value="2" selected="${bean.captchaMode}"><s:message code="guestbookConf.captchaMode.2"/></f:option>
    		<f:option value="3" selected="${bean.captchaMode}"><s:message code="guestbookConf.captchaMode.3"/></f:option>
    	</select>		
		</td>
		<td class="in-lab" width="15%"><em class="required">*</em><s:message code="guestbookConf.maxLength"/>:</td>
	  <td class="in-ctt" width="35%">
	  	<f:text name="maxLength" value="${bean.maxLength}" class="{required:true,digits:true,min:1,max:2147483647}" style="width:180px;"/>
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