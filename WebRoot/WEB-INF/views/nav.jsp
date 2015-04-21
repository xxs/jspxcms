<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<style type="text/css">
html{height:100%;}
</style>
<script type="text/javascript">
$(function() {
	$(".left-menu a").each(function(index) {
		<c:if test="${empty subId}">
		if(index==0) {
			$(this).addClass("left-menu-selected");
		}
		</c:if>
		$(this).click(function() {
			$(this).blur();
			$(".left-menu a").each(function() {
				$(this).removeClass("left-menu-selected");				
			});
			$(this).addClass("left-menu-selected");
		});
		$(this).hover(function () {
		  $(this).addClass("left-menu-hover");
    }, function () {
      $(this).removeClass("left-menu-hover");
    });

	});
});
var leftFrame,centerFrame;
function navigation(leftUrl,centerUrl) {
	if(!leftFrame) {
	 centerFrame = parent.frames['center'];
	 leftFrame=parent.frames['left'];  
	}
	if(centerUrl) {
	 centerFrame.location.href = centerUrl;
	}
	if(leftUrl) {
	 leftFrame.location.href = leftUrl;
	}
}
</script>
</head>
<body class="left-body">
<ul class="left-menu">
	<c:forEach var="m" items="${menu.children}">
		<c:set var="hasPermission" value="${false}"/>
		<c:if test="${empty m.perm}"><c:set var="hasPermission" value="${true}"/></c:if>
		<c:if test="${!hasPermission}"><shiro:hasPermission name="${m.perm}"><c:set var="hasPermission" value="${true}"/></shiro:hasPermission></c:if>
		<%--全局功能须有super角色 --%>
		<c:if test="${hasPermission && (fn:startsWith(m.perm,'core:user_global:') || fn:startsWith(m.perm,'core:org_global:') || fn:startsWith(m.perm,'core:site:') || fn:startsWith(m.perm,'core:conf_global:'))}">
			<shiro:lacksRole name="super"><c:set var="hasPermission" value="${false}"/></shiro:lacksRole>
		</c:if>
		<c:if test="${hasPermission}">
			<c:choose>
			<c:when test="${fn:indexOf(m.url,',')!=-1}">
				<li><a href="javascript:navigation('${m.leftUrl}','${m.centerUrl}');" class="left-fun${(!empty subId && subId==m.id) ? ' left-menu-selected' : ''}"><s:message code="${m.name}" text="${m.name}"/></a></li>			
			</c:when>
			<c:otherwise>
				<li><a href="${m.url}" target="center" class="left-fun${(!empty subId && subId==m.id) ? ' left-menu-selected' : ''}"><s:message code="${m.name}" text="${m.name}"/></a></li>
			</c:otherwise>
			</c:choose>
		</c:if>
	</c:forEach>
</ul>
</body>
</html>