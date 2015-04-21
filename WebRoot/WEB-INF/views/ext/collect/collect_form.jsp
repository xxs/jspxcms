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
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="collect.management"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			<shiro:hasPermission name="ext:collect:create">
			<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="ext:collect:copy">
			<div class="in-btn"><input type="button" value="<s:message code="copy"/>" onclick="location.href='create.do?id=${bean.id}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="ext:collect:delete">
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
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="collect.name"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="name" value="${oprt=='edit' ? (bean.name) : ''}" class="required" maxlength="100" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="collect.node"/>:</td>    
    <td class="in-ctt" width="35%">	    	
	    <f:hidden id="nodeId" name="nodeId" value="${bean.node.id}"/>
	    <f:hidden id="nodeIdNumber" value="${bean.node.id}"/>
	    <f:hidden id="nodeIdName" value="${bean.node.displayName}"/>
	    <f:text id="nodeIdNameDisplay" value="${bean.node.displayName}" readonly="readonly" style="width:160px;"/><input id="nodeIdButton" type="button" value="<s:message code='choose'/>"/>
	    <script type="text/javascript">
	    $(function(){
	    	Cms.f7.nodeInfoPerms("${ctx}","nodeId",{
	    		settings: {"title": "<s:message code='node.f7.selectNode'/>"},
	    		params: {"isRealNode": true}
	    	});
	    });
	    </script>
		</td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="collect.charset"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:text name="charset" value="${bean.charset}" default="UTF-8" class="required" maxlength="100" style="width:180px;"/>
    	<span class="in-prompt" title="<s:message code='collect.charset.prompt' htmlEscape='true'/>"></span>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="collect.url"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<div style="padding-bottom:3px;">
	    	<s:message code="collect.index"/> &nbsp;
				<s:message code="collect.begin"/>: <f:text name="begin" value="${bean.begin}" default="2" class="required digits" style="width:70px;"/>
				<s:message code="collect.end"/>: <f:text name="end" value="${bean.end}" default="10" class="required digits" style="width:70px;"/> &nbsp;
				<label><f:checkbox name="desc" value="${bean.desc}" default="true"/><s:message code="collect.desc"/></label>
			</div>
    	<f:textarea name="url" value="${bean.url}" class="required" maxlength="2000" style="width:95%;height:120px;"/>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="collect.itemArea"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:textarea name="itemArea" value="${bean.itemArea}" class="required" maxlength="255" style="width:95%;height:80px;"/>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="collect.itemUrl"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:textarea name="itemUrl" value="${bean.itemUrl}" class="required" maxlength="255" style="width:95%;height:80px;"/>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="collect.title"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:textarea name="title" value="${bean.title}" class="required" maxlength="255" style="width:95%;height:80px;"/>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="collect.text"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:textarea name="text" value="${bean.text}" class="required" maxlength="255" style="width:95%;height:80px;"/>
    </td>
  </tr>
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