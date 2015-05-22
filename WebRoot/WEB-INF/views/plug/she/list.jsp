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
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="site.management"/> - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${fn:length(list)}"/>)</span>
</div>
<form method="post">
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="140"><s:message code="operate"/></th>
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
  <c:forEach var="bean" varStatus="status" items="${siteList}">
  <tr>
    <td align="center">
      <a id="edit_opt_${bean.id}" href="make_all_site.do?id=${bean.id}" class="ls-opt">快捷生成</a>
      <a id="edit_opt_${bean.id}" href="edit.do?id=${bean.id}" class="ls-opt">详细配置</a>
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
<form action="batch_update.do" method="post">
<f:hidden name="parentId" value="${parentId}"/>
<div class="ls-bc-opt">
	<div class="ls-btn"><input type="button" value="全部删除" onclick="return optDelete(this.form);"/></div>
	<div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&parentId=${fnx:urlEncode(parentId)}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="280"><s:message code="operate"/></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="name"><s:message code="webFile.name"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="lastModified"><s:message code="webFile.lastModified"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="type"><s:message code="webFile.type"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="length"><s:message code="webFile.length"/></span></th>
  </tr>
  </thead>
  <tbody id="dblclickBody">
  <c:forEach var="bean" varStatus="status" items="${list}">
  <tr>
    <td><c:if test="${!bean.parent}"><input type="checkbox" name="ids" value="<c:out value='${bean.id}'/>"/></c:if></td>
    <td align="center">
    	<c:choose>
    		<c:when test='${bean.directory}'>
    			<c:url value="list.do?${searchstring}" var="editUrl">
    				<c:param name="parentId" value="${bean.id}"/>
    			</c:url>
    		</c:when>
    		<c:otherwise>
    			<c:url value="edit.do?${searchstring}" var="editUrl">
    				<c:param name="id" value="${bean.id}"/>
    				<c:param name="parentId" value="${parentId}"/>
    				<c:param name="position" value="${pagedList.number*pagedList.size+status.index}"/>
    			</c:url>
    		</c:otherwise>
    	</c:choose>
      <a id="edit_opt_${status.index}" href="${editUrl}" class="ls-opt" style="display:none;"><s:message code="edit"/></a>
      
			<shiro:hasPermission name="core:web_file:zip_download">
      <c:url value="zip_download.do?${searchstring}" var="zipDownloadUrl">
 				<c:param name="ids" value="${bean.id}"/>
 			</c:url>
      <a id="zip_download_opt_${status.index}" href="${zipDownloadUrl}" class="ls-opt"<c:if test="${bean.parent}"> disabled="disabled"</c:if>><s:message code="webFile.zipDownload"/></a>
      </shiro:hasPermission>
      
			<shiro:hasPermission name="core:web_file:zip">
      <c:url value="zip.do?${searchstring}" var="zipUrl">
 				<c:param name="ids" value="${bean.id}"/>
 				<c:param name="parentId" value="${parentId}"/>
 			</c:url>
      <a id="zip_opt_${status.index}" href="${zipUrl}" class="ls-opt"<c:if test="${bean.parent}"> disabled="disabled"</c:if>><s:message code="webFile.zip"/></a>
			</shiro:hasPermission>
      
			<shiro:hasPermission name="core:web_file:delete">
 			<c:url value="delete.do?${searchstring}" var="deleteUrl">
 				<c:param name="ids" value="${bean.id}"/>
 				<c:param name="parentId" value="${parentId}"/>
 			</c:url>
      <a href="${deleteUrl}" onclick="return confirmDelete();" class="ls-opt"<c:if test="${bean.parent}"> disabled="disabled"</c:if></a><s:message code="delete"/></a>
      </shiro:hasPermission>
      
     </td>
    <td onclick="location.href='${editUrl}';" style="cursor:pointer;">
    	<div id="beanname${status.index}" class="file-${bean.type}"><span<c:if test="${bean.image}"> imgUrl="${bean.url}"</c:if>><c:out value="${bean.name}"/></span></div>
    </td>
    <td onclick="location.href='${editUrl}';" style="cursor:pointer;"><fmt:formatDate value="${bean.lastModified}" pattern="yyyy-MM-dd HH:mm"/></td>
    <td onclick="location.href='${editUrl}';" style="cursor:pointer;"><s:message code="webFile.type.${bean.type}"/></td>
    <td align="right" onclick="location.href='${editUrl}';" style="cursor:pointer;"><c:if test="${bean.file}"><fmt:formatNumber value="${bean.lengthKB}" pattern="#,##0"/> KB</c:if></td>
  </tr>
  </c:forEach>
  </tbody>
</table>
</form>
</body>
</html>