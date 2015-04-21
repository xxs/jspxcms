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
.bar{margin-top:5px;padding:5px 5px 5px 15px;background-color:#eaf2fa;border-top:1px solid #cbdaea;border-bottom:1px solid #deeaf7;font-weight:bold;font-size:14px;color:#333;}
.line{height:32px;line-height:32px;border-bottom:#dde9f5 1px solid;}
.label{float:left;width:200px;padding-left:15px;color:#333;background-color:#f4f8fc;}
.content{float:left;color:#666;padding-left:15px;}
.clear{clear:both;}
</style>
</head>
<body class="c-body">
<h1 class="bar">系统信息</h1>
<p class="line">
	<label class="label">操作系统</label>
	<span class="content">${props['os.name']} ${props['os.version']}</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">JAVA运行环境</label>
	<span class="content">${props['java.runtime.name']} ${props['java.runtime.version']}</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">JAVA虚拟机</label>
	<span class="content">${props['java.vm.name']} ${props['java.vm.version']}</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">系统用户</label>
	<span class="content">${props['user.name']}</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">用户主目录</label>
	<span class="content">${props['user.home']}</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">用户工作目录</label>
	<span class="content">${props['user.dir']}</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">用户临时目录</label>
	<span class="content">${props['java.io.tmpdir']}</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">最大内存</label>
	<span class="content"><fmt:formatNumber value="${maxMemoryMB}" pattern="#.00"/> MB</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">已用内存</label>
	<span class="content"><fmt:formatNumber value="${usedMemoryMB}" pattern="#.00"/> MB</span>
	<div class="clear"></div>
</p>
<p class="line">
	<label class="label">可用内存</label>
	<span class="content"><fmt:formatNumber value="${useableMemoryMB}" pattern="#.00"/> MB</span>
	<div class="clear"></div>
</p>
</body>
</html>