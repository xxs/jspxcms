<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
	<shiro:hasPermission name="core:node:edit">
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
function optMulti(form, url, noValidate) {
	noValidate = noValidate || false;
	if(!noValidate && Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	var ids = "";
	$("input[name='ids']:checkbox:checked").each(function(){
		ids += "&ids="+$(this).val();
	});
	if(ids.length>0) {
		if(url.indexOf("?")==-1) {
			url += "?" + ids.substring(1);
		} else if(url.lastIndexOf("?")==url.length-1||url.lastIndexOf("&")==url.length-1) {
			url += ids.substring(1);
		} else {
			url += ids;
		}		
	}
	location.href=url;
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
<c:if test="${!empty refreshLeft}">
parent.frames["left"].location.href="left.do";
</c:if>
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="node.management"/> - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${fn:length(list)}"/>)</span>
</div>
<form action="list.do" method="get">
	<fieldset class="c-fieldset">
    <legend><s:message code="search"/></legend>
	  <label class="c-lab"><s:message code="node.name"/>: <input type="text" name="search_CONTAIN_name" value="${search_CONTAIN_name[0]}"/></label>
	  <label class="c-lab"><s:message code="node.number"/>: <input type="text" name="search_CONTAIN_number" value="${search_CONTAIN_number[0]}"/></label>
	  <label class="c-lab">
	  	<s:message code="node.hidden"/>:
	  	<select name="search_EQ_hidden_Boolean">
	  		<option value=""><s:message code="allSelect"/></option>
	  		<f:option value="true" selected="${search_EQ_hidden_Boolean[0]}"><s:message code="yes"/></f:option>
	  		<f:option value="false" selected="${search_EQ_hidden_Boolean[0]}"><s:message code="no"/></f:option>
	  	</select>
	  </label>
	  <label class="c-lab"><input type="submit" value="<s:message code="search"/>"/></label>
  </fieldset>
  <f:hidden name="queryParentId" value="${queryParentId}"/>
  <f:hidden name="showDescendants" value="${showDescendants}"/>
</form>
<form action="batch_update.do" method="post">
<f:hidden name="queryParentId" value="${queryParentId}"/>
<f:hidden name="showDescendants" value="${showDescendants}"/>
<tags:search_params/>
<div class="ls-bc-opt">
	<shiro:hasPermission name="core:node:create">
	<div class="ls-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?parentId=${parent.id}&queryParentId=${queryParentId}&showDescendants=${showDescendants}&${searchstring}';"/></div>
	<div class="ls-btn"></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:node:batch_update">
	<div class="ls-btn"><input type="submit" value="<s:message code="save"/>"/></div>
	<div class="ls-btn"></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:node:copy">
	<div class="ls-btn"><input type="button" value="<s:message code="copy"/>" onclick="return optSingle('#copy_opt_');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:node:edit">
	<div class="ls-btn"><input type="button" value="<s:message code="edit"/>" onclick="return optSingle('#edit_opt_');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:node:move:form">
	<div class="ls-btn"><input type="button" value="<s:message code="move"/>" onclick="return optMulti(this.form,'move_input.do?queryParentId=${queryParentId}&showDescendants=${showDescendants}&${searchstring}',true);"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:node:delete">
	<div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
	</shiro:hasPermission>
	<div class="ls-btn"></div>
	<shiro:hasPermission name="core:node:batch_update">
  <div class="ls-btn"><input type="button" value="<s:message code='moveTop'/>" onclick="Cms.moveTop('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveUp'/>" onclick="Cms.moveUp('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveDown'/>" onclick="Cms.moveDown('ids');"/></div>
  <div class="ls-btn"><input type="button" value="<s:message code='moveBottom'/>" onclick="Cms.moveBottom('ids');"/></div>
  </shiro:hasPermission>
	<div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&queryParentId=${queryParentId}&showDescendants=${showDescendants}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="220"><s:message code="operate"/></th>
    <th width="30" class="ls-th-sort"><span class="ls-sort" pagesort="id">ID</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="name"><s:message code="node.name"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="number"><s:message code="node.number"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="views"><s:message code="node.views"/></span></th>
    <th><s:message code="node.hidden"/></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${list}">
  <tr beanid="${bean.id}">
    <td><input type="checkbox" name="ids" value="${bean.id}"/></td>
    <td align="center">
			<shiro:hasPermission name="core:node:create">
      <a id="createChild_opt_${bean.id}" href="create.do?parentId=${bean.id}&queryParentId=${queryParentId}&showDescendants=${showDescendants}&${searchstring}" class="ls-opt"><s:message code="node.createChild"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="core:node:copy">
      <a id="copy_opt_${bean.id}" href="create.do?cid=${bean.id}&queryParentId=${queryParentId}&showDescendants=${showDescendants}&${searchstring}" class="ls-opt"<c:if test="${bean.parent==null}"> disabled="disabled"</c:if>><s:message code="copy"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="core:node:edit">
      <a id="edit_opt_${bean.id}" href="edit.do?id=${bean.id}&position=${pagedList.number*pagedList.size+status.index}&queryParentId=${queryParentId}&showDescendants=${showDescendants}&${searchstring}" class="ls-opt"><s:message code="edit"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="core:node:move:form">
      <a id="move_opt_${bean.id}" href="move_input.do?ids=${bean.id}&queryParentId=${queryParentId}&showDescendants=${showDescendants}&${searchstring}" class="ls-opt"<c:if test="${bean.parent==null}"> disabled="disabled"</c:if>><s:message code="move"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="core:node:delete">
      <a href="delete.do?ids=${bean.id}&queryParentId=${queryParentId}&showDescendants=${showDescendants}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
      </shiro:hasPermission>
     </td>
    <td><c:out value="${bean.id}"/><f:hidden name="id" value="${bean.id}"/></td>
    <td><span style="padding-left:${showDescendants ? bean.treeLevel*12 : 0}px"><f:text name="name" value="${bean.name}" style="width:120px;"/></span></td>
    <td align="center"><f:text name="number" value="${bean.number}" style="width:120px;"/></td>
    <td align="center"><f:text name="views" value="${bean.views}" style="width:50px;text-align:right;"/></td>
    <td align="center"><f:checkbox name="hidden" value="${bean.hidden}"/></td>
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