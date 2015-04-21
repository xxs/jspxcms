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
  <span class="c-position"><s:message code="site.configuration"/> - <s:message code="edit"/></span>
</div>
<div class="ls-bc-opt margin-top5">
	 <div id="radio">
		<jsp:include page="types.jsp"/>
	</div>
</div>
<form id="validForm" action="watermark_update.do" method="post">
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.watermark.mode"/>:</td>
    <td class="in-ctt" width="35%">
    	<label><f:radio name="mode" value="0" checked="${bean.watermark.mode}"/><s:message code="site.watermark.mode.0"/></label> &nbsp;
    	<label><f:radio name="mode" value="1" checked="${bean.watermark.mode}" default="1"/><s:message code="site.watermark.mode.1"/></label>
    </td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.watermark.alpha"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="alpha" value="${bean.watermark.alpha}" class="{required:true,digits:true,min:0,max:100}" maxlength="3" style="width:180px;"/><span class="in-prompt" title="<s:message code='site.watermark.alpha.prompt'/>">&nbsp;</span></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.watermark.minWidth"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="minWidth" value="${bean.watermark.minWidth}" class="{required:true,digits:true,min:0,max:99999}" maxlength="5" style="width:180px;"/>px</td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.watermark.minHeight"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="minHeight" value="${bean.watermark.minHeight}" class="{required:true,digits:true,min:0,max:99999}" maxlength="5" style="width:180px;"/>px</td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.watermark.paddingX"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="paddingX" value="${bean.watermark.paddingX}" class="{required:true,digits:true,min:0,max:99999}" maxlength="5" style="width:180px;"/>px</td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.watermark.paddingY"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="paddingY" value="${bean.watermark.paddingY}" class="{required:true,digits:true,min:0,max:99999}" maxlength="5" style="width:180px;"/>px</td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="site.watermark.image"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="image" value="${bean.watermark.image}" class="" maxlength="255" style="width:300px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="site.watermark.position"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<table cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
    		<tr>
    			<td style="padding:5px;border:1px solid #999;"><label><input type="radio" name="position" value="1"<c:if test="${bean.watermark.position eq 1}"> checked="checked"</c:if>/><s:message code="site.watermark.position.1"/></label></td>
    			<td style="padding:5px;border:1px solid #999;"><label><input type="radio" name="position" value="2"<c:if test="${bean.watermark.position eq 2}"> checked="checked"</c:if>/><s:message code="site.watermark.position.2"/></label></td>
    			<td style="padding:5px;border:1px solid #999;"><label><input type="radio" name="position" value="3"<c:if test="${bean.watermark.position eq 3}"> checked="checked"</c:if>/><s:message code="site.watermark.position.3"/></label></td>
    		</tr>
    		<tr>
    			<td style="padding:5px;border:1px solid #999;"><label><input type="radio" name="position" value="4"<c:if test="${bean.watermark.position eq 4}"> checked="checked"</c:if>/><s:message code="site.watermark.position.4"/></label></td>
    			<td style="padding:5px;border:1px solid #999;"><label><input type="radio" name="position" value="5"<c:if test="${bean.watermark.position eq 5}"> checked="checked"</c:if>/><s:message code="site.watermark.position.5"/></label></td>
    			<td style="padding:5px;border:1px solid #999;"><label><input type="radio" name="position" value="6"<c:if test="${bean.watermark.position eq 6}"> checked="checked"</c:if>/><s:message code="site.watermark.position.6"/></label></td>
    		</tr>
    		<tr>
    			<td style="padding:5px;border:1px solid #999;"><label><input type="radio" name="position" value="7"<c:if test="${bean.watermark.position eq 7}"> checked="checked"</c:if>/><s:message code="site.watermark.position.7"/></label></td>
    			<td style="padding:5px;border:1px solid #999;"><label><input type="radio" name="position" value="8"<c:if test="${bean.watermark.position eq 8}"> checked="checked"</c:if>/><s:message code="site.watermark.position.8"/></label></td>
    			<td style="padding:5px;border:1px solid #999;"><label><input type="radio" name="position" value="9"<c:if test="${bean.watermark.position eq 9}"> checked="checked"</c:if>/><s:message code="site.watermark.position.9"/></label></td>
    		</tr>
    	</table>    	
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