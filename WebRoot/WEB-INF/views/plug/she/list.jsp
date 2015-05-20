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
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="site.management"/> - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${fn:length(list)}"/>)</span>
</div>
<form action="list.do" method="get">
	<fieldset class="c-fieldset">
    <legend><s:message code="search"/></legend>
	  <label class="c-lab"><s:message code="site.name"/>: <input type="text" name="search_CONTAIN_name" value="${search_CONTAIN_name[0]}"/></label>
	  <label class="c-lab"><input type="submit" value="<s:message code="search"/>"/></label>
  </fieldset>
</form>
<form method="post">
<tags:search_params/>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="110"><s:message code="operate"/></th>
    <th width="30" class="ls-th-sort"><span class="ls-sort" pagesort="id">ID</span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="name"><s:message code="site.name"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="number"><s:message code="site.number"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="domain"><s:message code="site.domain"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="htmlPath"><s:message code="site.htmlPath"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="org.treeNumber"><s:message code="site.org"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="status"><s:message code="site.status"/></span></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${list}">
  <tr<shiro:hasPermission name="core:site:edit"> ondblclick="location.href=$('#edit_opt_${bean.id}').attr('href');"</shiro:hasPermission>>
    <td align="center">
      <a id="edit_opt_${bean.id}" href="edit.do?id=${bean.id}" class="ls-opt">导出</a>
    </td>
    <td><c:out value="${bean.id}"/></td>
    <td><span style="padding-left:${bean.treeLevel*12}px"><c:out value="${bean.name}"/></span></td>
    <td><c:out value="${bean.number}"/></td>
    <td><c:out value="${bean.domain}"/></td>
    <td><c:out value="${bean.htmlPath}"/></td>
    <td><c:out value="${bean.org.displayName}"/></td>
    <td><s:message code="site.status.${bean.status}"/></td>
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