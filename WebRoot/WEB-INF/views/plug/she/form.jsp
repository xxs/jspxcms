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
	$("#realpath").hide();
	$("input[name='name']").focus();
});
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<c:set var="numberExist"><s:message code="site.number.exist"/></c:set>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="site.management"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" method="post">
<tags:search_params/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><s:message code="site.name"/>:</td>
    <td class="in-ctt" width="35%">${bean.name}</td>
    <td class="in-lab" width="15%"><s:message code="site.fullName"/>:</td>
    <td class="in-ctt" width="35%">${bean.fullName}</td>
  </tr>
    <tr>
    <td class="in-lab" width="15%"><s:message code="site.domain"/>:</td>
    <td class="in-ctt" width="35%">
    	${bean.domain}
    </td>
    <td class="in-lab" width="15%"><s:message code="site.number"/>:</td>
    <td class="in-ctt" width="35%">${bean.number}</td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="site.htmlPath"/>:</td>
    <td class="in-ctt" width="35%">
    	 
    	<label for="allPerm"><f:checkbox name="isrealpath" default="false" onclick="$('#realpath').toggle(this.checked);$('#realpath input').prop('disabled',this.checked);"/>启用绝对路径 &nbsp;</label>
    	<f:text id="realpath" name="realpath" value="${bean.htmlPath}" class="" maxlength="100" style="width:150px;"/>
    </td>
    <td class="in-lab" width="15%"><s:message code="site.templateTheme"/>:</td>
    <td class="in-ctt" width="35%">
	    	<select name="templateTheme">
	    		<f:options items="${themeList}" selected="${bean.templateTheme}"/>
	    	</select>
    </td>
  </tr>
  <tr>
    <td colspan="4" class="in-opt">
      <div class="in-btn"><input type="button" value="生成整站html+css" onclick="this.form.action='make_all_site.do';this.form.submit();""/></div>
      <div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='list.do?${searchstring}';"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
</table>
</form>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="110"><s:message code="operate"/></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="name"><s:message code="webFile.name"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="lastModified"><s:message code="webFile.lastModified"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="length"><s:message code="webFile.length"/></span></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${list}">
  <tr>
    <td><input type="checkbox" name="ids" value="${bean.id}"/></td>
    <td align="center">
      <a href="restore.do?id=${bean.id}&${searchstring}" onclick="return confirmRestore();" class="ls-opt">打包下载</a>
      <a href="delete.do?ids=${bean.id}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
    </td>
    <td>
      <c:out value="${bean.name}"/>
    </td>
    <td><fmt:formatDate value="${bean.lastModified}" pattern="yyyy-MM-dd HH:mm"/></td>
    <td align="right"><c:if test="${bean.file}"><fmt:formatNumber value="${bean.lengthKB}" pattern="#,##0"/> KB</c:if></td>
  </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>