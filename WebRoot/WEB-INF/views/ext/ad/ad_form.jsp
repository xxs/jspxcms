<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
function uploadImg(name,button) {
	if($("#f_"+name).val()=="") {alert("<s:message code='pleaseSelectTheFile'/>");return;}
	Cms.uploadImg("../../core/upload_image.do",name,button);
}
function uploadFlash(name,button) {
	if($("#f_"+name).val()=="") {alert("<s:message code='pleaseSelectTheFile'/>");return;}
	Cms.uploadFile("../../core/upload_flash.do",name,button);
}
function imgCrop(name) {
	if($("#"+name).val()=="") {alert("<s:message code='noImageToCrop'/>");return;}
	Cms.imgCrop("../../commons/img_area_select.do",name);
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="ad.management"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			<shiro:hasPermission name="ext:ad:create">
			<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="ext:ad:copy">
			<div class="in-btn"><input type="button" value="<s:message code="copy"/>" onclick="location.href='create.do?id=${bean.id}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="ext:ad:delete">
			<div class="in-btn"><input type="button" value="<s:message code="delete"/>" onclick="if(confirmDelete()){location.href='delete.do?ids=${bean.id}&${searchstring}';}"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<div class="in-btn"></div>
			<div class="in-btn"><input type="button" value="<s:message code="prev"/>" onclick="location.href='edit.do?id=${side.prev.id}&position=${position-1}&${searchstring}';"<c:if test="${empty side.prev}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"><input type="button" value="<s:message code="next"/>" onclick="location.href='edit.do?id=${side.next.id}&position=${position+1}&${searchstring}';"<c:if test="${empty side.next}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			<div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='list.do?${searchstring}';"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="ad.slot"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<select name="slotId" class="required" onchange="location.href='${oprt=='edit' ? 'edit' : 'create'}.do?id=${bean.id}&querySlotId='+this.value;">
    		<f:options items="${slotList}" itemValue="id" itemLabel="name" selected="${slot.id}"/>
    	</select>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="ad.name"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="name" value="${oprt=='edit' ? (bean.name) : ''}" class="required" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="ad.seq"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="seq" value="${bean.seq}" default="50" class="{required:true,digits:true,min:0,max:99999}" maxlength="5" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="ad.beginDate"/>:</td>
    <td class="in-ctt" width="35%">
    	<input type="text" name="beginDate" value="<fmt:formatDate value="${bean.beginDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-ddTHH:mm:ss'});" style="width:180px;"/>
    </td>
    <td class="in-lab" width="15%"><s:message code="ad.endDate"/>:</td>
    <td class="in-ctt" width="35%">
			<input type="text" name="endDate" value="<fmt:formatDate value="${bean.endDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-ddTHH:mm:ss'});" style="width:180px;"/>
		</td>
  </tr>
  <c:choose>
  <c:when test="${slot.type==1}">
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="ad.text"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="text" value="${bean.text}" class="required" maxlength="255" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="ad.url"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="url" value="${bean.url}" default="http://" class="required" maxlength="255" style="width:180px;"/></td>
  </tr>
  </c:when>
  <c:when test="${slot.type==2}">
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="ad.text"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="text" value="${bean.text}" class="required" maxlength="255" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="ad.url"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="url" value="${bean.url}" default="http://" class="required" maxlength="255" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="ad.image"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<tags:image_upload name="image" value="${bean.image}" width="${slot.width}" height="${slot.height}"/>
    </td>
  </tr>
  </c:when>
  <c:when test="${slot.type==3}">
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="ad.flash"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    <div>
	    <f:text id="flash" name="flash" value="${bean.flash}" maxlength="255" style="width:300px;"/>
    </div>
    <div style="padding-top:3px;">
    	<input id="f_flash" name="f_flash" type="file" size="23" style="width:235px;"/> <input type="button" onclick="uploadFlash('flash',this)" value="<s:message code="upload"/>"/>
    </div>
    </td>
  </tr>
  </c:when>
  <c:when test="${slot.type==4}">
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="ad.script"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:textarea name="script" value="${bean.script}" style="width:500px;height:120px;"/>
    </td>
  </tr>
  </c:when>
  </c:choose>
  <tr>
    <td colspan="4" class="in-opt">
      <div class="in-btn"><input type="submit" value="<s:message code="save"/>"/></div>
      <div class="in-btn"><input type="submit" value="<s:message code="saveAndReturn"/>" onclick="$('#redirect').val('list');"/></div>
      <c:if test="${oprt=='create'}">
      <div class="in-btn"><input type="submit" value="<s:message code="saveAndCreate"/>" onclick="$('#redirect').val('create');"/></div>
      </c:if>
      <div style="clear:both;"></div>
    </td>
  </tr>
</table>
</form>
</body>
</html>