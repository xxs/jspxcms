<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
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
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="guestbook.management"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
		<shiro:hasPermission name="ext:guestbook:create">
   	<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
		<div class="in-btn"></div>
		</shiro:hasPermission>
		<shiro:hasPermission name="ext:guestbook:copy">
		<div class="in-btn"><input type="button" value="<s:message code="copy"/>" onclick="location.href='create.do?id=${bean.id}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
		</shiro:hasPermission>
		<shiro:hasPermission name="ext:guestbook:delete">
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
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="guestbook.type"/>:</td>
    <td class="in-ctt" width="35%">
    	<select name="typeId" class="required">
    		<f:options items="${typeList}" itemValue="id" itemLabel="name" selected="${bean.type.id}"/>
    	</select>
    </td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="guestbook.creator"/>:</td>
    <td class="in-ctt" width="35%"><f:text value="${bean.creator.username}" readOnly="readOnly" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="guestbook.name"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="username" value="${bean.username}" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="guestbook.gender"/>:</td>
    <td class="in-ctt" width="35%">
		<label><f:radio name="gender" value="true" checked="${bean.gender}" default="true"/><s:message code="male"/></label>
    	<label><f:radio name="gender" value="false" checked="${bean.gender}"/><s:message code="female"/></label>
	</td>
  </tr>
  <tr>
	<td class="in-lab" width="15%"><s:message code="guestbook.qq"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="qq" value="${bean.qq}" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><s:message code="guestbook.email"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="email" value="${bean.email}" class="email" maxlength="100" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="guestbook.phone"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="phone" value="${bean.phone}" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><s:message code="guestbook.mobile"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="mobile" value="${bean.mobile}" maxlength="11" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="guestbook.creationDate"/>:</td>
    <td class="in-ctt" width="35%"><input type="text" name="creationDate" value="<fmt:formatDate value="${bean.creationDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-ddTHH:mm:ss'});" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><s:message code="guestbook.creationIp"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="creationIp" value="${bean.creationIp}" maxlength="100" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="guestbook.title"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text name="title" value="${bean.title}" style="width:500px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="guestbook.text"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:textarea name="text" value="${bean.text}" style="width:500px;height:80px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="guestbook.replyText"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:textarea name="replyText" value="${bean.replyText}" style="width:500px;height:80px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="guestbook.replyer"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text value="${bean.replyer.username}" readonly="readonly" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="guestbook.replyDate"/>:</td>
    <td class="in-ctt" width="35%"><input type="text" name="replyDate" value="<fmt:formatDate value="${bean.replyDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-ddTHH:mm:ss'});" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><s:message code="guestbook.replyIp"/>:</td>
    <td class="in-ctt" width="35%"><f:text value="${bean.replyIp}" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="guestbook.recommend"/>:</td>
    <td class="in-ctt" width="35%">
    	<label><f:radio name="recommend" value="true" checked="${bean.recommend}"/><s:message code="yes"/></label>
    	<label><f:radio name="recommend" value="false" checked="${bean.recommend}" default="false"/><s:message code="no"/></label>
    </td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="guestbook.status"/>:</td>
    <td class="in-ctt" width="35%">
    	<select name="status" class="required" >
    		<f:option value="0" selected="${bean.status}" default="0"><s:message code='guestbook.status.0'/></f:option>
    		<f:option value="1" selected="${bean.status}"><s:message code='guestbook.status.1'/></f:option>
    		<f:option value="2" selected="${bean.status}"><s:message code='guestbook.status.2'/></f:option>
  		</select>
    </td>
  </tr>
  <tr>
    <td colspan="4" class="in-opt">
      <div class="in-btn"><input type="submit" value="<s:message code="save"/>"/></div>
      <div class="in-btn"><input type="submit" value="<s:message code="saveAndReturn"/>" onclick="$('#redirect').val('list');"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
</table>
</form>
</body>
</html>