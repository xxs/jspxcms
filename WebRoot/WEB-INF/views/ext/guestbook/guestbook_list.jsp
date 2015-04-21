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
	$("#sortHead").headSort();
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
  <span class="c-position"><s:message code="guestbook.management"/> - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${fn:length(pagedList.content)}"/>)</span>
</div>
<form action="list.do" method="get">
	<fieldset class="c-fieldset">
    <legend><s:message code="search"/></legend>
	  <label class="c-lab">
	  	<s:message code="guestbook.type"/>:
	  	<select name="search_EQ_type.id">
	  		<option value=""><s:message code="allSelect"/></option>
	  		<f:options items="${typeList}" itemValue="id" itemLabel="name" selected="${requestScope['search_EQ_type.id'][0]}"/>
	  	</select>
	  </label>
	  <label class="c-lab">
	  	<s:message code="guestbook.recommend"/>:
	  	<select name="search_EQ_recommend_Boolean">
	  		<option value=""><s:message code="allSelect"/></option>
	  		<f:option value="true" selected="${search_EQ_recommend_Boolean[0]}"><s:message code="yes"/></f:option>
	  		<f:option value="false" selected="${search_EQ_recommend_Boolean[0]}"><s:message code="no"/></f:option>
	  	</select>
	  </label>
	  <label class="c-lab"><s:message code="guestbook.status"/>:
	  	<select name="search_EQ_status">
          <option value=""><s:message code="allSelect"/></option>
	      <option value="0"<c:if test="${'0' eq search_EQ_status[0]}"> selected="selected"</c:if>><s:message code="guestbook.status.0"/></option>
	      <option value="1"<c:if test="${'1' eq search_EQ_status[0]}"> selected="selected"</c:if>><s:message code="guestbook.status.1"/></option>
	      <option value="2"<c:if test="${'2' eq search_EQ_status[0]}"> selected="selected"</c:if>><s:message code="guestbook.status.2"/></option>
        </select>
	  </label>
	  <label class="c-lab">
	  	<s:message code="guestbook.reply"/>:
	  	<select name="search_EQ_reply_Boolean">
	  		<option value=""><s:message code="allSelect"/></option>
	  		<f:option value="true" selected="${search_EQ_reply_Boolean[0]}"><s:message code="yes"/></f:option>
	  		<f:option value="false" selected="${search_EQ_reply_Boolean[0]}"><s:message code="no"/></f:option>
	  	</select>
	  </label>
	  <label class="c-lab"><input type="submit" value="<s:message code="search"/>"/></label>
  </fieldset>
</form>
<form method="post">
<tags:search_params/>
<div class="ls-bc-opt">
	<shiro:hasPermission name="ext:guestbook:create">
	<div class="ls-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?${searchstring}';"/></div>
  <div class="ls-btn"></div>
  </shiro:hasPermission>
	<shiro:hasPermission name="ext:guestbook:copy">
  <div class="ls-btn"><input type="button" value="<s:message code="copy"/>" onclick="return optSingle('#copy_opt_');"/></div>
  </shiro:hasPermission>
	<shiro:hasPermission name="ext:guestbook:edit">
  <div class="ls-btn"><input type="button" value="<s:message code="edit"/>" onclick="return optSingle('#edit_opt_');"/></div>
  </shiro:hasPermission>
	<shiro:hasPermission name="ext:guestbook:delete">
  <div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
  </shiro:hasPermission>
	<shiro:hasPermission name="ext:guestbook_conf:edit">
  <div class="ls-btn"><input type="button" value="<s:message code="guestbookConf.setting"/>" onclick="location.href='../guestbook_conf/edit.do';"/></div>
  </shiro:hasPermission>
  <div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="110"><s:message code="operate"/></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="type.name"><s:message code="guestbook.type"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="title"><s:message code="guestbook.title"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="text"><s:message code="guestbook.text"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="creationDate"><s:message code="guestbook.creationDate"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="reply"><s:message code="guestbook.reply"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="status"><s:message code="guestbook.status"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="recommend"><s:message code="guestbook.recommend"/></span></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${pagedList.content}">  
  <tr<shiro:hasPermission name="ext:guestbook:edit"> ondblclick="location.href=$('#edit_opt_${bean.id}').attr('href');" </shiro:hasPermission>>
    <td><input type="checkbox" name="ids" value="${bean.id}"/><f:hidden name="id" value="${bean.id}"/></td>
    <td align="center">
			<shiro:hasPermission name="ext:guestbook:copy">
      <a id="copy_opt_${bean.id}" href="create.do?id=${bean.id}&${searchstring}" class="ls-opt"><s:message code="copy"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="ext:guestbook:edit">
      <a id="edit_opt_${bean.id}" href="edit.do?id=${bean.id}&position=${pagedList.number*pagedList.size+status.index}&${searchstring}" class="ls-opt"><s:message code="edit"/></a>
      </shiro:hasPermission>
			<shiro:hasPermission name="ext:guestbook:delete">
      <a href="delete.do?ids=${bean.id}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
      </shiro:hasPermission>
     </td>
    <td><c:out value="${bean.type.name}"/></td> 
    <td><c:out value="${bean.title}"/></td>
    <td><c:out value="${bean.text}"/></td>
    <td><fmt:formatDate value="${bean.creationDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    <td><c:choose><c:when test="${bean.reply}"><s:message code="yes"/></c:when><c:otherwise><b><s:message code="no"/></b></c:otherwise></c:choose></td>
    <td><c:if test="${bean.status == 1}"><b></c:if><s:message code="guestbook.status.${bean.status}"/><c:if test="${bean.status == 1}"></b></c:if></td>
    <td><c:choose><c:when test="${bean.recommend}"><b><s:message code="yes"/></b></c:when><c:otherwise><s:message code="no"/></c:otherwise></c:choose></td> 
  </tr>
  </c:forEach>
  </tbody>
</table>
<c:if test="${fn:length(pagedList.content) le 0}"> 
<div class="ls-norecord margin-top5"><s:message code="recordNotFound"/></div>
</c:if>
</form>
<form action="list.do" method="get" class="ls-page">
	<tags:search_params excludePage="true"/>
 	<tags:pagination pagedList="${pagedList}"/>
</form>
</body>
</html>