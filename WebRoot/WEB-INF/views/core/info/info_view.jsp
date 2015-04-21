<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<style type="text/css">
* html{overflow-y: scroll;}
.view-container h1{font-size:18px;text-align:center;padding:5px 20px;}
.view-container .ext{text-align:center;padding:2px 0;border-top:1px solid #eee;border-bottom:1px solid #eee;}
.view-container .ext .item{padding:0 5px;}
.view-container .text{padding:2px 0;}
.view-container .text p{padding:3px 0;}
</style>
<script type="text/javascript">
$(function() {
	$("#validForm").validate();
	$("input[name='title']").focus();
});
function uploadFile(name,button) {
	if($("#f_"+name).val()=="") {alert("<s:message code='pleaseSelectTheFile'/>");return;}
	Cms.uploadFile("../upload_file.do",name,button);
}
function uploadVideo(name,button) {
	if($("#f_"+name).val()=="") {alert("<s:message code='pleaseSelectTheFile'/>");return;}
	Cms.uploadFile("../upload_video.do",name,button);
}
function uploadImg(name,button) {
	if($("#f_"+name).val()=="") {alert("<s:message code='pleaseSelectTheFile'/>");return;}
	Cms.uploadImg("../upload_image.do",name,button);
}
function imgCrop(name) {
	if($("#"+name).val()=="") {alert("<s:message code='noImageToCrop'/>");return;}
	Cms.imgCrop("../../commons/img_area_select.do",name);
}
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="info.management"/> - <s:message code="view"/> <span style="font-weight:normal;font-size:12px;">(<s:message code="info.status"/>: <s:message code="info.status.${bean.status}"/>, ID:${bean.id})</span></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="queryNodeId" value="${queryNodeId}"/>
<f:hidden name="queryNodeType" value="${queryNodeType}"/>
<f:hidden name="queryInfoPermType" value="${queryInfoPermType}"/>
<f:hidden id="queryStatus" name="queryStatus" value="${queryStatus}"/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			<shiro:hasPermission name="core:info:create">
			<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?queryNodeId=${queryNodeId}&queryNodeType=${queryNodeType}&queryInfoPermType=${queryInfoPermType}&queryStatus=${queryStatus}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:info:copy">
			<div class="in-btn"><input type="button" value="<s:message code="copy"/>" onclick="location.href='create.do?id=${bean.id}&queryNodeId=${queryNodeId}&queryNodeType=${queryNodeType}&queryInfoPermType=${queryInfoPermType}&queryStatus=${queryStatus}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:info:edit">
			<div class="ls-btn"><input type="button" value="<s:message code="edit"/>" onclick="location.href='edit.do?id=${bean.id}&queryNodeId=${queryNodeId}&queryNodeType=${queryNodeType}&queryInfoPermType=${queryInfoPermType}&queryStatus=${queryStatus}&position=${pagedList.number*pagedList.size+status.index}&${searchstring}';"/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:info:delete">
			<div class="in-btn"><input type="button" value="<s:message code="delete"/>" onclick="if(confirmDelete()){location.href='delete.do?ids=${bean.id}&queryNodeId=${queryNodeId}&queryNodeType=${queryNodeType}&queryInfoPermType=${queryInfoPermType}&queryStatus=${queryStatus}&${searchstring}';}"<c:if test="${!bean.auditPerm}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<div class="in-btn"></div>
			<shiro:hasPermission name="core:info:audit_pass">
			<div class="ls-btn"><input type="button" value="<s:message code="info.auditPass"/>" onclick="location.href='audit_pass.do?ids=${bean.id}&redirect=view&queryNodeId=${queryNodeId}&queryNodeType=${queryNodeType}&queryInfoPermType=${queryInfoPermType}&queryStatus=${queryStatus}&position=${pagedList.number*pagedList.size+status.index}&${searchstring}';"<c:if test="${!bean.auditPerm}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:info:audit_reject">
			<div class="ls-btn"><input type="button" value="<s:message code="info.auditReject"/>" onclick="location.href='audit_reject.do?ids=${bean.id}&redirect=view&queryNodeId=${queryNodeId}&queryNodeType=${queryNodeType}&queryInfoPermType=${queryInfoPermType}&queryStatus=${queryStatus}&position=${pagedList.number*pagedList.size+status.index}&${searchstring}';"<c:if test="${!bean.auditPerm}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:info:audit_return">
			<div class="ls-btn"><input type="button" value="<s:message code="info.auditReturn"/>" onclick="location.href='audit_return.do?ids=${bean.id}&redirect=view&queryNodeId=${queryNodeId}&queryNodeType=${queryNodeType}&queryInfoPermType=${queryInfoPermType}&queryStatus=${queryStatus}&position=${pagedList.number*pagedList.size+status.index}&${searchstring}';"<c:if test="${!bean.auditPerm}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<div class="in-btn"></div>
			<div class="in-btn"><input type="button" value="<s:message code="prev"/>" onclick="location.href='view.do?id=${side.prev.id}&queryNodeId=${queryNodeId}&queryNodeType=${queryNodeType}&queryInfoPermType=${queryInfoPermType}&queryStatus=${queryStatus}&position=${position-1}&${searchstring}';"<c:if test="${empty side.prev}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"><input type="button" value="<s:message code="next"/>" onclick="location.href='view.do?id=${side.next.id}&queryNodeId=${queryNodeId}&queryNodeType=${queryNodeType}&queryInfoPermType=${queryInfoPermType}&queryStatus=${queryStatus}&position=${position+1}&${searchstring}';"<c:if test="${empty side.next}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			<div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='list.do?queryNodeId=${queryNodeId}&queryNodeType=${queryNodeType}&queryInfoPermType=${queryInfoPermType}&queryStatus=${queryStatus}&${searchstring}';"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
  <tr>
	  <td colspan="4" class="in-opt">
	  <div class="view-container">
	  	<h1><c:out value="${bean.title}"/></h1>
	  	<div class="ext">
	  		<span class="item"><s:message code="info.creator"/>: <c:out value="${bean.creator.username}"/> <c:if test="${!empty bean.creator.realName}">(<c:out value="${bean.creator.realName}"/>)</c:if></span>
	  		<span class="item"><s:message code="info.publishDate"/>: <fmt:formatDate value="${bean.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
	  	</div>
	  	<div class="text">
	  	${bean.textWithoutPageBreak}
	  	</div>
	  </div>
	  </td>
  </tr>
</table>
</form>
</body>
</html>