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
	$("#pagedTable").tableHighlight();
	$("#validForm").validate();
});

function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
function optDelete(form) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(!confirmDelete()) {
		return false;
	}
	form.action='delete.do';
	form.submit();
	return true;
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<c:set var="numberExist"><s:message code="attribute.number.exist"/></c:set>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="attribute.management"/> - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${fn:length(list)}"/>)</span>
</div>
<shiro:hasPermission name="core:attribute:save">
<form id="validForm" action="save.do" method="post">
	<fieldset class="c-fieldset">
    <legend><s:message code="create"/></legend>
	  <label class="c-lab"><em class="required">*</em><s:message code="attribute.name"/>: <f:text name="name" class="required" maxlength="50" style="width:120px;"/></label>
	  <label class="c-lab"><em class="required">*</em><s:message code="attribute.number"/>: <f:text name="number" class="{required:true,remote:'check_number.do',messages:{remote:'${numberExist}'}}" maxlength="20" style="width:120px;"/></label>
	  <label class="c-lab"><input type="submit" value="<s:message code="submit"/>"/></label>
	  <div style="clear:both"></div>
  </fieldset>
</form>
</shiro:hasPermission>
<form action="batch_update.do" method="post">
<div class="ls-bc-opt">
	<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
	<div class="in-btn"></div>
	<shiro:hasPermission name="core:attribute:batch_update">
	<div class="ls-btn"><input type="submit" value="<s:message code="save"/>"/></div>
	<div class="ls-btn"></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:attribute:delete">
	<div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
  <div class="ls-btn"></div>
  </shiro:hasPermission>
	<shiro:hasPermission name="core:attribute:batch_update">
  <div class="ls-btn"><input type="button" value="<s:message code='moveTop'/>" onclick="Cms.moveTop('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveUp'/>" onclick="Cms.moveUp('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveDown'/>" onclick="Cms.moveDown('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveBottom'/>" onclick="Cms.moveBottom('ids');"/></div>
  </shiro:hasPermission>
	<div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead>
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="80"><s:message code="operate"/></th>
    <th width="30">ID</th>
    <th><s:message code="attribute.name"/></th>
    <th><s:message code="attribute.number"/></th>
    <th>栏目绑定</th>
    <th>文档绑定</th>
    <th>可选项</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${list}">
  <tr>
    <td><input type="checkbox" name="ids" value="${bean.id}"/></td>
    <td align="center">
      <a href="edit.do?id=${bean.id}" class="ls-opt"><s:message code="edit"/></a>
      <a href="delete.do?ids=${bean.id}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
     </td>
    <td>${bean.id}<f:hidden name="id" value="${bean.id}"/></td>
    <td align="center"><f:text name="name" value="${bean.name}" class="required" maxlength="20" style="width:120px;"/></td>
    <td align="center"><f:text name="number" value="${bean.number}" class="required" maxlength="20" style="width:120px;"/></td>
    <td align="left">
    	<c:forEach var="n" items="${bean.nodePerms}">
	  		${n.displayName}</br>
	  	</c:forEach>
	</td>
    <td align="left">
    	<c:forEach var="n" items="${bean.infoPerms}">
	  		${n.displayName}</br>
	  	</c:forEach>
	</td>
    <td align="center"></td>
  </tr>
  </c:forEach>
  </tbody>
</table>
</form>
</body>
</html>