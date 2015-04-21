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
  <span class="c-position"><s:message code="memberGroup.management"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			<shiro:hasPermission name="core:member_group:create">
			<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:member_group:copy">
			<div class="in-btn"><input type="button" value="<s:message code="copy"/>" onclick="location.href='create.do?id=${bean.id}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:member_group:delete">
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
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="memberGroup.name"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="name" value="${oprt=='edit' ? bean.name : ''}" class="required" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><s:message code="memberGroup.description"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="description" value="${bean.description}" maxlength="255" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="memberGroup.viewNodes"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<span id="viewNodeIds">
	  	<c:forEach var="n" items="${viewNodes}">
	  		<f:hidden name="viewNodeIds" value="${n.id}"/>
	  	</c:forEach>
	  	</span>
	  	<span id="viewNodeIdsNumber">
	  	<c:forEach var="n" items="${viewNodes}">
	  		<f:hidden name="viewNodeIdsNumber" value="${n.id}"/>
	  	</c:forEach>
	  	</span>
	  	<span id="viewNodeIdsName">
	  	<c:forEach var="n" items="${viewNodes}">
	  		<f:hidden name="viewNodeIdsName" value="${n.displayName}"/>
	  	</c:forEach>
	  	</span>
	    <f:text id="viewNodeIdsNameDisplay" readonly="readonly" style="width:300px;"/><input id="viewNodeIdsButton" type="button" value="<s:message code='choose'/>"/>
			<script type="text/javascript">
			$(function(){
	    	Cms.f7.nodePerms("${ctx}","viewNodeIds",{
	    		settings: {"title": "<s:message code='memberGroup.viewNodes.select'/>"}
	    	});
	    });
			</script>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="memberGroup.contriNodes"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<span id="contriNodeIds">
	  	<c:forEach var="n" items="${contriNodes}">
	  		<f:hidden name="contriNodeIds" value="${n.id}"/>
	  	</c:forEach>
	  	</span>
	  	<span id="contriNodeIdsNumber">
	  	<c:forEach var="n" items="${contriNodes}">
	  		<f:hidden name="contriNodeIdsNumber" value="${n.id}"/>
	  	</c:forEach>
	  	</span>
	  	<span id="contriNodeIdsName">
	  	<c:forEach var="n" items="${contriNodes}">
	  		<f:hidden name="contriNodeIdsName" value="${n.displayName}"/>
	  	</c:forEach>
	  	</span>
	    <f:text id="contriNodeIdsNameDisplay" readonly="readonly" style="width:300px;"/><input id="contriNodeIdsButton" type="button" value="<s:message code='choose'/>"/>
			<script type="text/javascript">
			$(function(){
	    	Cms.f7.nodePerms("${ctx}","contriNodeIds",{
	    		settings: {"title": "<s:message code='memberGroup.contriNodes.select'/>"}
	    	});
	    });
			</script>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="memberGroup.commentNodes"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<span id="commentNodeIds">
	  	<c:forEach var="n" items="${commentNodes}">
	  		<f:hidden name="commentNodeIds" value="${n.id}"/>
	  	</c:forEach>
	  	</span>
	  	<span id="commentNodeIdsNumber">
	  	<c:forEach var="n" items="${commentNodes}">
	  		<f:hidden name="commentNodeIdsNumber" value="${n.id}"/>
	  	</c:forEach>
	  	</span>
	  	<span id="commentNodeIdsName">
	  	<c:forEach var="n" items="${commentNodes}">
	  		<f:hidden name="commentNodeIdsName" value="${n.displayName}"/>
	  	</c:forEach>
	  	</span>
	    <f:text id="commentNodeIdsNameDisplay" readonly="readonly" style="width:300px;"/><input id="commentNodeIdsButton" type="button" value="<s:message code='choose'/>"/>
			<script type="text/javascript">
			$(function(){
	    	Cms.f7.nodePerms("${ctx}","commentNodeIds",{
	    		settings: {"title": "<s:message code='memberGroup.commentNodes.select'/>"}
	    	});
	    });
			</script>
    </td>
  </tr>
  <%--
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="memberGroup.type"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<select name="type" onchange="$('#iptr').toggle($(this).val()==2);">
    		<f:option value="0" selected="${bean.type}" default="0"><s:message code="memberGroup.type.0"/></f:option>
    		<f:option value="2" selected="${bean.type}"><s:message code="memberGroup.type.2"/></f:option>
    	</select>
    </td>
  </tr>
  <tr id="iptr"<c:if test="${empty bean || bean.type != 2}"> style="display:none;"</c:if>>
    <td class="in-lab" width="15%"><s:message code="memberGroup.ip"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:textarea name="ip" value="${bean.ip}" style="width:500px;height:150px;"/>
    </td>
  </tr>
   --%>  
  <tr>
    <td colspan="4" class="in-opt">
    	<input type="hidden" name="type" value="${oprt=='create' ? 0 : bean.type}"/>
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