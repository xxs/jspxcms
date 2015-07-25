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
	$("#pagedTable").tableHighlight();
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
  <span class="c-position"><s:message code="brand.management"/> - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${fn:length(list)}"/>)</span>
</div>
<form action="list.do" method="get">
	<fieldset class="c-fieldset">
    <legend><s:message code="search"/></legend>
	  <label class="c-lab"><s:message code="brand.name"/>: <input type="text" name="search_CONTAIN_name" value="${search_CONTAIN_name[0]}"/></label>
	  <label class="c-lab">
	  	<s:message code="brand.recommend"/>:
	  	<select name="search_EQ_recommend_Boolean">
	  		<option value=""><s:message code="allSelect"/></option>
	  		<f:option value="true" selected="${search_EQ_recommend_Boolean[0]}"><s:message code="yes"/></f:option>
	  		<f:option value="false" selected="${search_EQ_recommend_Boolean[0]}"><s:message code="no"/></f:option>
	  	</select>
	  </label>
	  <label class="c-lab"><s:message code="brand.status"/>:
	  	<select name="search_EQ_status">
          <option value=""><s:message code="allSelect"/></option>
	      <option value="0"<c:if test="${'0' eq search_EQ_status[0]}"> selected="selected"</c:if>><s:message code="brand.status.0"/></option>
	      <option value="1"<c:if test="${'1' eq search_EQ_status[0]}"> selected="selected"</c:if>><s:message code="brand.status.1"/></option>
        </select>
	  </label>
	  <label class="c-lab"><input type="submit" value="<s:message code="search"/>"/></label>
  </fieldset>
</form>
<form method="post" action="batch_update.do">
<tags:search_params/>
<div class="ls-bc-opt">
	<shiro:hasPermission name="ext:brand:create">
  <div class="ls-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"/></div>
  <div class="ls-btn"></div>
  </shiro:hasPermission>
	<shiro:hasPermission name="ext:brand:batch_update">
  <div class="ls-btn"><input type="submit" value="<s:message code="save"/>"/></div>
  <div class="ls-btn"></div>
  </shiro:hasPermission>
	<shiro:hasPermission name="ext:brand:copy">
  <div class="ls-btn"><input type="button" value="<s:message code="copy"/>" onclick="return optSingle('#copy_opt_');"/></div>
  </shiro:hasPermission>
	<shiro:hasPermission name="ext:brand:edit">
  <div class="ls-btn"><input type="button" value="<s:message code="edit"/>" onclick="return optSingle('#edit_opt_');"/></div>
  </shiro:hasPermission>
	<shiro:hasPermission name="ext:brand:delete">
  <div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
  </shiro:hasPermission>
  <div class="ls-btn"></div>
	<shiro:hasPermission name="ext:brand:batch_update">
  <div class="ls-btn"><input type="button" value="<s:message code='moveTop'/>" onclick="Cms.moveTop('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveUp'/>" onclick="Cms.moveUp('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveDown'/>" onclick="Cms.moveDown('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveBottom'/>" onclick="Cms.moveBottom('ids');"/></div>
  </shiro:hasPermission>
  <div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="110"><s:message code="operate"/></th>
    <th><s:message code="brand.name"/></th>
    <th><s:message code="brand.logo"/></th>
    <th><s:message code="brand.status"/></th>
    <th><s:message code="brand.recommend"/></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${list}">  
  <tr<shiro:hasPermission name="ext:brand:edit"> ondblclick="location.href=$('#edit_opt_${bean.id}').attr('href');"</shiro:hasPermission>>
    <td><input type="checkbox" name="ids" value="${bean.id}"/><f:hidden name="id" value="${bean.id}"/></td>
    <td align="center">
			<shiro:hasPermission name="ext:brand:copy">
      <a id="copy_opt_${bean.id}" href="create.do?id=${bean.id}&${searchstring}" class="ls-opt"><s:message code="copy"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="ext:brand:edit">
      <a id="edit_opt_${bean.id}" href="edit.do?id=${bean.id}&position=${pagedList.number*pagedList.size+status.index}&${searchstring}" class="ls-opt"><s:message code="edit"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="ext:brand:delete">
      <a href="delete.do?ids=${bean.id}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
      </shiro:hasPermission>
     </td>
    <td><c:out value="${bean.name}"/></td>
    <td><c:choose><c:when test="${empty bean.logo}">&nbsp;</c:when><c:otherwise><img style="margin-left:20px;" src="<c:out value="${bean.logo}"/>" /></c:otherwise></c:choose></td> 
    <td><c:if test="${bean.status == 1}"><b></c:if><s:message code="brand.status.${bean.status}"/><c:if test="${bean.status == 1}"></b></c:if></td>
    <td><c:choose><c:when test="${bean.recommend}"><b><s:message code="yes"/></b></c:when><c:otherwise><s:message code="no"/></c:otherwise></c:choose></td> 
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