<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="robots" content="none"/>
<title>Jspxcms管理平台登录 - Powered by Jspxcms</title>
<script type="text/javascript">
//if(top!=this){top.location=this.location;}
</script>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
	$("#validForm").validate();
	$("#username").focus().select();
});

<c:if test="${!empty shiroLoginFailure}">
	<c:choose>
	<c:when test="${shiroLoginFailure=='com.jspxcms.common.security.IncorrectCaptchaException'}">
	  alert("<s:message code='incorrectCaptchaError'/>");
	</c:when>
	<c:when test="${shiroLoginFailure=='com.jspxcms.common.security.CaptchaRequiredException'}">
	  //do nothing
	</c:when>
	<c:otherwise>
	  alert("<s:message code='usernameOrPasswordError'/>");
	</c:otherwise>
	</c:choose>
</c:if>
</script>
<style type="text/css">
.center{margin:0 auto;width:800px;}
.clear{clear:both;}
.div1{margin-top:150px;padding-top:5px;background:url('${ctx}/back/images/login_bg.png') no-repeat 430px top;}
.desc{float:left;height:180px;width:430px;padding-top:40px;text-align:center;color:#858585;font-size:28px;font-weight:bold;background:url('${ctx}/back/images/admin_logo.png') no-repeat center 8px;}
.input_form{float:right;height:180px;width:370px;}
.input_p{padding:10px 0;}
.input_p_submit{text-align:left;padding:5px 0 5px 80px;}
.label{display:block;float:left;width:70px;padding-right:10px;text-align:right;font-size:14px;font-weight:bold;color:#666666;}
.input{display:block;float:left;width:180px;}
.div2{text-align:center;color:#555555;}

a:link,a:visited{text-decoration:none;color:#014c90;}
a:hover,a:active{text-decoration:underline;color:#014c90;}

</style>
</head>
<body>
<div class="center div1">
	<div class="desc">管理中心</div>
	<form id="validForm" action="login.do" method="post" class="input_form">
		<p class="input_p">
			<label for="username" class="label">用户名:</label>
			<input type="text" id="username" name="username" value="admin" class="input required"/>
			<div class="clear"></div>
		</p>
		<p class="input_p">
			<label for="password" class="label">密码:</label>
			<input type="password" id="password" name="password" value="admin" class="input"/>
			<div class="clear"></div>
		</p>
		<c:if test="${sessionScope.shiroCaptchaRequired}">
		<p class="input_p">
			<label for="captcha" class="label">验证码:</label>
			<input type="text" id="captcha" name="captcha" class="input required"/>
			<div class="clear"></div>
		</p>
		<p class="input_p">
			<label for="captcha" class="label">&nbsp;</label>
			<img src="${ctx}/captcha.servlet" onclick="this.src='${ctx}/captcha.servlet?d='+new Date()*1" style="cursor:pointer;border:1px solid #ccc;" title="点击重新获取验证码"/>
			<div class="clear"></div>
		</p>		
		</c:if>
		<p class="input_p_submit">
			<input type="submit" value="提交"/>
		</p>
	</form>
	<div class="clear"></div>
</div>
<div class="center div2">&copy; 2010-2014 <a href="http://www.jspxcms.com/" target="_blank">Jspxcms</a> All Rights Reserved</div>
</body>
</html>