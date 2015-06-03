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
	$("input[name='text']").focus();
});
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="comment.management"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<f:hidden name="bean.site.id" value="${bean.site.id}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
    		<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			<shiro:hasPermission name="core:comment:create">
			<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:comment:copy">
			<div class="in-btn"><input type="button" value="<s:message code="copy"/>" onclick="location.href='create.do?id=${bean.id}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:comment:delete">
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
    <td class="in-lab" width="15%"><em class="required">*</em>绑定栏目:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<span id="nodePermsContainer">
    	<span id="nodePermIds">
	  	<c:forEach var="n" items="${nodePerms}">
	  		<f:hidden name="nodePermIds" value="${n.id}"/>
	  	</c:forEach>
	  	</span>
	  	<span id="nodePermIdsNumber">
	  	<c:forEach var="n" items="${nodePerms}">
	  		<f:hidden name="nodePermIdsNumber" value="${n.id}"/>
	  	</c:forEach>
	  	</span>
	  	<span id="nodePermIdsName">
	  	<c:forEach var="n" items="${nodePerms}">
	  		<f:hidden name="nodePermIdsName" value="${n.displayName}"/>
	  	</c:forEach>
	  	</span>
	    <f:text id="nodePermIdsNameDisplay" readonly="readonly" style="width:220px;"/><input id="nodePermIdsButton" type="button" value="<s:message code='choose'/>"/>
	    </span>
			<script type="text/javascript">
			$(function(){
	    	Cms.f7.nodePerms("${ctx}","nodePermIds",{
	    		settings: {"title": "<s:message code='role.nodePerms.select'/>"}
	    	});
	    });
			</script>
    </td>
  </tr>
  <!-- 
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em>绑定文档:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<span id="infoPermsContainer">
    	<span id="infoPermIds">
	  	<c:forEach var="n" items="${infoPerms}">
	  		<f:hidden name="infoPermIds" value="${n.id}"/>
	  	</c:forEach>
	  	</span>
	  	<span id="infoPermIdsNumber">
	  	<c:forEach var="n" items="${infoPerms}">
	  		<f:hidden name="infoPermIdsNumber" value="${n.id}"/>
	  	</c:forEach>
	  	</span>
	  	<span id="infoPermIdsName">
	  	<c:forEach var="n" items="${infoPerms}">
	  		<f:hidden name="infoPermIdsName" value="${n.displayName}"/>
	  	</c:forEach>
	  	</span>
	    <f:text id="infoPermIdsNameDisplay" readonly="readonly" style="width:220px;"/><input id="infoPermIdsButton" type="button" value="<s:message code='choose'/>"/>
	    </span>
			<script type="text/javascript">
			$(function(){
	    	Cms.f7.nodePerms("${ctx}","infoPermIds",{
	    		settings: {"title": "<s:message code='role.infoPerms.select'/>"},
	    		params: {"isRealNode": true}
	    	});
	    });
			</script>
    </td>
  </tr>
   -->
  <tr>
    <td class="in-lab" width="15%">参数名称:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:text name="name" value="${oprt=='edit' ? bean.name : ''}" class="required" maxlength="255" style="width:180px;"/>
    	<c:if test="${oprt=='edit'}">
      		<f:text name="id" value="${oprt=='edit' ? bean.id : ''}" class="required" maxlength="255" style="width:180px;"/>
	    	<f:text name="site.id" value="${oprt=='edit' ? bean.site.id : ''}" class="required" maxlength="255" style="width:180px;"/>
      	</c:if>
    </td>
  </tr>
  </table>
  
  
  
  <div class="inls-opt margin-top5">
  <b><s:message code="scoreGroup.items"/></b> &nbsp;
  <a href="javascript:void(0);" onclick="addRow();" class="ls-opt"><s:message code='addRow'/></a> &nbsp;
  <a href="javascript:void(0);" onclick="Cms.moveTop('itemIds');" class="ls-opt"><s:message code='moveTop'/></a>
  <a href="javascript:void(0);" onclick="Cms.moveUp('itemIds');" class="ls-opt"><s:message code='moveUp'/></a>
  <a href="javascript:void(0);" onclick="Cms.moveDown('itemIds');" class="ls-opt"><s:message code='moveDown'/></a>
  <a href="javascript:void(0);" onclick="Cms.moveBottom('itemIds');" class="ls-opt"><s:message code='moveBottom'/></a>     
</div>
<textarea id="templateArea" style="display:none">
	<tr>
    <td align="center">
    	<input type="checkbox" name="itemIds" value=""/>
    	<input type="hidden" name="itemId" value=""/>
    </td>
    <td align="center">
      <a href="javascript:void(0);" onclick="$(this).parent().parent().remove();" class="ls-opt"><s:message code="delete"/></a>
    </td>
    <td align="center"><f:text name="itemName" value="" class="required" maxlength="100" style="width:150px;"/></td>
  </tr>
</textarea>
  <script type="text/javascript">
var rowIndex = 0;
<c:if test="${!empty bean && fn:length(bean.items) gt 0}">
rowIndex = ${fn:length(bean.items)};
</c:if>
var rowTemplate = $.format($("#templateArea").val());
function addRow() {
	$(rowTemplate(rowIndex++)).appendTo("#pagedTable tbody");
	$("#pagedTable").tableHighlight();
}
$(function() {
	if(rowIndex==0) {
		<c:if test="${oprt=='create'}">
		addRow();
		</c:if>
	}
});
</script>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="inls-tb">
  <thead>
  <tr>
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="80"><s:message code="operate"/></th>
    <th><s:message code="scoreItem.name"/></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="item" varStatus="status" items="${bean.items}">
  <tr>
    <td align="center">
    	<input type="checkbox" name="itemIds" value="${bean.id}"/>
    	<input type="hidden" name="itemId" value="${item.id}"/>
    </td>
    <td align="center">
      <a href="javascript:void(0);" onclick="$(this).parent().parent().remove();" class="ls-opt"><s:message code="delete"/></a>
    </td>
    <td align="center"><f:text name="itemName" value="${item.name}" class="required" maxlength="100" style="width:150px;"/></td>
  </tr>
  </c:forEach>
  </tbody>
</table>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
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