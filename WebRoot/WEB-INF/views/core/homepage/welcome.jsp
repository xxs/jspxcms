<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<style type="text/css">
.welcome{padding:10px 0 0 15px;color:#333;}
.bar{margin-top:10px;padding:5px 5px 5px 15px;background-color:#eaf2fa;border-top:1px solid #cbdaea;border-bottom:1px solid #deeaf7;font-weight:bold;font-size:14px;color:#333;}
.line{height:32px;line-height:32px;border-bottom:#dde9f5 1px solid;}
.label{float:left;width:200px;padding-left:15px;color:#333;background-color:#f4f8fc;}
.content{float:left;color:#666;padding-left:15px;}
.clear{clear:both;}
a.latest-version{color:red;}
</style>
</head>
<body class="c-body">
<p class="welcome">欢迎使用专业的JAVA内容管理系统 -- Jspxcms内容管理系统。</p>
<!-- 
<h1 class="bar">安全提示</h1>
 -->
<h1 class="bar">您的资料</h1>
<p class="line">
	<label class="label">当前版本</label>
	<span class="content">Jspxcms v${site.version} <a href="http://www.jspxcms.com" target="_blank" class="latest-version">查看最新版</a></span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">用户名</label>
	<span class="content">${user.username}</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">上次登录时间</label>
	<span class="content">
		<c:choose>
		<c:when test="${empty user.prevLoginDate}">您是第一次登录</c:when>
		<c:otherwise><fmt:formatDate value="${user.prevLoginDate}" pattern="yyyy-MM-dd HH:mm:ss"/></c:otherwise>
		</c:choose>
	</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">上次登录IP</label>
	<span class="content">	
		<c:choose>
		<c:when test="${empty user.prevLoginIp}">您是第一次登录</c:when>
		<c:otherwise><c:out value="${user.prevLoginIp}"/></c:otherwise>
		</c:choose>
	</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">本次登录时间</label>
	<span class="content">
		<fmt:formatDate value="${user.lastLoginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
	</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">本次登录IP</label>
	<span class="content">
		<c:out value="${user.lastLoginIp}"/>
	</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">登录次数</label>
	<span class="content"><fmt:formatNumber value="${user.logins}" pattern="#,###"/></span>
	<div class="clear"></div>
</p>
</body>
</html>