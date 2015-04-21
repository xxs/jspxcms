<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	$("#sortHead").headSort();
	<shiro:hasPermission name="core:model:edit">
	$("#pagedTable tbody tr").dblclick(function(eventObj) {
		var nodeName = eventObj.target.nodeName.toLowerCase();
		if(nodeName!="input"&&nodeName!="select"&&nodeName!="textarea") {
			location.href=$("#edit_opt_"+$(this).attr("beanid")).attr('href');
		}
	});
	</shiro:hasPermission>
});
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
function optSingle(opt) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(Cms.checkeds("ids")>1) {
		alert("<s:message code='pleaseSelectOne'/>");
		return false;
	}
	var id = $("input[name='ids']:checkbox:checked").val();
	location.href=$(opt+id).attr("href");
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
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="model.management"/> - <s:message code="model.type.${queryType}"/> - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${fn:length(list)}"/>)</span>
</div>
<div class="ls-bc-opt margin-top5">
	<div id="radio">
	<c:forEach var="type" items="${types}">
		<input type="radio" id="radio_${type}" onclick="location.href='list.do?queryType=${type}&${searchstring}';"<c:if test="${queryType==type}"> checked="checked"</c:if>/><label for="radio_${type}"><s:message code="model.type.${type}"/></label>
	</c:forEach>
	</div>
	<script type="text/javascript">
		$("#radio").buttonset();
	</script>
</div>
<form action="batch_update.do" method="post">
<f:hidden name="queryType" value="${queryType}"/>
<tags:search_params/>
<div class="ls-bc-opt margin-top5">
	<shiro:hasPermission name="core:model:create">
	<div class="ls-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?queryType=${queryType}&${searchstring}';"/></div>
	<div class="ls-btn"></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:model:batch_update">
	<div class="ls-btn"><input type="submit" value="<s:message code="save"/>"/></div>
	<div class="ls-btn"></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:model_field:list">
	<div class="ls-btn"><input type="button" value="<s:message code="model.fieldList"/>" onclick="return optSingle('#fields_opt_');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:model:copy">
	<div class="ls-btn"><input type="button" value="<s:message code="copy"/>" onclick="return optSingle('#copy_opt_');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:model:edit">
	<div class="ls-btn"><input type="button" value="<s:message code="edit"/>" onclick="return optSingle('#edit_opt_');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:model:delete">
	<div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
	</shiro:hasPermission>
	<div class="ls-btn"></div>
	<shiro:hasPermission name="core:model:batch_update">
  <div class="ls-btn"><input type="button" value="<s:message code='moveTop'/>" onclick="Cms.moveTop('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveUp'/>" onclick="Cms.moveUp('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveDown'/>" onclick="Cms.moveDown('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveBottom'/>" onclick="Cms.moveBottom('ids');"/></div>
  </shiro:hasPermission>
	<div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&queryType=${queryType}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="170"><s:message code="operate"/></th>
    <th width="30" class="ls-th-sort"><span class="ls-sort" pagesort="id">ID</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="name"><s:message code="model.name"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="type"><s:message code="model.type"/></span></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${list}">
  <tr beanid="${bean.id}">
    <td><input type="checkbox" name="ids" value="${bean.id}"/></td>
    <td align="center">
			<shiro:hasPermission name="core:model_field:list">
      <a id="fields_opt_${bean.id}" href="../model_field/list.do?modelId=${bean.id}&queryType=${queryType}&${searchstring}" class="ls-opt"><s:message code="model.fieldList"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="core:model:copy">
      <a id="copy_opt_${bean.id}" href="create.do?id=${bean.id}&queryType=${queryType}&${searchstring}" class="ls-opt"><s:message code="copy"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="core:model:edit">
      <a id="edit_opt_${bean.id}" href="edit.do?id=${bean.id}&position=${pagedList.number*pagedList.size+status.index}&queryType=${queryType}&${searchstring}" class="ls-opt"><s:message code="edit"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="core:model:delete">
      <a href="delete.do?ids=${bean.id}&queryType=${queryType}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
      </shiro:hasPermission>
     </td>
    <td><c:out value="${bean.id}"/><f:hidden name="id" value="${bean.id}"/></td>
    <td align="center"><f:text name="name" value="${bean.name}" style="width:150px;"/></td>
    <td align="center"><s:message code="model.type.${bean.type}"/></td>
  </tr>
  </c:forEach>
  </tbody>
</table>
<c:if test="${fn:length(list) le 0}"> 
<div class="ls-norecord margin-top5"><s:message code="recordNotFound"/></div>
</c:if>
</form>
</body>
</html>