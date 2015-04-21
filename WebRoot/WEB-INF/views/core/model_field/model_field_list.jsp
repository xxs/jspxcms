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
	<shiro:hasPermission name="core:model_field:edit">
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
function optMulti(form, action, msg) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(msg && !confirm(msg)) {
		return false;
	}
	form.action=action;
	form.submit();
	return true;
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
  <span class="c-position"><s:message code="model.management"/> - <s:message code="model.type.${model.type}"/> - <s:message code="model.fieldList"/> - ${model.name}</span>
	<span class="c-total">(<s:message code="totalElements" arguments="${fn:length(list)}"/>)</span>
</div>
<div class="ls-bc-opt margin-top5">
	<div id="radio">
	<c:forEach var="type" items="${types}">
		<input type="radio" id="radio_${type}" onclick="location.href='../model/list.do?queryType=${type}&${searchstring}';"<c:if test="${queryType==type}"> checked="checked"</c:if>/><label for="radio_${type}"><s:message code="model.type.${type}"/></label>
	</c:forEach>
	</div>
	<script type="text/javascript">
		$("#radio").buttonset();
	</script>
</div>
<form action="batch_update.do" method="post">
<f:hidden name="modelId" value="${model.id}"/>
<tags:search_params/>
<div class="ls-bc-opt">
	<shiro:hasPermission name="core:model_field:create">
	<div class="ls-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?modelId=${model.id}&${searchstring}';"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:model_field:predefined_list">
	<div class="ls-btn"><input type="button" value="<s:message code="modelField.addPredefinedField"/>" onclick="location.href='predefined_list.do?modelId=${model.id}&${searchstring}';"/></div>
	</shiro:hasPermission>
	<div class="ls-btn"></div>
	<shiro:hasPermission name="core:model_field:batch_update">
	<div class="ls-btn"><input type="submit" value="<s:message code="save"/>"/></div>
	<div class="ls-btn"></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:model_field:edit">
	<div class="ls-btn"><input type="button" value="<s:message code="edit"/>" onclick="return optSingle('#edit_opt_');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:model_field:disable">
	<div class="ls-btn"><input type="button" value="<s:message code="disable"/>" onclick="return optMulti(this.form,'disable.do');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:model_field:enable">
	<div class="ls-btn"><input type="button" value="<s:message code="enable"/>" onclick="return optMulti(this.form,'enable.do');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:model_field:delete">
	<div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
	</shiro:hasPermission>
	<div class="ls-btn"></div>
	<shiro:hasPermission name="core:model_field:batch_update">
  <div class="ls-btn"><input type="button" value="<s:message code='moveTop'/>" onclick="Cms.moveTop('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveUp'/>" onclick="Cms.moveUp('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveDown'/>" onclick="Cms.moveDown('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveBottom'/>" onclick="Cms.moveBottom('ids');"/></div>
	<div class="ls-btn"></div>
	</shiro:hasPermission>
	<div class="ls-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='../model/list.do?queryType=${model.type}&${searchstring}';"/></div>
	<div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="80"><s:message code="operate"/></th>
    <th width="30">ID</th>
    <th><s:message code="modelField.name"/></th>
    <th><s:message code="modelField.label"/></th>
    <th><s:message code="modelField.dblColumn"/></th>
    <th><s:message code="modelField.invoke"/></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${list}">
  <tr beanid="${bean.id}" style="<c:if test='${bean.disabled}'>background-color:#D4D0C8;</c:if>">
    <td><input type="checkbox" name="ids" value="${bean.id}"/></td>
    <td align="center">
			<shiro:hasPermission name="core:model_field:copy">
      <a id="edit_opt_${bean.id}" href="edit.do?id=${bean.id}&position=${pagedList.number*pagedList.size+status.index}&${searchstring}" class="ls-opt"><s:message code="edit"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="core:model_field:delete">
      <a href="delete.do?ids=${bean.id}&modelId=${model.id}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
      </shiro:hasPermission>
     </td>
    <td><c:out value="${bean.id}"/><input type="hidden" name="id" value="${bean.id}"/></td>
    <td align="center">
      <input type="text" name="name" value="<c:out value="${bean.name}"/>"<c:if test="${bean.predefined}"> readonly="readonly" class="disabled"</c:if>/>
    </td>
    <td align="center"><f:text type="text" name="label" value="${bean.label}"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="${bean.dblColumn}"/></td>
    <td>
    	<c:choose>
    	<c:when test="${bean.custom && bean.clob}">
    		clobs['${bean.name}']
    	</c:when>
    	<c:when test="${bean.custom && !bean.clob}">
    		customs['${bean.name}']
    	</c:when>
    	<c:otherwise>
    		<%-- \${info.${bean.name}} --%>
    	</c:otherwise>
    	</c:choose>
    </td>
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