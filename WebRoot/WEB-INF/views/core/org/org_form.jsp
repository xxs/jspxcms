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
  <span class="c-position"><s:message code="org.management"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			<shiro:hasPermission name="core:org:create">
			<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?parentId=${parent.id}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:org:copy">
			<div class="in-btn"><input type="button" value="<s:message code="copy"/>" onclick="location.href='create.do?id=${bean.id}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:org:delete">
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
    <td class="in-lab" width="15%"><s:message code="org.parent"/>:</td>
    <td class="in-ctt" colspan="3">
    <c:set var="parentName"><c:choose><c:when test="${empty parent}"><s:message code="org.root"/></c:when><c:otherwise><c:out value="${parent.displayName}"/></c:otherwise></c:choose></c:set>
    <c:choose>
    <c:when test="${orgTreeNumber eq bean.treeNumber}">
    	<f:hidden id="parentId" name="parentId" value="${parent.id}"/>
    	${parentName}
    </c:when>
    <c:otherwise>	    
	    <f:hidden id="parentId" name="parentId" value="${parent.id}"/>
	    <f:hidden id="parentIdNumber" value="${parent.id}"/>
	    <f:hidden id="parentIdName" value="${parentName}"/>
	    <f:text id="parentIdNameDisplay" value="${parentName}" readonly="readonly" style="width:300px;"/><input id="parentIdButton" type="button" value="<s:message code='choose'/>"/>
	    <script type="text/javascript">
	    $(function(){
	    	Cms.f7.org("${ctx}","parentId",{
	    		settings: {"title": "<s:message code='org.f7.selectOrg'/>"},
	    		params: {
	    			"excludeChildrenId":"${(oprt=='edit') ? (bean.id) : ''}",
	    			"treeNumber": "${orgTreeNumber}"
	    		}
	    	});
	    });
	    </script>
    </c:otherwise>
    </c:choose>
    <%-- 
    	<c:choose>
    		<c:when test="${!empty baen.parent}">
    			<c:out value="${baen.parent.name}"/>
    		</c:when>
    		<c:otherwise>
    			<s:message code="org.root"/>
    		</c:otherwise>
    	</c:choose>
    	 --%>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="org.name"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="name" value="${oprt=='edit' ? bean.name : ''}" class="required" maxlength="150" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><s:message code="org.number"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="number" value="${oprt=='edit' ? bean.number : ''}" maxlength="100" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="org.phone"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="phone" value="${bean.phone}" maxlength="100" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><s:message code="org.fax"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="fax" value="${bean.fax}" maxlength="100" style="width:180px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="org.fullName"/>:</td>
    <td class="in-ctt" colspan="3"><f:text name="fullName" value="${oprt=='edit' ? bean.fullName : ''}" maxlength="150" style="width:500px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="org.address"/>:</td>
    <td class="in-ctt" colspan="3"><f:text name="address" value="${bean.address}" class="{maxlength:255}" style="width:500px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="org.description"/>:</td>
    <td class="in-ctt" colspan="3"><f:textarea name="description" value="${bean.description}" class="{maxlength:255}" style="width:500px;height:80px;"/></td>
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